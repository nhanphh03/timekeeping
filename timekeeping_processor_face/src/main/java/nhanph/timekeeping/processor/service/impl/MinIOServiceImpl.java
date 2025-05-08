package nhanph.timekeeping.processor.service.impl;

import lombok.RequiredArgsConstructor;
import nhanph.timekeeping.processor.dto.minIO.UploadFileDTO;
import nhanph.timekeeping.processor.service.MinIOService;
import nhanph.timekeeping.processor.util.ApiClient;
import nhanph.timekeeping.processor.util.Constants;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MinIOServiceImpl implements MinIOService {
    private final ApiClient apiClient;

    @Override
    public String uploadImageDetection(UploadFileDTO imageBase64) {
        return apiClient.sendPostRequest(
                Constants.SERVICE_MINIO.URL_MINIO + Constants.SERVICE_MINIO.UPLOAD,
                imageBase64,
                String.class,
                null
        );
    }

    @Override
    public String getBase64Image(String fileUrl){
        return apiClient.downloadFileEncodeToBase64(fileUrl);
    }
}
