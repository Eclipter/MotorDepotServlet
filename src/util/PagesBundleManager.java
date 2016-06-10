package util;

import java.util.ResourceBundle;

/**
 * Created by USER on 07.06.2016.
 */
public final class PagesBundleManager {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(BundleName.PAGES);

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    private PagesBundleManager() {
    }
}
