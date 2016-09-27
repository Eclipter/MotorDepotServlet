package by.bsu.dektiarev.util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that is used to retrieve value from bundles with many locales
 * Created by USER on 10.06.2016.
 */
public final class InternationalizedBundleManager {

    private static final List<String> languages = Arrays.asList("ru");

    /**
     * Gets the value from specified bundle
     * @param bundleName name of bundle
     * @param key specified key
     * @param languageTag specified language or null in order to get default language
     * @return value from bundle
     */
    public static String getProperty(String bundleName, String key, String languageTag) {
        Locale locale = Locale.ROOT;
        for(String language : languages) {
            if(language.equals(languageTag)) {
                locale = new Locale(languageTag);
                break;
            }
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, locale);
        return resourceBundle.getString(key);
    }

    private InternationalizedBundleManager() {
    }
}
