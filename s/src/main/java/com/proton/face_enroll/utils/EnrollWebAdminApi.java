package com.proton.face_enroll.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.proton.face_enroll.dto.request.FindPeopleByIdReq;
import com.proton.face_enroll.dto.response.BasicResponse;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Log4j2
public class EnrollWebAdminApi {

    private static CloseableHttpClient httpClient;
    private final Environment env;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public BasicResponse callApiOnOdooToCheckEmployee(FindPeopleByIdReq findPeopleByIdReq) {
        try {
            String urlCheckEmployee = this.env.getProperty("url.check.employee.odoo.prt");
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlCheckEmployee);
            String auth = "QTNB" +
                    findPeopleByIdReq.getPeopleId() +
                    findPeopleByIdReq.getName().replaceAll("\\s+","");

            Map<Object, Object> map = new HashMap<>();
            map.put("peopleId", findPeopleByIdReq.getPeopleId());
            map.put("fullName", findPeopleByIdReq.getName());
            map.put("mobilePhone", findPeopleByIdReq.getPhoneNumber());
            String json = objectMapper.writeValueAsString(map);

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(auth.getBytes());
            String hashString = DatatypeConverter
                    .printHexBinary(hash).toLowerCase();
            log.debug("Hash string: {}", hashString);

            StringEntity entity = new StringEntity(json);
            entity.setContentType("application/json");
            httpPost.setHeaders(new Header[]{
                    new BasicHeader("Content-type", "application/json"),
                    new BasicHeader("Authorization", hashString)
            });
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entityResponse = response.getEntity();
            String jsonResponse = EntityUtils.toString(entityResponse, StandardCharsets.UTF_8);
            log.info("callApiOnOdooToCheckEmployee {}", jsonResponse);
            Gson gsonRemove = new Gson();
            return gsonRemove.fromJson(jsonResponse, BasicResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }


}
