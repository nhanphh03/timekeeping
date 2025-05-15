package proton.face.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;


public class AppConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

	private static final AppConfig INS = new AppConfig();
	private final Properties prop;

	private AppConfig() {
		InputStream input = null;
		InputStreamReader inReader = null;
		this.prop = new Properties();

		try {
			File file = new File("config.properties"); // external file
			if (file.exists()) {
				input = Files.newInputStream(file.toPath());
				LOGGER.info("// read config. from external file - path = {}", file.getAbsolutePath());
			} else {
				input = Files.newInputStream(Paths.get("./resource/config.properties"));
			}
			inReader = new InputStreamReader(input, StandardCharsets.UTF_8);
			this.prop.load(inReader);
			LOGGER.info("// load properties - size = " + this.prop.size());

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		} finally {
            try {
                Objects.requireNonNull(inReader).close();
				Objects.requireNonNull(input).close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
		}
	}

	public String getProperty(String key) {
		if (this.prop == null || key == null || key.isEmpty()) {
			return "";
		}
		return this.prop.getProperty(key);
	}

	public String getWidthScreen() {
		return prop.getProperty("screen_width");
	}

	public String getHeightScreen() {
		return prop.getProperty("screen_height");
	}

	public String getSquareMinHeight() {
		return prop.getProperty("square_min_height");
	}

	public String getSquareMinWidth() {
		return prop.getProperty("square_min_width");
	}

	public String getResolutionVideoWidth() {
		return prop.getProperty("square_min_width");
	}

	public String getResolutionVideoHeight() {
		return prop.getProperty("resolution_video_height");
	}

	public String getFaceHaar() {
		return prop.getProperty("face_cascade_haar");
	}

	public String getFaceLPB() {
		return prop.getProperty("face_cascade_lbp");
	}

	public String getCustomParameter(String str) {
		return prop.getProperty(str);
	}

	public String getUrlSaveFace() {
		return prop.getProperty("url_save_face");
	}

	public String getInterval_X() {
		return prop.getProperty("interval_x");
	}

	public String getInterval_Y() {
		return prop.getProperty("interval_y");
	}

	public static AppConfig getInstance() {
		return INS;
	}
}
