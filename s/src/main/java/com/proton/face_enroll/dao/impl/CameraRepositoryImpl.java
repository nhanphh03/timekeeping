package com.proton.face_enroll.dao.impl;

import com.proton.face_enroll.dao.CameraRepository;
import com.proton.face_enroll.model.Camera;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CameraRepositoryImpl implements CameraRepository {

    private final Logger log = LoggerFactory.getLogger(CameraRepositoryImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CameraRepositoryImpl(@Qualifier("enrollJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Camera> getCameraList() {
        List<Camera> list = new ArrayList<>();
        String sql = "SELECT ca.camera_id , ca.camera_name as name, ca.camera_description as description, " +
                " ca.link as rtspURL , ca.camera_group  , ca.face_source, ca.cam3d, " +
                " COALESCE(ca.positionX, 0) AS positionX, COALESCE(ca.positionY, 0) AS positionY" +
                " FROM camera ca WHERE ca.status = '1' ";

        try {
            list = this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Camera.class));
        } catch (Exception var4) {
            this.log.error("Failed to get list camera", var4);
        }

        return list;
    }
}
