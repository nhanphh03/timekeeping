package proton.face.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class Utils {

	public static boolean isEmpty(String s) {
		return s == null || s.isEmpty();
	}

	public static <T> String convertDateToString(T date, String format) {
		if (date == null) {
			return "";
		}
		String str = "";
		try {
			str = new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return str;
	}

	public static String convertByte64ToFile(String byte64, String path) throws Exception {
		byte[] result = Base64.getDecoder().decode(byte64);
		File file = new File(path + "face" + System.currentTimeMillis() + ".jpg");
		FileUtils.writeByteArrayToFile(file, result);

		return file.getPath();
	}

	public static String getNameFileImage(String peopleId) {
		String uuid = UUID.randomUUID().toString();
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		return time + "/"
				+ peopleId + "_"
				+ uuid + ".jpg";
	}

	public static String avatarPeopleUrl(String peopleId) {
		return "/media/avatar/"
				+ peopleId +".jpg";
	}
}
