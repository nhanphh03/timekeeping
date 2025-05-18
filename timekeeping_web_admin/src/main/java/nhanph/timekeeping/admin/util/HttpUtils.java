package nhanph.timekeeping.admin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.IOUtils;
import org.primefaces.model.file.UploadedFile;

import org.springframework.stereotype.Service;
import nhanph.timekeeping.admin.constant.StatusConstant;
import nhanph.timekeeping.admin.dto.response.DataResponseFromCheckFacesSearch;
import nhanph.timekeeping.admin.dto.response.DataResponseFromRegisterFaceSearch;
import nhanph.timekeeping.admin.dto.response.DataResponseFromRemoveFacesSearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class HttpUtils implements Serializable {
    private static final String URL_FACE_REG = ConfProperties.getProperty("url.register.face.prt");
    private static final String URL_FACE_SEARCH = ConfProperties.getProperty("url.search.face.prt");

    private static HttpClient httpClient;
    private static HttpPost httpPost;


    public DataResponseFromRegisterFaceSearch callAPIToCreateV2(String capturedImagePath, String peopleId) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            log.info("Face image URL: {}", capturedImagePath);
            HttpGet request = new HttpGet(capturedImagePath);
            HttpResponse response = httpClient.execute(request);
            InputStream content = response.getEntity().getContent();
            String base64Image = Base64.getEncoder().encodeToString(IOUtils.toByteArray(content));

            CloseableHttpClient client = HttpClients.createDefault();
            log.info("Face reg URL: {}", URL_FACE_REG);
            HttpPost httpPost = new HttpPost(URL_FACE_REG);

            long millis = System.currentTimeMillis();
            long timestampSeconds = TimeUnit.MILLISECONDS.toSeconds(millis);

            String json = "{\"image\":\" " + base64Image + "\",\"people_id\":\"" + peopleId
                    + "\",\"created_at\":\"" + timestampSeconds + "\",\"source\":\"" + "Constants.SOURCE" + "\",\"is_live_check\":" + "false" + "}";

            StringEntity entity = new StringEntity(json);
            entity.setContentType("application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(entity);

			HttpResponse responseRegister = client.execute(httpPost);
			HttpEntity entityRegister = responseRegister.getEntity();

			String jsonResponse = EntityUtils.toString(entityRegister, StandardCharsets.UTF_8);
			log.info("Register face response {}", jsonResponse);
			Gson gson = new Gson();
            return gson.fromJson(jsonResponse, DataResponseFromRegisterFaceSearch.class);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return null;
        }
    }



    public DataResponseFromRegisterFaceSearch callAPIToCreateHaveFile(UploadedFile imagePath, String peopleId) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("URL_FACE_CREATE_HAVE_FILE");
            Map<String, Object> map = new HashMap<>();
            map.put("imagePath", imagePath);
            map.put("people_id", peopleId);
            Gson toJson = new Gson();
            String json = toJson.toJson(map);

            StringEntity entity = new StringEntity(json);
            entity.setContentType("application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(entity);

            HttpResponse response1 = client.execute(httpPost);

            HttpEntity entity1 = response1.getEntity();

            // use org.apache.http.util.EntityUtils to read json as string
            String jsonResponse = EntityUtils.toString(entity1, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, DataResponseFromRegisterFaceSearch.class);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return null;
        }
    }

    public DataResponseFromRemoveFacesSearch callAPIToRemove(String peopleId) {

        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPostRemove = new HttpPost("URL_FACE_DEL");

            String json = "{ \"people_id\":\"" + peopleId + "\"}";

            StringEntity entityRemove = new StringEntity(json);
            entityRemove.setContentType("application/json");
            httpPostRemove.setHeader("Content-type", "application/json");
            httpPostRemove.setEntity(entityRemove);

            HttpResponse responseRemove = client.execute(httpPostRemove);

            HttpEntity entityRemoveResponse = responseRemove.getEntity();

            String jsonResponse = EntityUtils.toString(entityRemoveResponse, StandardCharsets.UTF_8);
            log.info("Remove face response {}", jsonResponse);

            Gson gsonRemove = new Gson();
            return gsonRemove.fromJson(jsonResponse, DataResponseFromRemoveFacesSearch.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean checkLiveless(String base64Image) {
        boolean retValue = false;
        httpClient = HttpClients.createDefault();
        httpPost = new HttpPost("Constants.URL_FACE_CHECK_LIVELINESS");

        String json = "{\"image\": \"" + base64Image + "\"}";

        StringEntity jsonParameter = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(jsonParameter);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                String content = EntityUtils.toString(respEntity);
                Gson gson = new Gson();
                JsonElement jelem = gson.fromJson(content, JsonElement.class);
                JsonObject jobj = jelem.getAsJsonObject();
                int code = jobj.get("Code").getAsInt();
                String message = jobj.get("Message").getAsString();

                if (StatusConstant.CODE_CHECK_LIVELESS_SUCCESS == code) {
                    retValue = true;
                    log.info("check live is ok: {}", message);
                } else {
                    log.info("check live is not ok: {}", message);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            httpPost.releaseConnection();
        }
        return retValue;
    }

    public DataResponseFromRegisterFaceSearch callAPIToRegister(String base64Image, String peopleId) {
        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(URL_FACE_REG);
            Map<String, String> map = new HashMap<>();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            map.put("image", base64Image);
            map.put("people_id", peopleId);
            map.put("created_at", timestamp.toString());
            map.put("meta_data", "");
            map.put("source", "nhanphh2003");
            map.put("is_live_check", "false");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(map);

            StringEntity entity = new StringEntity(json);
            entity.setContentType("application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(entity);
            HttpResponse responseRegister = httpClient.execute(httpPost);
            HttpEntity entityRegister = responseRegister.getEntity();
            String jsonResponse = EntityUtils.toString(entityRegister, StandardCharsets.UTF_8);
            log.info("Reg face response {}", jsonResponse);
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse, DataResponseFromRegisterFaceSearch.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            httpPost.releaseConnection();
        }
    }

    public DataResponseFromCheckFacesSearch callAPIToSearch(String source, String base64Image) {

        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(URL_FACE_SEARCH);

            String json = "{ \"source\":\"" + source + "\",\"image\":\"" + base64Image + "\"}";

            StringEntity entity = new StringEntity(json);
            entity.setContentType("application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(entity);

            HttpResponse response = client.execute(httpPost);

            HttpEntity entityResponse = response.getEntity();

            String jsonResponse = EntityUtils.toString(entityResponse, StandardCharsets.UTF_8);
            log.info("callAPIToSearch {}", jsonResponse);

            Gson gsonRemove = new Gson();
            return gsonRemove.fromJson(jsonResponse, DataResponseFromCheckFacesSearch.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}










