package jp.gn.gonchan.logics;

import jp.gn.gonchan.Entity.Articles;
import jp.gn.gonchan.Entity.ProvidingDestination;
import jp.gn.gonchan.constant.Constant;
import jp.gn.gonchan.dao.MProvidingDestinationDao;
import jp.gn.gonchan.dao.TArticleDao;
import jp.gn.gonchan.dto.MatomeDto;
import jp.gn.gonchan.dto.ModifedDto;
import jp.gn.gonchan.dto.cokkie.CokkieInfo;
import jp.gn.gonchan.dto.display.AbstractDisplayDto;
import jp.gn.gonchan.dto.display.NewArrivalsDisplayDto;

import com.google.common.net.HttpHeaders;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RssLogic {

    private static final int PAGENATION_STAYING_AROUND = 4;

    private static final Logger logger = LoggerFactory.getLogger(RssLogic.class);

    @Inject
    MProvidingDestinationDao providingDestinationDao;

    @Inject
    TArticleDao articleDao;

    /**
     * DBに登録されている取得先サイトのRSSリンクを取得し、記事が追加されているか確認し、DBに登録する
     */
    public void registerAllRss() {
        List<ProvidingDestination> destinations = getAllDestination();
        for (ProvidingDestination destination : destinations) {
            if (destination.getRssUrl() != null) {
                logger.debug(destination.getDiestinationName());
                Optional<ModifedDto> optModifedDto = getSingleRss(destination);
                if (optModifedDto.isPresent()) {
                    ModifedDto dto = optModifedDto.get();
                    OffsetDateTime dt =
                            OffsetDateTime.parse(dto.getLastModifed(), DateTimeFormatter.RFC_1123_DATE_TIME);
                    Date date = Date.from(dt.toInstant());
                    providingDestinationDao.updateModified(new Timestamp(date.getTime()), dto.geteTag(),
                            destination.getDestinationId());
                }
            }
        }
    }

    /**
     * 取得先サイトのRSSリンクにHTTPリクエストを投げ、Feedを取得し、追加された記事の確認を行う<br>
     * 追加された記事がある場合は、DBに登録する.
     *
     * @param destination 取得先サイトのDto
     * @return 更新確認Dto
     */
    public Optional<ModifedDto> getSingleRss(final ProvidingDestination destination) {

        HttpGet request = new HttpGet(destination.getRssUrl());
        // 前回に取得した更新日時をif-modified-sinceヘッダに含める
        request.addHeader(HttpHeaders.IF_MODIFIED_SINCE, destination.getLastModified());
        // 前回のetagをif-mone-matchヘッダに含める
        request.addHeader(HttpHeaders.IF_NONE_MATCH, destination.geteTag());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(request);) {

            final Header[] lastModifedHeader = response.getHeaders(HttpHeaders.LAST_MODIFIED);
            // 配列の要素が2つ以上を考慮しない
            Optional<String> optLastModifed = Optional.ofNullable(null);
            for (Header header : lastModifedHeader) {
                optLastModifed = Optional.ofNullable(header.getValue());
            }
            final Header[] eTagHeader = response.getHeaders(HttpHeaders.ETAG);
            // 配列の要素が2つ以上を考慮しない
            Optional<String> opteTag = Optional.ofNullable(null);
            for (Header header : eTagHeader) {
                opteTag = Optional.ofNullable(header.getValue());
            }

            final int status = response.getStatusLine().getStatusCode();
            // ステータスコード200以外の場合は抜ける
            if (status != HttpStatus.SC_OK) {
                return Optional.ofNullable(null);
            }

            // ヘッダに値があるかチェックする
            if (opteTag.isPresent() && optLastModifed.isPresent()) {
                final String etag = opteTag.get();
                final String lastModifed = optLastModifed.get();

                // etagを優先し、同じであれば更新しない
                if (etag.equals(destination.geteTag()) || lastModifed.equals(destination.getLastModified())) {
                    // TODO LastModified比較のため、データ形式を変更する
                    logger.debug("Etag,LastModifiedに変更なしのため、更新されませんでした");
                    return Optional.ofNullable(null);
                }
                InputStream stream = response.getEntity().getContent();
                SyndFeed feed = convertSyndFeedByStream(stream);
                List<Articles> articles = convertArticles(destination.getDestinationId(), feed);
                registreRss(articles);

                ModifedDto modifedDto = new ModifedDto();
                modifedDto.setLastModifed(lastModifed);
                modifedDto.seteTag(etag);
                return Optional.of(modifedDto);
            }

            // ヘッダに値がない場合は、更新する
            InputStream stream = response.getEntity().getContent();
            SyndFeed feed = convertSyndFeedByStream(stream);
            List<Articles> articles = convertArticles(destination.getDestinationId(), feed);
            registreRss(articles);
            return Optional.ofNullable(null);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        } catch (FeedException exception) {
            // TODO unicodeを考慮
            exception.printStackTrace();
            return Optional.ofNullable(null);
        }
    }

    private SyndFeed convertSyndFeedByStream(InputStream stream)
            throws IllegalArgumentException, FeedException, IOException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(stream));
        return feed;
    }

    private SyndFeed convertSyndFeedByStringReder(InputStream stream)
            throws IOException, IllegalArgumentException, FeedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder xml = new StringBuilder();
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            xml.append(line);
        }
        Pattern p = Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+");
        StringReader sreader = new StringReader(p.matcher(xml.toString()).replaceAll(""));
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(sreader);
        return feed;
    }

    private List<Articles> convertArticles(int destinationId, SyndFeed feed)
            throws IllegalArgumentException, FeedException, IOException {
        List<SyndEntry> syndEntries = feed.getEntries();

        List<Articles> articleList = new ArrayList<>();
        for (SyndEntry entry : syndEntries) {
            Articles articles = new Articles();
            articles.setDestinationId(destinationId);
            if (entry.getTitle().length() > 1000) {
                articles.setTitle(entry.getTitle().substring(0, 999));
            } else {
                articles.setTitle(entry.getTitle());
            }

            if (entry.getAuthor().length() > 50) {
                articles.setAuthor(entry.getAuthor().substring(0, 49));
            } else {
                articles.setAuthor(entry.getAuthor());
            }

            if (entry.getLink().length() > 1000) {
                articles.setLink(entry.getLink().substring(0, 999));
            } else {
                articles.setLink(entry.getLink());
            }

            if (entry.getUri().length() > 1000) {
                articles.setUri(entry.getUri().substring(0, 999));
            } else {
                articles.setUri(entry.getUri());
            }

            if (entry.getContributors().toString().length() > 100) {
                articles.setContributors(entry.getContributors().toString().substring(0, 99));
            } else {
                articles.setContributors(entry.getContributors().toString());
            }

            if (entry.getEnclosures().toString().length() > 10000) {
                articles.setEnclosures(entry.getEnclosures().toString().substring(0, 9999));
            } else {
                articles.setEnclosures(entry.getEnclosures().toString());
            }

            if (entry.getLinks().toString().length() > 100) {
                articles.setLinks(entry.getLinks().toString().substring(0, 99));
            } else {
                articles.setLinks(entry.getLinks().toString());
            }

            articles.setPublishedDate(entry.getPublishedDate());
            articles.setUpdatedDate(entry.getUpdatedDate());

            if (!entry.getContents().isEmpty()) {
                SyndContent syndContents = (SyndContent) entry.getContents().get(0);
                articles.setContentType(syndContents.getType());
                if (syndContents.getValue().length() > 9999) {
                    articles.setContentValue(syndContents.getValue().substring(0, 9999));
                } else {
                    articles.setContentValue(syndContents.getValue());
                }
            }

            SyndContent syndDescription = (SyndContent) entry.getDescription();
            articles.setDescriptionType(syndDescription.getType());
            if (syndDescription.getValue().length() > 9999) {
                articles.setDescriptionValue(syndDescription.getValue().substring(0, 9999));
            } else {
                articles.setDescriptionValue(syndDescription.getValue());
            }
            articleList.add(articles);
        }
        return articleList;
    }

    private void registreRss(List<Articles> articleList) {
        for (Articles articles : articleList) {
            final Optional<Integer> opt = exsistsArticle(articles);
            if (!opt.isPresent()) {
                // 登録日時を取得
                articles.setInsertDate(Timestamp.from(new Date().toInstant()));
                articleDao.insertArticle(articles);
                logger.debug("更新されました :" + articles.getTitle());
            } else {
                logger.debug("登録済みのため、更新されませんでした :" + articles.getTitle());
            }
        }
    }

    private Optional<Integer> exsistsArticle(Articles articles) {
        return articleDao.selectArticleByTitle(articles.getTitle());
    }

    public List<Articles> createMatomeDtoList(int destinationId) {
        List<Articles> articles = articleDao.selectListByIdLim10(destinationId);
        return articles;
    }

    private List<ProvidingDestination> getAllDestination() {
        List<ProvidingDestination> list = providingDestinationDao.selectAllDistination();
        return list;
    }

    private List<ProvidingDestination> getDestinationByLimit() {
        List<ProvidingDestination> list = providingDestinationDao.selectDistinationByLimit();
        return list;
    }


    public AbstractDisplayDto getNewArrivalsByBlog(CokkieInfo cokkieInfo, int destinationId, int page) {
        final int totalCount = articleDao.selectTotalByDestination(destinationId);
        final int pageListCount = totalCount / Constant.DEFAULT_DISPLAY_COUNT;
        List<Integer> pageList = makePageList(pageListCount, page);
        NewArrivalsDisplayDto dto = new NewArrivalsDisplayDto();
        dto.setPageList(pageList);

        final int offset = page * Constant.DEFAULT_DISPLAY_COUNT;

        List<MatomeDto> arrivalsByBlog =
                articleDao.selectListNewArrivalsByBlog(destinationId, Constant.DEFAULT_DISPLAY_COUNT, offset);
        dto.setNewArrivalsDto(arrivalsByBlog);
        if (cokkieInfo != null) {
            dto.setTargetSelf(cokkieInfo.isTargetSelf());
        }
        return dto;
    }

    public AbstractDisplayDto getNewArrivals(CokkieInfo cokkieInfo, int page) {
        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = start.plusHours(23).plusMinutes(59).plusSeconds(59);

        final Timestamp startTimeStamp = Timestamp.valueOf(start);
        final Timestamp endTimeStamp = Timestamp.valueOf(end);

        final int totalCount = getTotalTodayArticle(startTimeStamp, endTimeStamp);
        final int pageListCount = totalCount / Constant.DEFAULT_DISPLAY_COUNT;
        List<Integer> pageList = makePageList(pageListCount, page);

        NewArrivalsDisplayDto dto = new NewArrivalsDisplayDto();
        dto.setPageList(pageList);

        final int offset = page * Constant.DEFAULT_DISPLAY_COUNT;

        List<MatomeDto> newArrivalsDto =
                getNewArrivalsArticles(startTimeStamp, endTimeStamp, Constant.DEFAULT_DISPLAY_COUNT, offset);
        dto.setNewArrivalsDto(newArrivalsDto);
        if (cokkieInfo != null) {
            dto.setTargetSelf(cokkieInfo.isTargetSelf());
        }
        return dto;
    }

    private List<Integer> makePageList(final int pageListCount, final int stayingPage) {
        List<Integer> pageList = new ArrayList<>();
        int index = stayingPage - PAGENATION_STAYING_AROUND;
        if (index < 1) {
            index = 1;
        }
        int end = stayingPage + PAGENATION_STAYING_AROUND;
        if (end > pageListCount) {
            end = pageListCount;
        }
        for (; index <= end; index++) {
            pageList.add(index);
        }
        return pageList;
    }

    private List<MatomeDto> getNewArrivalsArticles(Timestamp startTimeStamp, Timestamp endTimeStamp, int displayCount,
            int offset) {

        List<MatomeDto> matomeDtos =
                articleDao.selectListNewArrivals(startTimeStamp, endTimeStamp, displayCount, offset);
        return matomeDtos;
    }

    private int getTotalTodayArticle(final Timestamp startTimeStamp, final Timestamp endTimeStamp) {
        return articleDao.selectTodayArticles(startTimeStamp, endTimeStamp);
    }
}
