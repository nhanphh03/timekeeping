package com.proton.face_enroll.dao.impl;

import com.proton.face_enroll.dao.DepartmentRepository;
import com.proton.face_enroll.model.Department;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private final Logger log = LoggerFactory.getLogger(DepartmentRepositoryImpl.class);

    private NamedParameterJdbcTemplate jdbcTemplate;
    private Environment env;

    @Autowired
    public DepartmentRepositoryImpl(@Qualifier("enrollJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }


    @Override
    public List<Department> findAll() {
        String sql = "select * from `groups` where status = 1;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Department department = new Department();
            department.setId(rs.getString("group_id"));
            department.setCode(rs.getString("group_code"));
            department.setName(rs.getString("group_name"));
            department.setDescription(rs.getString("group_description"));
            return department;
        });
    }
}
