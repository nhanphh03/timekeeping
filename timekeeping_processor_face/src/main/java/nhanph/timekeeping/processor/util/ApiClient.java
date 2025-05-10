package nhanph.timekeeping.processor.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;

/**
 * @Package: nhanph.timekeeping.util
 * @author: nhanph
 * @date: 05/08/2025
 * @Copyright: @nhanph
 */
@Slf4j
@Component
public class ApiClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final int MAX_RETRIES = 3;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration RETRY_DELAY = Duration.ofMillis(500);

    public ApiClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends a POST request with JSON body and custom headers.
     *
     * @param url          Target URL
     * @param requestBody  Request body to serialize as JSON
     * @param responseType Expected response type
     * @param headers      Optional custom headers
     * @param <T>          Response type
     * @return Response object
     * @throws ApiClientException if request fails after retries
     */
    public <T> T sendPostRequest(String url, Object requestBody, Class<T> responseType, Map<String, String> headers) {
        validateInputs(url, requestBody, responseType);
        int attempt = 0;

        while (attempt < MAX_RETRIES) {
            try {
                HttpRequest request = buildPostRequest(url, requestBody, headers);
                log.info("Sending POST request to {} (attempt {})", url, attempt + 1);

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
                if (response.statusCode() >= 200 && response.statusCode() < 300) {
                    String responseBody = response.body();
                    log.info("Response from {}: {}", url, responseBody);
                    return objectMapper.readValue(responseBody, responseType);
                } else {
                    log.warn("Request to {} failed with status {}: {}", url, response.statusCode(), response.body());
                }
            } catch (Exception e) {
                log.error("Error during POST request to {} (attempt {}): {}", url, attempt + 1, e.getMessage());
                if (attempt == MAX_RETRIES - 1) {
                    throw new ApiClientException("Failed to send POST request to " + url + " after " + MAX_RETRIES + " attempts", e);
                }
            }

            attempt++;
            sleepBeforeRetry(attempt);
        }

        throw new ApiClientException("Failed to send POST request to " + url + " after " + MAX_RETRIES + " attempts");
    }

    /**
     * Downloads a file and encodes it to Base64.
     *
     * @param fileUrl URL of the file to download
     * @return Base64 encoded string
     * @throws ApiClientException if download fails
     */
    public String downloadFileEncodeToBase64(String fileUrl) {
        validateUrl(fileUrl);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(fileUrl))
                    .timeout(REQUEST_TIMEOUT)
                    .GET()
                    .build();

            log.info("Downloading file from {}", fileUrl);
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                String encoded = Base64.getEncoder().encodeToString(response.body());
                log.info("Successfully downloaded and encoded file from {}", fileUrl);
                return encoded;
            } else {
                throw new ApiClientException("Failed to download file from " + fileUrl + ": HTTP " + response.statusCode());
            }
        } catch (Exception e) {
            throw new ApiClientException("Error downloading file from " + fileUrl, e);
        }
    }

    private HttpRequest buildPostRequest(String url, Object requestBody, Map<String, String> headers) {
        try {
            String jsonRequest = objectMapper.writeValueAsString(requestBody);
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(REQUEST_TIMEOUT)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest, StandardCharsets.UTF_8));

            if (headers != null) {
                headers.forEach(builder::header);
            }

            return builder.build();
        } catch (Exception e) {
            throw new ApiClientException("Failed to build POST request for " + url, e);
        }
    }

    private void validateInputs(String url, Object requestBody, Class<?> responseType) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        if (requestBody == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }
        if (responseType == null) {
            throw new IllegalArgumentException("Response type cannot be null");
        }
    }

    private void validateUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("File URL cannot be null or empty");
        }
    }

    private void sleepBeforeRetry(int attempt) {
        try {
            Thread.sleep(RETRY_DELAY.toMillis() * attempt);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted during retry delay");
        }
    }

    /**
     * Custom exception for API client errors.
     */
    public static class ApiClientException extends RuntimeException {
        public ApiClientException(String message) {
            super(message);
        }

        public ApiClientException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}