package util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by USER on 10.06.2016.
 */
public final class InternationalizedBundleManager {

    public static String getProperty(String bundleName, String key, String languageTag) {
        Locale locale;
        if(languageTag.equals("en") || languageTag.equals("ru")) {
            locale = new Locale(languageTag);
        } else {
            locale = Locale.getDefault();
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, locale);
        return resourceBundle.getString(key);
    }

    private InternationalizedBundleManager() {
    }
}
