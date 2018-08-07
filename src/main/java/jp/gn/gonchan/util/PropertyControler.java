package jp.gn.gonchan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * @author yu-oonishi
 */
public final class PropertyControler extends Control {

    @Override
    public ResourceBundle newBundle(final String baseName, final Locale locale, final String format,
            final ClassLoader loader, final boolean reload) throws IOException {
        final String bundleName = toBundleName(baseName, locale);
        final String resrouceName = toResourceName(bundleName, "properties");

        try (InputStream is = loader.getResourceAsStream(resrouceName)) {
            final InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            final BufferedReader br = new BufferedReader(isr);
            return new PropertyResourceBundle(br);
        }
    }

}
