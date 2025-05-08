package com.proton.face_enroll.mapper;


import com.proton.face_enroll.model.Department;
import com.proton.face_enroll.model.People;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PeopleRowMapper implements RowMapper<People> {
    public PeopleRowMapper() {
    }

    public People mapRow(ResultSet rs, int rowNum) throws SQLException {
        Department dept = new Department();
        dept.setId(rs.getString("group_id"));
        dept.setCode(rs.getString("group_code"));
        dept.setName(rs.getString("group_name"));
        dept.setDescription(rs.getString("group_description"));
        People ppl = new People();
        ppl.setGroup(dept);
        ppl.setPeopleId(rs.getString("people_id"));
        ppl.setName(rs.getString("full_name"));
        ppl.setMobilePhone(rs.getString("mobile_phone"));
        ppl.setEnableNotification(rs.getInt("enable_notification"));
        ppl.setGender(rs.getString("gender"));
        ppl.setGreetingText(rs.getString("greeting_text"));
        ppl.setGreetingAudio(rs.getString("greeting_audio"));
        ppl.setTimemarkCache(System.currentTimeMillis());
        ppl.setAvatarPath(rs.getString("avatarPath"));
        return ppl;
    }
}