package proton.face.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import proton.face.constant.Constants;
import proton.face.constant.StatusConstant;
import proton.face.entity.CapturedImages;
import proton.face.entity.People;
import proton.face.repository.HistoryCapturedImagesRepository;
import proton.face.util.Utils;

import java.util.List;

@Repository
@Slf4j
public class HistoryCapturedImagesRepositoryImpl implements HistoryCapturedImagesRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public HistoryCapturedImagesRepositoryImpl(@Qualifier("jdbcTemplateConfig") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<CapturedImages> getPeopleCapturedImages(String peopleId, String status, String fromTime, String toTime) {
        String sql = " SELECT  "
                + "    c.id, "
                + "    c.people_id, "
                + "    CONCAT('" + Constants.URL_IMAGE + "', c.path_image) AS url, "
                + "    p.full_name, "
                + "    ct.name, "
                + "    g.group_name, "
                + "    p.Gender, "
                + "    p.date_of_birth, "
                + "    c.captured_time, "
                + "    ca.camera_name, "
                + "    p.status, "
                + "    p.customer_type, "
                + "    p.mobile_phone, "
                + "    c.liveness_status "
                + " FROM "
                + "    people p "
                + "        RIGHT JOIN "
                + "    capturedimages c ON p.people_id = c.people_id "
                + "        LEFT JOIN "
                + "    customertype ct ON p.customer_type = ct.id "
                + "        LEFT JOIN "
                + "    `groups` g ON p.group_id = g.group_id "
                + "        LEFT JOIN "
                + "    camera ca ON ca.camera_id = c.camera_id "
                + " WHERE 1=1 ";


        if (!Utils.isEmpty(peopleId)) {
            sql += "AND c.people_id = :people_id ";
        }
        if (!Utils.isEmpty(status)) {
            if (StatusConstant.CapturedImages.STATUS_TRUE.equals(status)) {
                sql += "AND (c.liveness_status = :liveness_status OR c.liveness_status is null) ";
            } else {
                sql += "AND c.liveness_status = :liveness_status ";
            }
        }
        if (!Utils.isEmpty(fromTime)) {
            sql += "AND DATE(c.captured_time) >= :captured_time ";
        }
        if (!Utils.isEmpty(toTime)) {
            sql += "AND DATE(c.captured_time) <= :captured_time ";
        }
        sql += "ORDER BY c.captured_time desc limit 1000 ";

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("people_id", peopleId);
        parameters.addValue("liveness_status", status);
        parameters.addValue("captured_time", fromTime);

        return jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            CapturedImages images = new CapturedImages();
            images.setId(rs.getInt("id"));
            images.setPeopleId(rs.getString("people_id"));
            images.setCapturedImagePath(rs.getString("url"));
            images.setFullName(rs.getString("full_name"));
            images.setCustomerType(rs.getString("name"));
            images.setGender(rs.getString("Gender"));
            images.setDateOfBirth(rs.getString("date_of_birth"));
            images.setCreatedTime(rs.getString("captured_time"));
            images.setCameraName(rs.getString("camera_name"));
            images.setGroupName(rs.getString("group_name"));
            images.setStatus(rs.getInt("status"));
            images.setCustomerTypeId(rs.getInt("customer_type"));
            images.setMobilePhone(rs.getString("mobile_phone"));
            images.setLivenessStatus(rs.getString("liveness_status"));
            return images;
        });

    }

    @Override
    public String updatePeople(People people, CapturedImages capturedImages) throws Exception {

        String sql = " update people p set full_name = :full_name, date_of_birth = :date_of_birth, customer_type = :customer_type, " +
                " group_id = :group_id, image_path = :image_path, mobile_phone = :mobile_phone, gender = :gender "
                + " where people_id = :people_id ";
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

        parameters = new MapSqlParameterSource();

        String sqlUpdateCapturedImages = "update capturedimages set people_id = :people_id where id = :id";
        parameters.addValue("people_id", people.getPeopleId());
        parameters.addValue("id", capturedImages.getId());

        jdbcTemplate.update(sqlUpdateCapturedImages, parameters);

        return "";
    }

    @Override
    public boolean reRegisterPeople(People people, CapturedImages ci) {

        String sql = " update people p set full_name = :full_name, date_of_birth = :date_of_birth," +
                " customer_type = :customer_type, group_id = :group_id, image_path = :image_path," +
                " mobile_phone = :mobile_phone," +
                " gender = :gender "
                + " where people_id = :people_id ";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("full_name", people.getFullName());
        parameters.addValue("date_of_birth", people.getDateOfBirth());
        parameters.addValue("customer_type", people.getCustomerTypeId());
        parameters.addValue("group_id", people.getGroupId());
        parameters.addValue("image_path", people.getImagePathNoHostV2());
        parameters.addValue("mobile_phone", people.getMobilePhone());
        parameters.addValue("gender", people.getGender());
        parameters.addValue("people_id", people.getPeopleId());
        jdbcTemplate.update(sql, parameters);

        parameters = new MapSqlParameterSource();
        String sqlUpdateCapturedImages = "update capturedimages set people_id = :people_id where id = :id";
        parameters.addValue("people_id", people.getPeopleId());
        parameters.addValue("id", ci.getId());
        jdbcTemplate.update(sqlUpdateCapturedImages, parameters);

        String sqlInsertDetection = " INSERT INTO detection "
                + " (captured_image_path, camera_id, people_id, response_time, recognization_status, " +
                " response_raw, created_time, captured_time, day_first_time, day_noon_time, liveness_status) "
                + " VALUES(:captured_image_path, :camera_id, :people_id, :response_time, :recognization_status," +
                " :response_raw, current_timestamp(), :captured_time, :day_first_time, :day_noon_time, :liveness_status) ";
        parameters = new MapSqlParameterSource();
        parameters.addValue("captured_image_path", people.getImagePathNoHostV2());
        parameters.addValue("camera_id", ci.getCameraId());
        parameters.addValue("people_id", people.getPeopleId());
        parameters.addValue("response_time", null);
        parameters.addValue("recognization_status", null);
        parameters.addValue("response_raw", null);
        parameters.addValue("captured_time", ci.getCreatedTime());
        parameters.addValue("day_first_time", ci.getCreatedTime());
        parameters.addValue("day_noon_time", null);
        parameters.addValue("liveness_status", "TRUE");
        jdbcTemplate.update(sqlInsertDetection, parameters);

        String sqlInsertPeopleRegImage = "insert into people_reg_image (people_id, image) values (:people_id, :image)";
        parameters = new MapSqlParameterSource();
        parameters.addValue("people_id", people.getPeopleId());
        parameters.addValue("image", people.getImagePathNoHostV2());
        jdbcTemplate.update(sqlInsertPeopleRegImage, parameters);
        return true;
    }

    @Override
    public CapturedImages getCapturedImagesById(int id) {
        String sql = " select "
                + " 	c.id, "
                + " 	p.group_id, "
                + " 	c.people_id, "
                + " 	p.full_name, "
                + " 	p.mobile_phone, "
                + " 	p.date_of_birth, "
                + " 	p.gender, "
                + " 	p.customer_type, "
                + "  CONCAT('" + Constants.URL_IMAGE + "', c.path_image) AS url, "
                + " 	c.captured_time, "
                + "  p.status "
                + " from "
                + " 	people p "
                + " right join capturedimages c on "
                + " 	p.people_id = c.people_id "
                + " where "
                + " 	c.id = :id ";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);

        List<CapturedImages> list = jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            CapturedImages images = new CapturedImages();
            images.setId(rs.getInt("id"));
            images.setGroupId(rs.getInt("group_id"));
            images.setPeopleId(rs.getString("people_id"));
            images.setFullName(rs.getString("full_name"));
            images.setMobilePhone(rs.getString("mobile_phone"));
            images.setDateOfBirth(rs.getString("date_of_birth"));
            images.setGender(rs.getString("gender"));
            images.setCustomerTypeId(rs.getInt("customer_type"));
            images.setCapturedImagePath(rs.getString("url"));
            images.setCreatedTime(rs.getString("captured_time"));
            images.setStatus(rs.getInt("status"));
            return images;
        });
        return list.isEmpty() ? null : list.get(0);
    }
}
