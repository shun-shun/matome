package jp.gn.gonchan.filters;

import jp.gn.gonchan.constant.Constant;
import jp.gn.gonchan.dto.cokkie.CokkieInfo;

import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

public class CokkieInfoFactory implements Factory<CokkieInfo> {

    private final Factory<ContainerRequestContext> contextFactory;

    @Inject
    public CokkieInfoFactory(Factory<ContainerRequestContext> contextFactory) {
        this.contextFactory = contextFactory;
    }

    @Override
    public CokkieInfo provide() {
        ContainerRequestContext context = contextFactory.provide();
        try {
            return (CokkieInfo) context.getProperty(Constant.COOKIE_NAME);
        } finally {
            contextFactory.dispose(context);
        }
    }

    @Override
    public void dispose(CokkieInfo instance) {}

}
