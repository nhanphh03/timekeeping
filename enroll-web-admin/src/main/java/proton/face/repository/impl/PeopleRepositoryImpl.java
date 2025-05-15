package proton.face.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import proton.face.constant.Constants;
import proton.face.entity.People;
import proton.face.repository.PeopleRepository;
import proton.face.service.MinioService;
import proton.face.util.StringUtils;
import proton.face.util.Utility;
import proton.face.util.Utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Repository
@Slf4j
public class PeopleRepositoryImpl implements PeopleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final DataSource dataSource;
    private final MinioService minioService;

    public PeopleRepositoryImpl(@Qualifier("jdbcTemplateConfig") NamedParameterJdbcTemplate jdbcTemplate,
                                @Qualifier("dataSourceConfig") DataSource dataSource,
                                MinioService minioService) {
        this.jdbcTemplate = jdbcTemplate;
        this.minioService = minioService;
        this.dataSource = dataSource;
    }


    @Override
    public List<People> getListPeople() {

        String sql = " SELECT  "
                + "    p.id, "
                + "    people_id, "
                + "    CONCAT('" + Constants.URL_IMAGE + "',image_path) AS url, "
                + "    full_name, "
                + "    ct.name, "
                + "    Gender, "
                + "    date_of_birth, "
                + "    Last_checkin_time, "
                + "    g.group_name, "
                + "    p.mobile_phone, "
                + "    p.group_id, "
                + "    p.customer_type, "
                + "    p.status "
                + "FROM "
                + "    people p "
                + "        INNER JOIN "
                + "    customertype ct ON ct.id = p.customer_type "
                + "        LEFT JOIN "
                + "    `groups` g ON p.group_id = g.group_id "
                + "    where p.status = 1 "
                + "    order by p.status "
                + "    limit 1000 ";

        return jdbcTemplate.query(sql, new MapSqlParameterSource(), (rs, rowNum) -> {
            People people = new People();
            people.setId(rs.getLong("id"));
            people.setPeopleId(rs.getString("people_id"));
            people.setImagePath(rs.getString("url"));
            people.setFullName(rs.getString("full_name"));
            people.setCustomerType(rs.getString("name"));
            people.setGender(rs.getString("Gender"));
            people.setDateOfBirth(rs.getString("date_of_birth"));
            people.setLastCheckinTime(rs.getString("Last_checkin_time"));
            people.setGroupName(rs.getString("group_name"));
            people.setMobilePhone(rs.getString("mobile_phone"));
            people.setGroupId(rs.getInt("group_id"));
            people.setCustomerTypeId(rs.getInt("customer_type"));
            people.setStatus(rs.getInt("status"));
            return people;
        });
    }


    @Override
    public void updatePeople(People people, boolean registerFace) throws Exception {
        String sql = " update people p set full_name = :full_name, date_of_birth = :date_of_birth," +
                " customer_type = :customer_type, group_id = :group_id, mobile_phone = :mobile_phone," +
                " gender = :gender, status = 1 " +
                " where people_id = :people_id ";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("people_id", people.getPeopleId());
        parameters.addValue("full_name", people.getFullName());
        parameters.addValue("date_of_birth", people.getDateOfBirth());
        parameters.addValue("customer_type", people.getCustomerTypeId());
        parameters.addValue("group_id", people.getGroupId());
        parameters.addValue("mobile_phone", people.getMobilePhone());
        parameters.addValue("gender", people.getGender());

        jdbcTemplate.update(sql, parameters);
    }

    @Override
    public boolean isExistPeople(String peopleId) {
        String sql = "SELECT 1 FROM people WHERE people_id = :people_id and status = 1 ";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("people_id", peopleId);
        try {
            return jdbcTemplate.queryForObject(sql, parameters, Integer.class) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void updateExistPeople(String peopleId) {
        String sql = " UPDATE people SET status = 1 WHERE people_id = :people_id AND status = 2 ";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("people_id", peopleId);
        jdbcTemplate.update(sql, parameters);
    }

    @Override
    public boolean registerPeople(People people) {
        String sql = "insert into people (people_id, full_name, date_of_birth, customer_type," +
                " group_id , image_path, mobile_phone, gender) " +
                "values (:people_id, :full_name, :date_of_birth, :customer_type, :group_id, :image_path, :mobile_phone, :gender)"
                + " on duplicate key update "
                + " full_name=values(full_name),"
                + " date_of_birth=values(date_of_birth),"
                + " mobile_phone=values(mobile_phone),"
                + " gender=values(gender),"
                + " status=values(status),"
                + " image_path=values(image_path)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("people_id", people.getPeopleId());
        parameters.addValue("full_name", people.getFullName());
        parameters.addValue("date_of_birth", people.getBirthday());
        parameters.addValue("customer_type", people.getCustomerTypeId());
        parameters.addValue("group_id", people.getGroupId());
        parameters.addValue("image_path", people.getImagePath());
        parameters.addValue("mobile_phone", people.getMobilePhone());
        parameters.addValue("gender", people.getGender());
        parameters.addValue("gender", people.getStatus());
        return jdbcTemplate.update(sql, parameters) != 0;
    }

    @Override
    public boolean registerPeopleRegImage(String peopleId, List<List<String>> imageList) {
        PreparedStatement ps = null;
        Connection connection = null;
        boolean result = false;
        String imagePath;
        try {
            connection = this.dataSource.getConnection();
            String sql = "insert into people_reg_image (people_id, image) values (?, ?)";
            ps = connection.prepareStatement(sql);
            for (List<String> list : imageList) {
                for (String image : list) {
                    imagePath = minioService.uploadImage(peopleId, Base64.getDecoder().decode(image));
                    ps.setString(1, peopleId);
                    ps.setString(2, imagePath);
                    ps.addBatch();
                }
            }
            ps.executeBatch();

            result = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            Utility.closeObject(ps);
        }
        return result;
    }

    @Override
    public boolean registerPeopleRegImage(String peopleId, String imagePath) {
        String sql = "insert into people_reg_image (people_id, image) values (:people_id, :image)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("people_id", peopleId);
        parameters.addValue("image", imagePath);
        return jdbcTemplate.update(sql, parameters) != 0;
    }

    @Override
    public boolean registerPeopleWhitelist(String peopleId, int userId) {

        String sql = "insert into people_whitelist (user_id, people_id) values (:user_id, :people_id)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("user_id", userId);
        parameters.addValue("people_id", peopleId);

        return jdbcTemplate.update(sql, parameters) != 0;
    }

    @Override
    public List<People> getPeopleList() {

        String sql = " select people_id , full_name, date_of_birth , Gender , customer_type ," +
                " group_id ,mobile_phone from people p where status <> 0 ";

        return jdbcTemplate.query(sql, new MapSqlParameterSource(), (rs, rowNum) -> {
            People people = new People();
            people.setPeopleId(rs.getString("people_id"));
            people.setFullName(rs.getString("full_name"));
            people.setDateOfBirth(rs.getString("date_of_birth"));
            people.setGender(rs.getString("Gender"));
            people.setCustomerType(rs.getString("customer_type"));
            people.setGroupId(rs.getInt("group_id"));
            people.setMobilePhone(rs.getString("mobile_phone"));
            return people;
        });

    }

    @Override
    public List<People> searchPeopleList(String peopleId, String fullName, String mobilePhone) {

        String sql = " SELECT  "
                + "    p.id, "
                + "    people_id, "
                + "    CONCAT('" + Constants.URL_IMAGE + "',image_path) AS url, "
                + "    full_name, "
                + "    ct.name, "
                + "    gender, "
                + "    date_of_birth, "
                + "    Last_checkin_time, "
                + "    g.group_name, "
                + "    p.mobile_phone, "
                + "    p.group_id, "
                + "    p.customer_type, "
                + "    p.status "
                + " FROM "
                + "    people p "
                + "        INNER JOIN "
                + "    customertype ct ON ct.id = p.customer_type "
                + "        LEFT JOIN "
                + "    `groups` g ON p.group_id = g.group_id "
                + "    where p.status = 1 ";

        if (!StringUtils.isEmpty(peopleId)) {
            sql += " and people_id = :people_id ";


        }
        if (!StringUtils.isEmpty(fullName)) {
            sql += " and full_name like CONCAT('%',  :full_name,  '%')";


        }
        if (!StringUtils.isEmpty(mobilePhone)) {
            sql += " and mobile_phone = :mobile_phone ";
        }
        sql += " order by p.status ";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("people_id", peopleId);
        parameters.addValue("full_name", fullName);
        parameters.addValue("mobile_phone", mobilePhone);

        return jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            People people = new People();
            people.setId(rs.getLong("id"));
            people.setPeopleId(rs.getString("people_id"));
            people.setImagePath(rs.getString("url"));
            people.setFullName(rs.getString("full_name"));
            people.setCustomerType(rs.getString("name"));
            people.setGender(rs.getString("gender"));
            people.setDateOfBirth(rs.getString("date_of_birth"));
            people.setLastCheckinTime(rs.getString("Last_checkin_time"));
            people.setGroupName(rs.getString("group_name"));
            people.setMobilePhone(rs.getString("mobile_phone"));
            people.setGroupId(rs.getInt("group_id"));
            people.setCustomerTypeId(rs.getInt("customer_type"));
            people.setStatus(rs.getInt("status"));
            return people;
        });
    }

    @Override
    public List<String> getLast5Detection(String peopleId) {
        String sql = " select captured_image_path from detection d where people_id = :people_id "
                + " and liveness_status <> 'FALSE' and liveness_status <> 'false' "
                + " order by created_time desc limit 5 ";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("people_id", peopleId);
        return jdbcTemplate.query(sql, parameters, (rs, rowNum) -> rs.getString("captured_image_path"));
    }
}
