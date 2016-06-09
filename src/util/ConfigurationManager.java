package util;

import java.util.ResourceBundle;

/**
 * Created by USER on 07.06.2016.
 */
public final class ConfigurationManager {

    private static final ResourceBundle resourceBundle;

    static  {
        resourceBundle = ResourceBundle.getBundle(BundleNames.PAGES_BUNDLE);
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
