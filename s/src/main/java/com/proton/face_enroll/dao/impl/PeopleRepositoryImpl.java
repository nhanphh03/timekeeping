package com.proton.face_enroll.dao.impl;

import com.proton.face_enroll.dao.PeopleRepository;
import com.proton.face_enroll.dto.request.FindPeopleByIdReq;
import com.proton.face_enroll.dto.response.PeopleResponse;
import com.proton.face_enroll.mapper.PeopleRowMapper;
import com.proton.face_enroll.model.People;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PeopleRepositoryImpl implements PeopleRepository {

    private final Logger log = LoggerFactory.getLogger(PeopleRepositoryImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Environment env;

    @Autowired
    public PeopleRepositoryImpl(@Qualifier("enrollJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    public People getPeopleById(String peopleId) {
        List<People> listPeople = new ArrayList<>();
        String urlImage = this.env.getProperty("url_face_image");
        String sql = " SELECT p.people_id ,p.full_name , p.date_of_birth ,p.gender ,p.customer_type ," +
                " p.mobile_phone ,p.enable_notification ,p.greeting_text ,p.greeting_audio ,p.group_id ," +
                " g.group_code ,g.group_name ,g.group_description, CONCAT('" + urlImage + "', p.avatar) as avatarPath " +
                " FROM people p LEFT JOIN groups g ON (p.group_id = g.group_id AND g.status = 1) " +
                " WHERE 1 = 1 AND p.people_id = :people_id AND p.status = 1 ";

        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("people_id", peopleId);
            listPeople = this.jdbcTemplate.query(sql, params, new PeopleRowMapper());
        } catch (Exception var6) {
            this.log.error("Failed to get people by id", var6);
        }

        return !listPeople.isEmpty() ? (People) ((List<?>) listPeople).get(0) : null;
    }

    public void createPeople(final People people) {
        final String sql = "INSERT INTO people (people_id,full_name,gender,customer_type,group_id,image_path,status," +
                " enable_notification,avatar) VALUES (:people_id, :full_name, :gender, 1, 4, :image_path, 1, 1, :avatar);";
        KeyHolder key = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("people_id", people.getPeopleId());
        params.addValue("full_name", people.getName());
        params.addValue("gender", people.getGender());
        params.addValue("image_path", people.getAvatarPath());
        params.addValue("avatar", people.getAvatarPath());
        try {
            this.jdbcTemplate.update(sql, params, key);
        } catch (Exception var5) {
            this.log.error("Failed to insert data to people: ", var5);
        }

    }

    @Override
    public List<PeopleResponse> findPeopleById(FindPeopleByIdReq findPeopleRequest) {
        final StringBuilder sql = new StringBuilder("select people_id, full_name, mobile_phone " +
                "from people " +
                "where status = 1 ");
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (!findPeopleRequest.getPeopleId().trim().isEmpty()) {
            sql.append(" and people_id like concat('%', :people_id, '%') ");
            params.addValue("people_id", findPeopleRequest.getPeopleId());
        }
        if (!findPeopleRequest.getName().trim().isEmpty()) {
            sql.append(" and full_name like concat('%', :full_name, '%') ");
            params.addValue("full_name", findPeopleRequest.getName());
        }
        if (!findPeopleRequest.getPhoneNumber().trim().isEmpty()) {
            sql.append(" and mobile_phone = :phone_number ");
            params.addValue("phone_number", findPeopleRequest.getPhoneNumber());
        }
        return this.jdbcTemplate.query(sql.toString(), params, (rs, rowNum) -> {
            PeopleResponse peopleResponse = new PeopleResponse();
            peopleResponse.setPeopleId(rs.getString("people_id"));
            peopleResponse.setFullName(rs.getString("full_name"));
            peopleResponse.setMobilePhone(rs.getString("mobile_phone"));
            return peopleResponse;
        });
    }

    @Override
    public Boolean isExistPeopleByPeopleId(String peopleId) {
        String sql = "SELECT COUNT(*) FROM people WHERE people_id = :people_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("people_id", peopleId);
        Integer count = this.jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != 0;
    }

    @Override
    public Boolean isExistPeopleByPhoneNumber(String mobilePhone) {
        String sql = "SELECT COUNT(*) FROM people WHERE mobile_phone = :mobilePhone";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mobilePhone", mobilePhone);
        Integer count = this.jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != 0;
    }
}

