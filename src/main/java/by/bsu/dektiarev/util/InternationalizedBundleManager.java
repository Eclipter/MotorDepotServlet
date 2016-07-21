package by.bsu.dektiarev.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that is used to retrieve value from bundles with many locales
 * Created by USER on 10.06.2016.
 */
public final class InternationalizedBundleManager {

    private static final String LANGUAGE_ENGLISH = "en";
    private static final String LANGUAGE_RUSSIAN = "ru";

    /**
     * Gets the value from specified bundle
     * @param bundleName name of bundle
     * @param key specified key
     * @param languageTag specified language
     * @return value from bundle
     */
    public static String getProperty(String bundleName, String key, String languageTag) {
        Locale locale;
        if(languageTag.equals(LANGUAGE_ENGLISH) || languageTag.equals(LANGUAGE_RUSSIAN)) {
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
