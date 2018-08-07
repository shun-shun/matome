package jp.gn.gonchan.resources;

import jp.gn.gonchan.dto.cokkie.CokkieInfo;
import jp.gn.gonchan.dto.display.AbstractDisplayDto;
import jp.gn.gonchan.logics.HtmlTemplateLogic;
import jp.gn.gonchan.logics.RssLogic;

import io.dropwizard.jersey.caching.CacheControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/new-arrivals-by-blog")
@Singleton
public class NewArrivalsByBlogResource extends AbstractResource {

    private static final Logger logger = LoggerFactory.getLogger(NewArrivalsByBlogResource.class);

    @Inject
    HtmlTemplateLogic templateLogic;

    @Inject
    RssLogic rssLogic;

    /**
     * トップ画面表示に必要な情報を取得し、トップ画面を表示する.
     */
    @GET
    @CacheControl(noCache = true, noStore = true)
    public Response displayTopScreen(@Context CokkieInfo cokkieInfo, @QueryParam("dest") String formDestination,
            @QueryParam("page") String formPage) {
        int destinationId;
        int page;
        try {
            destinationId = Integer.parseInt(formDestination);
            page = Integer.parseInt(formPage);
            if (page == 1) {
                page = 0;
            }
        } catch (NumberFormatException exception) {
            destinationId = 1;
            page = 0;
        }
        AbstractDisplayDto displayDto = rssLogic.getNewArrivalsByBlog(cokkieInfo, destinationId, page);
        logger.debug(displayDto.toString());
        setDisplayDto(displayDto, cokkieInfo);
        return Response.ok(templateLogic.getHtml("template/html/top.html", displayDto)).build();
    }

}
