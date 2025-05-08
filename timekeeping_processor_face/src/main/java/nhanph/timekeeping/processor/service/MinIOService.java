package nhanph.timekeeping.processor.service;

import nhanph.timekeeping.processor.dto.minIO.UploadFileDTO;

public interface MinIOService {
    void uploadImageDetection(UploadFileDTO imageBase64);

    String getBase64Image(String fileUrl);
}
