package util;

import java.util.ResourceBundle;

/**
 * Created by USER on 14.06.2016.
 */
public final class DatabaseConfigurationBundleManager {

    private static final ResourceBundle DATABASE_BUNDLE = ResourceBundle.getBundle(BundleName.DATABASE);


    public static String getProperty(String key) {
        return DATABASE_BUNDLE.getString(key);
    }

    private DatabaseConfigurationBundleManager() {
    }
}
