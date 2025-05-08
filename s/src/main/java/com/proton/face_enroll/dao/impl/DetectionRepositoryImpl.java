package com.proton.face_enroll.dao.impl;

import com.proton.face_enroll.dao.DetectionRepository;
import com.proton.face_enroll.model.Detection;
import com.proton.face_enroll.model.DetectionItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class DetectionRepositoryImpl implements DetectionRepository {
    private final Logger log = LoggerFactory.getLogger(DetectionRepositoryImpl.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public DetectionRepositoryImpl(@Qualifier("enrollJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    private Environment env;

    public DetectionRepositoryImpl() {
    }

    public List<DetectionItem> getDetectionItem(String key) {
        String keySearch = "%" + key + "%";
        int maxDataSize = Integer.parseInt(Objects.requireNonNull(this.env.getProperty("max_data_size")));
        String dateOffSet = this.env.getProperty("day_off_set");
        String urlImage = this.env.getProperty("url_face_image");
        List<DetectionItem> list = new ArrayList<>();
        String sql = " SELECT d.id, d.people_id, CONCAT('" + urlImage + "', d.captured_image_path) as imagePath," +
                " p.full_name as peopleName, p.Gender, d.created_time, g.group_name, ca.camera_name, ca.camera_id," +
                " p.group_id, d.captured_time, d.day_first_time as firstTimeDay, d.day_noon_time as firstTimeNoon," +
                " CONCAT('" + urlImage + "', p.avatar) as avatarPath  FROM people p " +
                " RIGHT JOIN detection d ON p.people_id = d.people_id " +
                " LEFT JOIN groups g ON p.group_id = g.group_id " +
                " LEFT JOIN camera ca ON ca.camera_id = d.camera_id " +
                " WHERE captured_time >= CURDATE() - INTERVAL "
                + dateOffSet
                + " DAY AND (d.people_id LIKE :keySearch OR p.full_name LIKE :keySearch) " +
                " ORDER BY d.captured_time DESC LIMIT :maxDataSize ";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("keySearch", keySearch);
        params.addValue("maxDataSize", maxDataSize);

        try {
            list = this.jdbcTemplate.query(sql,params, BeanPropertyRowMapper.newInstance(DetectionItem.class));
        } catch (Exception var9) {
            this.log.error("Failed to get list data detection: ", var9);
        }

        return list;
    }

    public long insertDetection(final Detection detect) {
        final String sql = "INSERT detection (captured_image_path, camera_id, people_id, response_time," +
                " recognization_status,response_raw, created_time, captured_time, day_first_time, day_noon_time," +
                " liveness_status, key_id) VALUES (:captured_image_path, :camera_id, :people_id, :response_time," +
                " :recognization_status, :response_raw, :created_time, :captured_time, :day_first_time, :day_noon_time," +
                " :liveness_status, :key_id)";

        MapSqlParameterSource params = getMapSqlParameterSource(detect);
        KeyHolder key = new GeneratedKeyHolder();

        try {
            this.jdbcTemplate.update(sql, params, key);
        } catch (Exception var5) {
            this.log.error("Failed to insert data to detection: ", var5);
            log.error(Arrays.toString(var5.getStackTrace()));
        }

        return Objects.requireNonNull(key.getKey()).longValue();
    }

    @NotNull
    private static MapSqlParameterSource getMapSqlParameterSource(Detection detect) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("captured_image_path", detect.getImagePath());
        params.addValue("camera_id", detect.getCameraId());
        params.addValue("people_id", detect.getPeopleId());
        params.addValue("response_time", detect.getResponseTime());
        params.addValue("recognization_status", detect.getRecognizationStatus());
        params.addValue("response_raw", detect.getResponseRaw());
        params.addValue("created_time", detect.getCreatedTime());
        params.addValue("captured_time", detect.getCapturedTime());
        params.addValue("day_first_time", detect.getFirstTimeCheckIn());
        params.addValue("day_noon_time", detect.getFirstTimeCheckInNoon());
        params.addValue("liveness_status", detect.getLivenessStatus());
        params.addValue("key_id", detect.getKeyId());
        return params;
    }
}

