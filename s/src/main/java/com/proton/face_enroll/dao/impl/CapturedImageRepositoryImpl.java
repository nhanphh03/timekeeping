package com.proton.face_enroll.dao.impl;

import com.proton.face_enroll.dao.CapturedImageRepository;
import com.proton.face_enroll.dto.response.CaptureImageListImageResponse;
import com.proton.face_enroll.dto.response.CapturedImageDetailResponse;
import com.proton.face_enroll.model.CapturedImage;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.proton.face_enroll.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository

public class CapturedImageRepositoryImpl implements CapturedImageRepository {
    private final Logger log = LoggerFactory.getLogger(CapturedImageRepositoryImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CapturedImageRepositoryImpl(@Qualifier("enrollJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NotNull
    private static MapSqlParameterSource getMapSqlParameterSource(CapturedImage capturedImage, String newDate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("path_image", capturedImage.getPathImage());
        params.addValue("detected_status", 1);
        params.addValue("captured_time", newDate);
        params.addValue("people_id", capturedImage.getPeopleId());
        params.addValue("camera_id", capturedImage.getCameraId());
        params.addValue("key_id", capturedImage.getKeyId());
        params.addValue("response_raw", capturedImage.getResponseRaw());
        params.addValue("response_time", capturedImage.getResponseTime());
        return params;
    }

    public long insertCapturedImages(final CapturedImage capturedImage) {
        final String sql = "INSERT capturedimages (path_image,detected_status,captured_time,people_id,camera_id," +
                " key_id,response_raw,response_time) VALUES (:path_image, :detected_status, :captured_time," +
                " :people_id, :camera_id, :key_id, :response_raw, :response_time)";
        long currTime = System.currentTimeMillis();
        String date = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date(currTime));
        final String newDate = date.replace('/', '-');
        KeyHolder key = new GeneratedKeyHolder();
        MapSqlParameterSource params = getMapSqlParameterSource(capturedImage, newDate);

        try {
            this.jdbcTemplate.update(sql, params, key);
        } catch (Exception var9) {
            this.log.error("Failed to insert data to captured image", var9);
        }

        return Objects.requireNonNull(key.getKey()).longValue();
    }

    @Override
    public List<CaptureImageListImageResponse> getAllCapturedImages() {
        String sql = "SELECT CONCAT('" + Constants.URL_IMAGE + "', path_image) AS url, id \n" +
                "FROM capturedimages\n" +
                "WHERE 1 = 1\n" +
                "and (people_id = 0 or people_id IS NULL)\n" +
                "order by captured_time desc\n" +
                "limit 100 ";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        return jdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> {
            CaptureImageListImageResponse response = new CaptureImageListImageResponse();
            response.setImagePath(rs.getString(1));
            response.setId(rs.getInt(2));
            return response;
        });
    }

    @Override
    public CapturedImageDetailResponse getCapturedImageById(long id) {
        String sql = "select * from capturedimages\n" +
                "where id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameterSource, (rs, rowNum) -> {
            CapturedImageDetailResponse capturedImage = new CapturedImageDetailResponse();
            capturedImage.setId(rs.getInt("id"));
            capturedImage.setPathImage(rs.getString("path_image"));
            capturedImage.setCameraId(rs.getInt("camera_id"));
            capturedImage.setPeopleId(rs.getInt("people_id"));
            capturedImage.setCapturedTime(rs.getString("captured_time"));
            return capturedImage;
        });
    }
}
