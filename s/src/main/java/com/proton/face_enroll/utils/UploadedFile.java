package com.proton.face_enroll.utils;

import java.io.IOException;
import java.io.InputStream;

public interface UploadedFile {
    String getFileName();

    InputStream getInputStream() throws IOException;

    byte[] getContent();

    String getContentType();

    long getSize();

    void write(String filePath) throws Exception;

    void delete() throws IOException;
}
