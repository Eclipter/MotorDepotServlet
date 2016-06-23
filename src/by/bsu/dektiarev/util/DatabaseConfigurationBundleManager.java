package by.bsu.dektiarev.util;

import java.util.ResourceBundle;

/**
 * Class used to return property from ResourceBundle where database configuration is stored
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
