package jp.gn.gonchan.filters;

import jp.gn.gonchan.constant.Constant;
import jp.gn.gonchan.dto.cokkie.CokkieInfo;
import jp.gn.gonchan.logics.CokkieLogic;
import jp.gn.gonchan.logics.HtmlTemplateLogic;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;

public class CokkieCheckFilter implements ContainerRequestFilter {

    @Inject
    CokkieLogic cokkieLogic;

    @Inject
    HtmlTemplateLogic templateLogic;

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final Cookie cookie = requestContext.getCookies().get(Constant.COOKIE_NAME);
        if (cookie == null) {
            return;
        }

        CokkieInfo cokkieInfo = cokkieLogic.createCokkieInfoFromCookie(cookie);
        if (cokkieInfo == null) {
            return;
        }
        requestContext.setProperty(Constant.COOKIE_NAME, cokkieInfo);
        return;
    }

}
