package nhanph.timekeeping.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import nhanph.timekeeping.admin.dto.upload.UploadFileDTO;
import nhanph.timekeeping.admin.util.ApiClient;
import nhanph.timekeeping.admin.util.ConfProperties;

import java.time.LocalDate;
import java.util.Base64;


/**
 * @Package: proton.face.dto.searchface
 * @author: nhanph
 * @date: 4/5/2025 2025
 * @Copyright: @nhanph
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class MinioService {

	private final ApiClient apiClient;

	public String uploadImage(String fileName, byte[] data) {
		String pathFile = "/media/avatar";
		UploadFileDTO uploadFileDTO = UploadFileDTO.builder()
				.base64Img(Base64.getEncoder().encodeToString(data))
				.fileUrl(pathFile)
				.fileName(fileName)
				.build();

		String urlUpload = ConfProperties.getProperty("service.upload.url.base64");
		log.info("urlUpload: {}{}",pathFile, fileName);
		return apiClient.sendPostRequestV2(urlUpload, uploadFileDTO);
	}

	public String uploadImageWithPath(String fileName, byte[] data) {
		LocalDate now = LocalDate.now();
		String pathFile = String.format("/%d/%02d/%02d/", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		UploadFileDTO uploadFileDTO = UploadFileDTO.builder()
				.base64Img(Base64.getEncoder().encodeToString(data))
				.fileUrl(pathFile)
				.fileName(fileName)
				.build();

		String urlUpload = ConfProperties.getProperty("service.upload.url.base64");
		log.info("urlUpload: {}", fileName);
		return apiClient.sendPostRequest(urlUpload, uploadFileDTO, String.class);
	}
}
