package com.proton.face_enroll.dao.impl;

import com.proton.face_enroll.dao.EnrollReportResponseRepository;
import com.proton.face_enroll.dto.response.EnrollReportResponse;
import com.proton.face_enroll.model.Department;
import com.proton.face_enroll.model.People;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class EnrollReportResponseRepositoryImpl implements EnrollReportResponseRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EnrollReportResponseRepositoryImpl(@Qualifier("enrollJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<EnrollReportResponse> getListTimekeepingOfPeople(String peopleId, Integer groupId, String fromDate, String toDate, boolean isMorningLate) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        try {

            String sql = " select t.*,  "
                    + " 	trim(concat(if (morning_late < 0, CONCAT('Đi muộn ', abs(morning_late), ' phút. '),''), "
                    + " 	if (t.noon_time is null, CONCAT('Không có giờ trưa. '),''), "
                    + " 	if (t.early_leave > 0, CONCAT('Về sớm ', abs(early_leave), ' phút.'),''))) decription "
                    + " from (SELECT  "
                    + "    p.full_name AS full_name, "
                    + "    CONCAT('https://s3.protontech.vn/face-enroll-proton/', image_path) AS url, "
                    + "    DATE_FORMAT(MIN(d.captured_time), '%H:%i:%s') AS check_in, "
                    + "    DATE_FORMAT(max(d.day_noon_time), '%H:%i:%s') AS noon_time, "
                    + "    DATE_FORMAT(MAX(d.captured_time), '%H:%i:%s') AS check_out, "
                    + "    p.people_id AS people_id, "
                    + "    g.group_id, "
                    + "    g.group_name, "
                    + "    DATE(d.captured_time) AS ngay_cham_cong, "
                    + "    ROUND(TIMESTAMPDIFF(MINUTE, "
                    + "                MIN(d.captured_time), "
                    + "                MAX(d.captured_time)) / 60, "
                    + "            2) AS tong_gio, "
                    + "    TIMESTAMPDIFF(MINUTE, time(MIN(d.captured_time)), (select time(value) from config c " +
                    " where c.code = 'DAY_START' and c.status=1)) morning_late, "
                    + "    TIMESTAMPDIFF(MINUTE, time(MAX(d.captured_time)), (select time(value) from config c " +
                    " where c.code = 'DAY_END' and c.status=1)) early_leave "
                    + "FROM "
                    + "    detection d "
                    + "        INNER JOIN "
                    + "    people p ON p.people_id = d.people_id "
                    + "        LEFT JOIN "
                    + "    `groups` g ON p.group_id = g.group_id WHERE p.status = 1 "
                    + "GROUP BY d.people_id , DATE(d.captured_time) , p.full_name , p.image_path "
                    + "ORDER BY DATE(d.captured_time) DESC,  p.full_name asc) t "
                    + "WHERE 1=1 ";

            if (peopleId != null && !peopleId.trim().isEmpty()) {
                sql += "AND t.people_id like concat('%',:people_id, '%') ";
            }
            if (groupId != null) {
                sql += "AND t.group_id = :group_id ";
            }
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                sql += "AND t.ngay_cham_cong >= :ngay_cham_cong_from_date ";
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                sql += "AND t.ngay_cham_cong <= :ngay_cham_cong_to_date ";
            }
            if (isMorningLate) {
                sql += "AND t.morning_late < 0 ";
            }

            if (peopleId != null && !peopleId.trim().isEmpty()) {
                parameters.addValue("people_id", peopleId);
            }
            if (groupId != null) {
                parameters.addValue("group_id", groupId);
            }
            if (fromDate != null && !fromDate.trim().isEmpty()) {
                parameters.addValue("ngay_cham_cong_from_date", fromDate);
            }
            if (toDate != null && !toDate.trim().isEmpty()) {
                parameters.addValue("ngay_cham_cong_to_date", toDate);
            }

            sql += " LIMIT 5000 ";
            return getSqlInsertTimekeeping(sql, parameters);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    private List<EnrollReportResponse> getSqlInsertTimekeeping(String sql, MapSqlParameterSource parameters) {
        return jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            EnrollReportResponse item = new EnrollReportResponse();
            item.setFullName(rs.getString("full_name"));
            item.setImagePath(rs.getString("url"));
            item.setCheckIn(rs.getString("check_in"));
            item.setNoonTime(rs.getString("noon_time"));
            item.setCheckOut(rs.getString("check_out"));
            item.setPeopleId(rs.getString("people_id"));
            item.setEnrolledDate(rs.getString("ngay_cham_cong"));
            item.setTotalWorkHours(rs.getString("tong_gio"));
            item.setGroupName(rs.getString("group_name"));
            item.setMorningLateV2(rs.getString("morning_late"));
            item.setEarlyLeave(rs.getString("early_leave"));
            item.setDescription(rs.getString("decription"));
            return item;
        });
    }


    public List<People> getPeopeIdList(String peopleId) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            String sql = " SELECT  "
                    + "    people_id, "
                    + "    CONCAT('https://s3.protontech.vn/face-enroll-proton/',image_path) AS url, "
                    + "    full_name, "
                    + "    p.group_id "
                    + "FROM "
                    + "    people p "
                    + "        LEFT JOIN "
                    + "    `groups` g ON p.group_id = g.group_id "
                    + "    where p.status = 1 ";
            if (peopleId != null && !peopleId.trim().isEmpty()) {
                sql += "AND p.people_id like concat('%', :people_id, '%') ";
            }
            if (peopleId != null && !peopleId.trim().isEmpty()) {
                parameters.addValue("people_id", peopleId);
            }

            return jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
                People people = new People();
                Department department = findById(rs.getInt("group_id"));
                people.setPeopleId(rs.getString("people_id"));
                people.setImageBase64(rs.getString("url"));
                people.setName(rs.getString("full_name"));
                people.setGroup(department);
                return people;
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public Department findById(int id) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            String sql = "select * from `groups` where status = 1 and group_id = :group_id ";
            parameters.addValue("group_id", id);
            return jdbcTemplate.queryForObject(sql, parameters, (rs, rowNum) -> {
                Department department = new Department();
                department.setDescription(rs.getString("group_description"));
                department.setId(rs.getString("group_id"));
                department.setName(rs.getString("group_name"));
                department.setCode(rs.getString("group_code"));
                return department;
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
