package jp.gn.gonchan.resources;

import jp.gn.gonchan.dto.cokkie.CokkieInfo;
import jp.gn.gonchan.dto.display.AbstractDisplayDto;
import jp.gn.gonchan.dto.display.FavoriteDisplayDto;
import jp.gn.gonchan.logics.FavoriteLogic;
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

@Path("/favorite")
@Singleton
public class FavoriteResource extends AbstractResources {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteResource.class);

    @Inject
    HtmlTemplateLogic templateLogic;

    @Inject
    FavoriteLogic favoriteLogic;

    /**
     * トップ画面表示に必要な情報を取得し、トップ画面を表示する.
     */
    @GET
    @CacheControl(noCache = true, noStore = true)
    public Response displayTopScreen(@Context CokkieInfo cokkieInfo) {
        AbstractDisplayDto displayDto = new AbstractDisplayDto() {

            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append("AbstractDisplayDto [getNavDto=");
                builder.append(super.getNavDto());
                builder.append("]");
                return builder.toString();
            }
        };
        if (cokkieInfo != null && cokkieInfo.getCokkieArticleDto() != null) {
            displayDto = fetchCookieInfo(cokkieInfo);
        }
        setDisplayDto(displayDto, cokkieInfo);
        return Response.ok(templateLogic.getHtml("template/html/favorite.html", displayDto)).build();
    }

    private AbstractDisplayDto fetchCookieInfo(CokkieInfo cokkieInfo) {
        FavoriteDisplayDto displayDto;
        displayDto = favoriteLogic.getTopDto(cokkieInfo.getCokkieArticleDto());
        displayDto.setTargetSelf(cokkieInfo.isTargetSelf());
        logger.debug(displayDto.toString());
        return displayDto;
    }

}
