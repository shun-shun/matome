package jp.gn.gonchan.resources;

import jp.gn.gonchan.dto.cokkie.CokkieInfo;
import jp.gn.gonchan.dto.display.AbstractDisplayDto;
import jp.gn.gonchan.logics.BlogIndexLogic;
import jp.gn.gonchan.logics.HtmlTemplateLogic;

import io.dropwizard.jersey.caching.CacheControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/blogIndex")
@Singleton
public class BlogIndexResource extends AbstractResource {

    private static final Logger logger = LoggerFactory.getLogger(BlogIndexResource.class);

    @Inject
    HtmlTemplateLogic templateLogic;

    @Inject
    BlogIndexLogic blogIndexLogic;

    /**
     * トップ画面表示に必要な情報を取得し、トップ画面を表示する.
     */
    @GET
    @CacheControl(noCache = true, noStore = true)
    public Response displayTopScreen(@Context CokkieInfo cokkieInfo) {
        AbstractDisplayDto displayDto = blogIndexLogic.getBlogIndexDisplayDto();
        logger.debug(displayDto.toString());
        setDisplayDto(displayDto, cokkieInfo);
        return Response.ok(templateLogic.getHtml("template/html/blogIndex.html", displayDto)).build();
    }

}
