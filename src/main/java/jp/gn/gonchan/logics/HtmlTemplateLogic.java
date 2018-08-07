package jp.gn.gonchan.logics;

import jp.gn.gonchan.MatomeConfiguration;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.StreamingOutput;

public class HtmlTemplateLogic {

    private final MustacheFactory mustacheFactory;

    /**
     * Create a new instance with the specified configuration.
     */
    @Inject
    public HtmlTemplateLogic(final MatomeConfiguration configuration) {
        // Create a MustacheFactory
        mustacheFactory = new DefaultMustacheFactory() {
            @Override
            public Mustache compile(String name) {
                if (configuration.isHtmlTemplateReloadable()) {
                    final Mustache mustache = getMustacheCacheFunction().apply(name);
                    mustache.init();
                    return mustache;
                } else {
                    return super.compile(name);
                }
            }

            @Override
            public String resolvePartialPath(String dir, String name, String extension) {
                return super.resolvePartialPath(dir, name, StringUtils.EMPTY);
            }
        };
    }

    /**
     * Returns a formatted HTML content using mustache.
     */
    public StreamingOutput getHtml(String htmlTemplateName, Object scope) {
        Mustache mustache = mustacheFactory.compile(htmlTemplateName);
        return (output) -> {
            try (OutputStreamWriter writer = new OutputStreamWriter(output, StandardCharsets.UTF_8)) {
                if (scope instanceof List) {
                    mustache.execute(writer, (List<?>) scope);
                } else if (scope instanceof Object[]) {
                    mustache.execute(writer, (Object[]) scope);
                } else {
                    mustache.execute(writer, scope);
                }
            }
        };
    }

    public StreamingOutput getHtml(String htmlTemplateName, Object... scopes) {
        return getHtml(htmlTemplateName, (Object) scopes);
    }

    public StreamingOutput getHtml(String htmlTemplateName, List<Object> scopes) {
        return getHtml(htmlTemplateName, (Object) scopes);
    }
}
