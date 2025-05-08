package com.proton.face_enroll.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

public class TimestampRowMapper implements RowMapper<Timestamp> {
    public TimestampRowMapper() {
    }

    public Timestamp mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getTimestamp("system_timestamp");
    }
}
