package util;

import java.util.ResourceBundle;

/**
 * Created by USER on 07.06.2016.
 */
public class ConfigurationManager {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/pages");

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
