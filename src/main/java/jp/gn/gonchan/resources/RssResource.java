package jp.gn.gonchan.resources;

import jp.gn.gonchan.dto.cokkie.CokkieInfo;
import jp.gn.gonchan.dto.display.AbstractDisplayDto;
import jp.gn.gonchan.logics.HtmlTemplateLogic;
import jp.gn.gonchan.logics.RssLogic;

import io.dropwizard.jersey.caching.CacheControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/rss")
@Singleton
public class RssResource extends AbstractResource {

    private static final Logger logger = LoggerFactory.getLogger(RssResource.class);

    @Inject
    HtmlTemplateLogic templateLogic;

    @Inject
    RssLogic rssLogic;

    /**
     * トップ画面表示に必要な情報を取得し、トップ画面を表示する.
     */
    @GET
    @CacheControl(noCache = true, noStore = true)
    public Response displayTopScreen(@Context CokkieInfo cokkieInfo) {
        rssLogic.registerAllRss();
        AbstractDisplayDto displayDto = null;
        setDisplayDto(displayDto, cokkieInfo);
        return Response.seeOther(URI.create("/")).build();
    }

}
