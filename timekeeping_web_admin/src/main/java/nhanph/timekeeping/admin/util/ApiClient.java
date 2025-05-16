package nhanph.timekeeping.admin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

/**
 * @Package: proton.face.util
 * @author: nhanph
 * @date: 3/3/2025 2025
 * @Copyright: @nhanph
 */
@Slf4j
@Component
public class ApiClient {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T sendPostRequest(String url, Object requestBody, Class<T> responseType) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        try {
            String jsonRequest = objectMapper.writeValueAsString(requestBody);
            StringEntity entity = new StringEntity(jsonRequest, StandardCharsets.UTF_8);
            httpPost.setEntity(entity);
            log.info("request {} ---- ", url);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String jsonResponse = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                    log.info("{} ---- {}", url, jsonResponse);
                    return objectMapper.readValue(jsonResponse, responseType);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while sending POST request to " + url, e);
        }

        return null;
    }

    public String sendPostRequestV2(String url, Object requestBody) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        try {
            String jsonRequest = objectMapper.writeValueAsString(requestBody);
            StringEntity entity = new StringEntity(jsonRequest, StandardCharsets.UTF_8);
            httpPost.setEntity(entity);
            log.info("request {} ---- ", url);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String jsonResponse = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                    log.info("{} ---- {}", url, jsonResponse);
                    return jsonResponse;
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while sending POST request to " + url, e);
        }
        return null;
    }
    public static String downloadFileEncodeToBase64(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try (InputStream inputStream = connection.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        }
    }
}
