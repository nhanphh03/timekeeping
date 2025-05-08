package com.proton.face_enroll.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.proton.face_enroll.dao.PeopleRepository;
import com.proton.face_enroll.model.People;
import com.proton.face_enroll.utils.Constants.SERVICE_FACE;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component

public class CallApiFace {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallApiFace.class);

    private final Environment env;

    private final PeopleRepository peopleRepository;
    private final MinioUtil minioUtil;

    public CallApiFace(Environment env, PeopleRepository peopleRepository, MinioUtil minioUtil) {
        this.env = env;
        this.peopleRepository = peopleRepository;
        this.minioUtil = minioUtil;
    }

    public void sendSearchFace(People people) {
        String url = this.env.getProperty("service.face.url");
        String endpoint = SERVICE_FACE.SEARCH;
        String faceSource = this.env.getProperty("face.source");
        String statusRtr;
        float initScore = 0.75F;
        String peopleId = "";
        HttpPost post = null;

        JsonObject jo;
        try {
            String urlSearch = url + endpoint;
            post = new HttpPost(urlSearch);
            post.addHeader("Content-Type", "application/json");
            jo = new JsonObject();
            jo.addProperty("source", faceSource);
            jo.addProperty("image", people.getImageBase64());
            String jsonBody = GsonUtil.getGson().toJson(jo);
            LOGGER.info("Sending request {} ...\n body data: {}", urlSearch, jsonBody);
            StringEntity entity = new StringEntity(jsonBody);
            post.setEntity(entity);
            HttpResponse response = HttpUtil.getClient().execute(post);
            String jsonString = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            Map result = (new ObjectMapper()).readValue(jsonString, HashMap.class);
            if (result != null) {
                statusRtr = result.get("status").toString();
                if (statusRtr.contains("SUCCESS")) {
                    JSONObject jsnobject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsnobject.getJSONArray("data");
                    if (jsonArray.isEmpty()) {
                        this.registerFace(people);
                    } else {
                        for(int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject explrObject = jsonArray.getJSONObject(i);
                            String peopleIdV2 = explrObject.getString("people_id");
                            float score = (float)explrObject.getDouble("score");
                            if (score > initScore) {
                                initScore = score;
                                peopleId = peopleIdV2;
                            }

                            LOGGER.info(peopleId);
                        }
                    }
                }
            }

            LOGGER.info("Response from search face: {}", jsonString);
        } catch (Exception var27) {
            LOGGER.error("Fail to call api search face!", var27);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }

        }
    }

    public void registerFace(People people) {
        String url = this.env.getProperty("service.face.url");
        String endpoint = SERVICE_FACE.REGISTER_FACE;
        String faceSource = this.env.getProperty("face.source");
        String statusRtr = "";
        HttpPost post = null;
        boolean check = false;

        JsonObject jo;
        try {
            String urlSearch = url + endpoint;
            post = new HttpPost(urlSearch);
            post.addHeader("Content-Type", "application/json");
            jo = new JsonObject();
            jo.addProperty("people_id", people.getPeopleId());
            jo.addProperty("created_at", System.currentTimeMillis());
            jo.addProperty("is_live_check", false);
            jo.addProperty("source", faceSource);
            jo.addProperty("image", people.getImageBase64());
            String jsonBody = GsonUtil.getGson().toJson(jo);
            LOGGER.info("Sending request {} ...\n body data: {}", urlSearch, jsonBody);
            StringEntity entity = new StringEntity(jsonBody);
            post.setEntity(entity);
            HttpResponse response = HttpUtil.getClient().execute(post);
            String jsonString = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            Map result = (new ObjectMapper()).readValue(jsonString, HashMap.class);
            if (result != null) {
                statusRtr = result.get("status").toString();
                if (statusRtr.contains("SUCCESS")) {
                    String uuid = UUID.randomUUID().toString();
                    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                    String pathAvatar = "face-avatar-dkc/" + time + "/" + people.getPeopleId() + "_" + uuid + ".jpg";
                    String s = minioUtil.uploadImage(pathAvatar, Base64.getDecoder().decode(people.getImageBase64()));
                    LOGGER.info(s);
                    people.setAvatarPath(pathAvatar);
                    check = true;
                }
            }

            if (check) {
                peopleRepository.createPeople(people);
            }

            LOGGER.info("Response from search face: ");
        } catch (Exception var23) {
            LOGGER.error("Fail to call api search face!", var23);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }

        }
    }
}

