package jp.gn.gonchan.resources;

import jp.gn.gonchan.constant.DisplayMessageLevel;
import jp.gn.gonchan.dto.cokkie.CokkieArticleDto;
import jp.gn.gonchan.dto.cokkie.CokkieInfo;
import jp.gn.gonchan.dto.display.AbstractDisplayDto;
import jp.gn.gonchan.form.RegisterArticleForm;
import jp.gn.gonchan.logics.CokkieLogic;
import jp.gn.gonchan.logics.HtmlTemplateLogic;
import jp.gn.gonchan.logics.RssLogic;
import jp.gn.gonchan.util.CommonValidator;
import jp.gn.gonchan.util.DisplayMessage;

import io.dropwizard.jersey.caching.CacheControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/")
@Singleton
public class TopResource extends AbstractResource {

    private static final Logger logger = LoggerFactory.getLogger(TopResource.class);

    @Inject
    HtmlTemplateLogic templateLogic;

    @Inject
    RssLogic rssLogic;

    @Inject
    CokkieLogic cokkieLogic;

    /**
     * トップ画面表示に必要な情報を取得し、トップ画面を表示する.
     */
    @GET
    @CacheControl(noCache = true, noStore = true)
    public Response displayTopScreen(@Context CokkieInfo cokkieInfo, @QueryParam("page") String formPage) {
        int page;
        try {
            page = Integer.parseInt(formPage);
            if (page == 1) {
                page = 0;
            }
        } catch (NumberFormatException exception) {
            page = 0;
        }
        AbstractDisplayDto displayDto = rssLogic.getNewArrivals(cokkieInfo, page);
        logger.debug(displayDto.toString());
        setDisplayDto(displayDto, cokkieInfo);
        return Response.ok(templateLogic.getHtml("template/html/top.html", displayDto)).build();
    }

    @POST
    public Response registerAritcleToCokkie(@Context CokkieInfo cokkieInfo, @BeanParam RegisterArticleForm loginForm) {

        CommonValidator<RegisterArticleForm> validator = new CommonValidator<>();
        List<String> errorMessages = validator.validate(loginForm);
        if (errorMessages.size() > 0) {
            DisplayMessage dm = new DisplayMessage(errorMessages, DisplayMessageLevel.ERROR);
            return Response.ok(templateLogic.getHtml("template/html/top.html", dm)).build();
        }

        CokkieArticleDto dto = new CokkieArticleDto();
        dto.setArticleId(Integer.parseInt(loginForm.getArticleId()));
        dto.setDestinationId(Integer.parseInt(loginForm.getDestinationId()));
        List<CokkieArticleDto> cokkieArticleList;
        if (cokkieInfo == null || cokkieInfo.getCokkieArticleDto() == null) {
            cokkieArticleList = new ArrayList<>();
            cokkieArticleList.add(dto);
        } else {
            cokkieArticleList = cokkieInfo.getCokkieArticleDto();
            cokkieArticleList.add(dto);
        }
        NewCookie cookies = cokkieLogic.createCookie(new CokkieInfo(cokkieArticleList));
        return Response.seeOther(URI.create("/")).cookie(cookies).build();
    }
}
