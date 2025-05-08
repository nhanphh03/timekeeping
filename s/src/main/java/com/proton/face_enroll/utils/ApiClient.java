package com.proton.face_enroll.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
            log.info("request {} ---- {}", url, requestBody.toString());
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String jsonResponse = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                    log.info("{} ---- {}", url, jsonResponse);
                    return objectMapper.readValue(jsonResponse, responseType);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while sending POST request to " + url, e);
        }

        return null;
    }

    public static String downloadFileEncodeToBase64(String fileUrl) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileUrl))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        return Base64.getEncoder().encodeToString(response.body());
    }

}
