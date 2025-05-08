package com.proton.face_enroll.dao.impl;

import com.proton.face_enroll.dao.RegisterFaceRepository;
import com.proton.face_enroll.dto.odooreq.RegisterFaceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class RegisterFaceRepositoryImpl implements RegisterFaceRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public void registerFace(RegisterFaceRequest request) {
        String insertPeopleSql = "insert into people (people_id, full_name, date_of_birth, " +
                " image_path, mobile_phone, gender, customer_type, group_id) values (:people_id, :full_name, :date_of_birth, " +
                " :image_path, :mobile_phone, :gender,  :customer_type, :group_id)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("people_id", request.getPeopleId());
        parameters.addValue("full_name", request.getFullName());
        parameters.addValue("date_of_birth", request.getDateOfBirth());
        parameters.addValue("image_path", request.getImagePath());
        parameters.addValue("status", 1);
        parameters.addValue("customer_type", Integer.parseInt(request.getType()));
        parameters.addValue("group_id", Integer.parseInt(request.getDepartment()));
        parameters.addValue("mobile_phone", request.getMobilePhone());
        parameters.addValue("gender", request.getGender());
        jdbcTemplate.update(insertPeopleSql, parameters);

        String sqlInsertDetection = " INSERT INTO detection "
                + " (captured_image_path, camera_id, people_id, response_time, recognization_status, response_raw " +
                " , created_time, captured_time, day_first_time, day_noon_time, liveness_status) "
                + " VALUES( current_timestamp() , :camera_id, :people_id, :response_time, :recognization_status," +
                " :response_raw, current_timestamp() , current_timestamp() , current_timestamp(), " +
                " :day_noon_time, :liveness_status) ";
        String sqlInsertPeopleRegImage = "insert into people_reg_image (people_id, image) values (:people_id, :image)";
        parameters = new MapSqlParameterSource();
        parameters.addValue("camera_id", 1);
        parameters.addValue("people_id", request.getPeopleId());
        parameters.addValue("response_time", null);
        parameters.addValue("recognization_status", null);
        parameters.addValue("response_raw", null);
        parameters.addValue("day_noon_time", null);
        parameters.addValue("liveness_status", "TRUE");
        parameters.addValue("image", request.getImagePath());
        jdbcTemplate.update(sqlInsertDetection, parameters);
        jdbcTemplate.update(sqlInsertPeopleRegImage, parameters);
    }
}
