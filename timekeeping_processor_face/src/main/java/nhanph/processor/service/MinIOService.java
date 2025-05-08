package nhanph.timekeeping.processor.service;

import nhanph.timekeeping.processor.dto.minIO.UploadFileDTO;

import java.io.IOException;

public interface MinIOService {
    String uploadImageDetection(UploadFileDTO imageBase64);

    String getBase64Image(String fileUrl);
}
