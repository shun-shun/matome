package jp.gn.gonchan.filters;

import javax.ws.rs.Path;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class CokkieCheckFeature implements DynamicFeature {
    
    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        if (resourceInfo.getResourceClass().getAnnotation(Path.class) != null) {
            context.register(CokkieCheckFilter.class);
        }
        
    }
    
}
