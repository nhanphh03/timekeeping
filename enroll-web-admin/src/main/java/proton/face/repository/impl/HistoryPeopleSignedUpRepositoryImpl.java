package proton.face.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import proton.face.constant.Constants;
import proton.face.constant.StatusConstant;
import proton.face.entity.Detection;
import proton.face.entity.People;
import proton.face.repository.HistoryPeopleSignedUpRepository;
import proton.face.util.Utils;

import java.util.List;

@Repository
@Slf4j
public class HistoryPeopleSignedUpRepositoryImpl implements HistoryPeopleSignedUpRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public HistoryPeopleSignedUpRepositoryImpl(@Qualifier("jdbcTemplateConfig") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Detection> getPeopleSignedUp(String peopleId, String status, String fromTime, String toTime) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String sql = " SELECT  "
                + "    d.id, "
                + "    d.people_id, "
                + "    CONCAT('" + Constants.URL_IMAGE + "', d.captured_image_path) AS url, "
                + "    p.full_name, "
                + "    ct.name, "
                + "    p.Gender, "
                + "    p.date_of_birth, "
                + "    d.created_time, "
                + "    g.group_name, "
                + "    ca.camera_name, "
                + "    p.customer_type, "
                + "    p.mobile_phone, "
                + "    p.group_id, "
                + "    d.liveness_quick, "
                + "    d.day_first_time, "
                + "    d.day_noon_time "
                + "FROM "
                + "    people p "
                + "        RIGHT JOIN "
                + "    detection d ON p.people_id = d.people_id "
                + "        LEFT JOIN "
                + "    customertype ct ON p.customer_type = ct.id "
                + "        LEFT JOIN "
                + "    `groups` g ON p.group_id = g.group_id "
                + "        LEFT JOIN "
                + "    camera ca ON ca.camera_id = d.camera_id "
                + "where (liveness_status <> 'FALSE' and liveness_status <> 'false') ";
        if (!Utils.isEmpty(peopleId)) {
            sql += "AND d.people_id = :people_id ";
            parameters.addValue("people_id", peopleId);
        }
        if (!Utils.isEmpty(status)) {
            if (StatusConstant.Detection.STATUS_TRUE.equals(status)) {
                sql += "AND (d.liveness_quick = :liveness_quick OR d.liveness_quick is null) ";
            } else {
                sql += "AND d.liveness_quick = :liveness_quick ";
            }
            parameters.addValue("liveness_quick", status);
        }
        if (!Utils.isEmpty(fromTime)) {
            sql += "AND DATE(d.created_time)  >= :created_time ";
            parameters.addValue("created_time", fromTime);
        }
        if (!Utils.isEmpty(toTime)) {
            sql += "AND DATE(d.created_time) <= :created_time ";
            parameters.addValue("created_time", toTime);
        }
        sql += "ORDER BY d.created_time desc limit 1000 ";

        return jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            Detection detection = new Detection();
            detection.setId(rs.getLong("id"));
            detection.setPeopleId(rs.getString("people_id"));
            detection.setCapturedImagePath(rs.getString("url"));
            detection.setFullName(rs.getString("full_name"));
            detection.setCustomerType(rs.getString("name"));
            detection.setGender(rs.getString("Gender"));
            detection.setDateOfBirth(rs.getString("date_of_birth"));
            detection.setCreatedTime(rs.getString("created_time"));
            detection.setCameraName(rs.getString("camera_name"));
            detection.setGroupName(rs.getString("group_name"));
            detection.setCustomerTypeId(rs.getInt("customer_type"));
            detection.setMobilePhone(rs.getString("mobile_phone"));
            detection.setGroupId(rs.getInt("group_id"));
            detection.setLivenessStatus(rs.getString("liveness_quick"));
            detection.setDayFirstTime(rs.getString("day_first_time"));
            detection.setDayNoonTime(rs.getString("day_noon_time"));
            return detection;
        });
    }

    @Override
    public String updatePeople(People people, Detection detection) throws Exception {

        String sql = " update people p set full_name = :full_name, date_of_birth = :date_of_birth," +
                " customer_type = :customer_type, group_id = :group_id, image_path = :image_path," +
                " mobile_phone = :mobile_phone, gender = :gender where people_id = :people_id ";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("full_name", people.getFullName());
        parameters.addValue("date_of_birth", people.getBirthday());
        parameters.addValue("customer_type", people.getCustomerTypeId());
        parameters.addValue("group_id", people.getGroupId());
        parameters.addValue("image_path", people.getImagePathNoHostV2());
        parameters.addValue("mobile_phone", people.getMobilePhone());
        parameters.addValue("gender", people.getGender());
        parameters.addValue("people_id", people.getPeopleId());
        jdbcTemplate.update(sql, parameters);


        String sqlUpdateDetection = " update detection set people_id = :people_id, created_time = :created_time " +
                "where id = :id ";
        parameters = new MapSqlParameterSource();
        parameters.addValue("people_id", detection.getPeopleId());
        parameters.addValue("created_time", detection.getCreatedTime());
        parameters.addValue("id", detection.getId());
        jdbcTemplate.update(sqlUpdateDetection, parameters);

        return "";
    }

    @Override
    public Detection getPeopleSignedUpById(int id) {

        String sql = " SELECT  "
                + "    d.id, "
                + "    d.people_id, "
                + "    CONCAT('" + Constants.URL_IMAGE + "', d.captured_image_path) AS url, "
                + "    p.full_name, "
                + "    ct.name, "
                + "    p.Gender, "
                + "    p.date_of_birth, "
                + "    d.created_time, "
                + "    g.group_name, "
                + "    ca.camera_name, "
                + "    p.customer_type, "
                + "    p.mobile_phone, "
                + "    p.group_id, "
                + "    d.liveness_quick, "
                + "    d.day_first_time, "
                + "    d.day_noon_time "
                + " FROM "
                + "    people p "
                + "        RIGHT JOIN "
                + "    detection d ON p.people_id = d.people_id "
                + "        LEFT JOIN "
                + "    customertype ct ON p.customer_type = ct.id "
                + "        LEFT JOIN "
                + "    `groups` g ON p.group_id = g.group_id "
                + "        LEFT JOIN "
                + "    camera ca ON ca.camera_id = d.camera_id "
                + "where (liveness_status <> 'FALSE' and liveness_status <> 'false') "
                + "    and d.id = :id ";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<Detection> list = jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            Detection detection = new Detection();
            detection.setId(rs.getLong("id"));
            detection.setPeopleId(rs.getString("people_id"));
            detection.setCapturedImagePath(rs.getString("url"));
            detection.setFullName(rs.getString("full_name"));
            detection.setCustomerType(rs.getString("name"));
            detection.setGender(rs.getString("Gender"));
            detection.setDateOfBirth(rs.getString("date_of_birth"));
            detection.setCreatedTime(rs.getString("created_time"));
            detection.setCameraName(rs.getString("camera_name"));
            detection.setGroupName(rs.getString("group_name"));
            detection.setCustomerTypeId(rs.getInt("customer_type"));
            detection.setMobilePhone(rs.getString("mobile_phone"));
            detection.setGroupId(rs.getInt("group_id"));
            detection.setLivenessStatus(rs.getString("liveness_quick"));
            detection.setDayFirstTime(rs.getString("day_first_time"));
            detection.setDayNoonTime(rs.getString("day_noon_time"));
            return detection;
        });
        return list.isEmpty() ? null : list.get(0);
    }
}
