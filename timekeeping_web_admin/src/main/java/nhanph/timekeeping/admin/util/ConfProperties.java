package nhanph.timekeeping.admin.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ConfProperties {
    private static final Properties PROPS = new Properties();

    static {
        String env = System.getProperty("app.env", "");
        if (StringUtils.isNotBlank(env)) {
            env = "-" + env;
        }
        String resource = "/config" + env + ".properties";
        log.info("Properties path: {}", resource);
        try (InputStream input = ConfProperties.class.getResourceAsStream(resource)) {
            if (input == null) {
                throw new IllegalArgumentException("Properties file not found: " + resource);
            }
            PROPS.load(input);
        } catch (Exception ex) {
            log.error("Failed to load config from {}", resource, ex);
        }
    }

    public static String getProperty(String key) {
        if (key == null || key.isEmpty()) {
            return "";
        }
        return PROPS.getProperty(key);
    }
}
