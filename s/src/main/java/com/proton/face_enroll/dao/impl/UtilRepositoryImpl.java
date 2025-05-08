package com.proton.face_enroll.dao.impl;

import com.proton.face_enroll.dao.UtilRepository;
import com.proton.face_enroll.mapper.TimestampRowMapper;
import com.proton.face_enroll.model.Config;
import com.proton.face_enroll.model.LivenessHistory;
import com.proton.face_enroll.model.Timesheet;
import com.proton.face_enroll.model.Whitelist;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UtilRepositoryImpl implements UtilRepository {

    private final Logger log = LoggerFactory.getLogger(UtilRepositoryImpl.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UtilRepositoryImpl(@Qualifier("enrollJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UtilRepositoryImpl() {
    }

    public List<Timesheet> getTimesheetSQL(String fromTime, String toTime) {
        List<Timesheet> list = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String newFromTime = dtf.format(now) + " " + fromTime;
        String newToTime = dtf.format(now) + " " + toTime;
        String sql = "SELECT d.people_id, date_format(min(d.captured_time), '%Y-%m-%d %H:%i:%s') AS first_time" +
                " FROM detection d WHERE d.captured_time BETWEEN :newFromTime AND :newToTime" +
                " GROUP BY d.people_id ORDER BY d.people_id DESC ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("newFromTime", newFromTime);
        params.addValue("newToTime", newToTime);

        try {
            list = this.jdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(Timesheet.class));
        } catch (Exception var10) {
            this.log.error("Failed to get time sheet", var10);
        }

        return list;
    }

    public Integer insertLivenessHistory(final LivenessHistory history) {
        String sql = " INSERT INTO liveness_history (name, status, action_time, camera_id, raw_response," +
                " color_base64, depth_base64, node)  VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
        return this.jdbcTemplate.execute(sql, pst -> {
            pst.setString(1, history.getName() == null ? "" : history.getName());
            pst.setString(2, history.getStatus());
            pst.setTimestamp(3, new Timestamp(history.getActionTime().getTime()));
            pst.setString(4, history.getCameraId());
            pst.setString(5, history.getRawResponse());
            pst.setString(6, history.getColorBase64());
            pst.setString(7, history.getDepthBase64());
            pst.setString(8, history.getNode());
            return pst.executeUpdate();
        });
    }

    public Timestamp getCurrentSystemTime() {
        List<Timestamp> list = new ArrayList<>();
        String sql = "SELECT date_format(current_timestamp(), '%Y-%m-%d %H:%i:%s') AS system_timestamp";

        try {
            list = this.jdbcTemplate.query(sql, new TimestampRowMapper());
        } catch (Exception var4) {
            this.log.error("Failed to get current time", var4);
        }
        log.info("Current system time: {}", list.get(0));
        return !list.isEmpty() ? (Timestamp)((List<?>)list).get(0) : null;
    }

    public List<Whitelist> getListWhiteList() {
        List<Whitelist> list = new ArrayList<>();
        String sql = " SELECT w.id, w.user_id, w.people_id, w.created_time, w.status" +
                "  FROM people_whitelist w  WHERE w.status = '1' ";

        try {
            list = this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Whitelist.class));
        } catch (Exception var4) {
            this.log.error("Failed to get people white list", var4);
        }

        return list;
    }

    public List<Config> getListConfig() {
        List<Config> list = new ArrayList<>();
        String sql = "SELECT c.id, c.code, c.type, c.name, c.description, c.value, c.order_no, c.status" +
                " FROM config c WHERE c.status = '1' ORDER BY COALESCE (c.order_no, 99999) ";

        try {
            list = this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Config.class));
        } catch (Exception var4) {
            log.error(var4.getMessage());
        }

        return list;
    }
}

