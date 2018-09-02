package jp.gn.gonchan.dao;

import jp.gn.gonchan.Entity.Articles;
import jp.gn.gonchan.dto.MatomeDto;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RegisterMapperFactory(BeanMapperFactory.class)
public interface TArticleDao {

    @SqlUpdate("INSERT INTO T_ARTICLES(destinationId,articleId,title,author,link,uri,contributors,enclosures,links,"
            + "contentType,contentValue,descriptionType,descriptionValue,publishedDate,updatedDate,insertDate) "
            + "VALUES(:destinationId,:articleId,:title,:author,:link,:uri,:contributors,:enclosures,"
            + ":links,:contentType,:contentValue,:descriptionType,"
            + ":descriptionValue,:publishedDate,:updatedDate,:insertDate);")
    public void insertArticle(@BindBean Articles articles);

    @SqlQuery("SELECT DESTINATIONID FROM T_ARTICLES WHERE title = :title")
    @SingleValueResult
    public Optional<Integer> selectArticleByTitle(@Bind("title") String title);


    @SqlQuery("SELECT * FROM T_ARTICLES WHERE destinationId = :destinationId ORDER BY ASC LIMIT 10")
    public List<Articles> selectListByIdLim10(@Bind("id") int destinationId);

    @SqlQuery("SELECT * FROM T_ARTICLES ORDER BY RAND() LIMIT 10")
    public List<Articles> selectListRandam();


    @SqlQuery("SELECT title,link,diestinationName,publishedDate "
            + "FROM T_ARTICLES LEFT OUTER JOIN M_PROVIDING_DESTINATION "
            + "ON T_ARTICLES.destinationId = M_PROVIDING_DESTINATION.destinationId " + "ORDER BY RAND() LIMIT 20;")
    public List<MatomeDto> selectListRandamtest();

    @SqlQuery("SELECT T_ARTICLES.destinationId, articleId, title,link,DiestinationName,publishedDate "
            + "FROM T_ARTICLES LEFT OUTER JOIN M_PROVIDING_DESTINATION "
            + "ON T_ARTICLES.destinationId = M_PROVIDING_DESTINATION.destinationId "
            + "WHERE publishedDate BETWEEN :startTimeStamp AND :endTimeStamp ORDER BY PUBLISHEDDATE DESC "
            + "LIMIT :displayCount OFFSET :offset;")
    public List<MatomeDto> selectListNewArrivals(@Bind("startTimeStamp") Timestamp startTimeStamp,
            @Bind("endTimeStamp") Timestamp endTimeStamp, @Bind("displayCount") int displayCount,
            @Bind("offset") int offset);

    @SqlQuery("SELECT T_ARTICLES.destinationId, articleId, title,link,DiestinationName,publishedDate "
            + "FROM T_ARTICLES LEFT OUTER JOIN M_PROVIDING_DESTINATION "
            + "ON T_ARTICLES.destinationId = M_PROVIDING_DESTINATION.destinationId "
            + "WHERE T_ARTICLES.destinationId = :destinationId ORDER BY PUBLISHEDDATE DESC "
            + "LIMIT :displayCount OFFSET :offset;")
    public List<MatomeDto> selectListNewArrivalsByBlog(@Bind("destinationId") int destinationId,
            @Bind("displayCount") int displayCount, @Bind("offset") int offset);

    @SqlQuery("SELECT T_ARTICLES.destinationId, articleId, title,link,DiestinationName,publishedDate "
            + "FROM T_ARTICLES LEFT OUTER JOIN M_PROVIDING_DESTINATION "
            + "ON T_ARTICLES.destinationId = M_PROVIDING_DESTINATION.destinationId"
            + " WHERE T_ARTICLES.destinationId = :destinationId AND articleId = :articleId;")
    @SingleValueResult
    public Optional<MatomeDto> selectArticleById(@Bind("destinationId") int destinationId,
            @Bind("articleId") int articleId);


    @SqlQuery("SELECT count(*) FROM T_ARTICLES WHERE publishedDate BETWEEN :startTimeStamp AND :endTimeStamp")
    public int selectTodayArticles(@Bind("startTimeStamp") Timestamp startTimeStamp,
            @Bind("endTimeStamp") Timestamp endTimeStamp);

    @SqlQuery("SELECT count(*) FROM T_ARTICLES WHERE destinationId = :destinationId")
    public int selectTotalByDestination(@Bind("destinationId") int destinationId);
}
