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
        String sql = "SELECT c.id,\n" +
                "       c.customer_code,\n" +
                "       CONCAT('" + Constants.URL_IMAGE +
                "', d.image_path) AS url,\n" +
                "       p.full_name,\n" +
                "       ct.name                                        as customer_type,\n" +
                "       g.name                                         as customer_group,\n" +
                "       p.Gender,\n" +
                "       p.date_of_birth,\n" +
                "       c.captured_time,\n" +
                "       p.status,\n" +
                "       d.camera_code,\n" +
                "       p.customer_type,\n" +
                "       p.mobile_phone\n" +
                "FROM customer p\n" +
                "         RIGHT JOIN\n" +
                "     captured_images c ON p.customer_code = c.customer_code\n" +
                "         LEFT JOIN\n" +
                "     customer_type ct ON p.customer_type = ct.id\n" +
                "         JOIN\n" +
                "     customer_group g ON p.group_id = g.id\n" +
                "          JOIN\n" +
                "     detection d ON p.customer_code = d.customer_code\n" +
                "\n" +
                "WHERE 1 = 1";


        if (!Utils.isEmpty(peopleId)) {
            sql += "AND c.customer_code = :people_id ";
        }
        if (!Utils.isEmpty(fromTime)) {
            sql += "AND c.captured_time >= :captured_time ";
        }
        if (!Utils.isEmpty(toTime)) {
            sql += "AND c.captured_time <= :captured_time ";
        }
        sql += " ORDER BY c.captured_time desc limit 200 ";

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("people_id", peopleId);
        parameters.addValue("captured_time", fromTime);

        return jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            CapturedImages images = new CapturedImages();
            images.setId(rs.getInt("id"));
            images.setPeopleId(rs.getString("customer_code"));
            images.setCapturedImagePath(rs.getString("url"));
            images.setFullName(rs.getString("full_name"));
            images.setCustomerType(rs.getString("customer_type"));
            images.setGender(rs.getString("gender"));
            images.setDateOfBirth(rs.getString("date_of_birth"));
            images.setCreatedTime(rs.getString("captured_time"));
            images.setCameraName(rs.getString("camera_code"));
            images.setGroupName(rs.getString("customer_group"));
            images.setStatus(rs.getInt("status"));
            images.setMobilePhone(rs.getString("mobile_phone"));
            return images;
        });

    }

    @Override
    public String updatePeople(People people, CapturedImages capturedImages) throws Exception {

        String sql = " update customer p set full_name = :full_name, date_of_birth = :date_of_birth, customer_type = :customer_type, " +
                " group_id = :group_id, image_path = :image_path, mobile_phone = :mobile_phone, gender = :gender "
                + " where customer_code = :people_id ";
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
        return "";
    }

    @Override
    public boolean reRegisterPeople(People people, CapturedImages ci) {

        String sql = "  update customer p set full_name = :full_name, date_of_birth = :date_of_birth,\n" +
                "                 customer_type = :customer_type, group_id = :group_id, image_path = :image_path,\n" +
                "                 mobile_phone = :mobile_phone,\n" +
                "                 gender = :gender \n" +
                "                 where customer_code = :people_id ";
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
        return true;
    }

    @Override
    public CapturedImages getCapturedImagesById(int id) {
        String sql = " select c.id,\n" +
                "       p.group_id,\n" +
                "       c.customer_code,\n" +
                "       p.full_name,\n" +
                "       p.mobile_phone,\n" +
                "       p.date_of_birth,\n" +
                "       p.gender,\n" +
                "       p.customer_type,\n" +
                "       CONCAT(' + Constants.URL_IMAGE ', c.path_image) AS url,\n" +
                "       c.captured_time,\n" +
                "       p.status\n" +
                "from customer p\n" +
                "         right join captured_images c on\n" +
                "    p.customer_code = c.customer_code\n" +
                "where c.id = :id ";
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
