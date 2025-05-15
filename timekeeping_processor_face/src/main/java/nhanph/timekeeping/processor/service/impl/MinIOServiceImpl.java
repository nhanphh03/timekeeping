package nhanph.timekeeping.processor.service.impl;

import lombok.RequiredArgsConstructor;
import nhanph.timekeeping.processor.dto.minIO.UploadFileDTO;
import nhanph.timekeeping.processor.dto.minIO.UploadResponseDTO;
import nhanph.timekeeping.processor.service.MinIOService;
import nhanph.timekeeping.processor.util.ApiClient;
import nhanph.timekeeping.processor.util.Constants;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {
    private final ApiClient apiClient;

    @Override
    public void uploadImageDetection(UploadFileDTO imageBase64) {
        apiClient.sendPostRequest(
                Constants.MINIO_SERVICE.urlMinio + Constants.MINIO_SERVICE.UPLOAD_BASE64,
                imageBase64,
                UploadResponseDTO.class,
                null
        );
    }

    @Override
    public String getBase64Image(String fileUrl){
        return apiClient.downloadFileEncodeToBase64(fileUrl);
    }
}
