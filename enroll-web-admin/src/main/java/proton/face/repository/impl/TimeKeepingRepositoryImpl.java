package proton.face.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import proton.face.constant.Constants;
import proton.face.entity.People;
import proton.face.entity.Timekeeping;
import proton.face.repository.TimeKeepingRepository;
import proton.face.util.Utils;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Repository
@Slf4j
public class TimeKeepingRepositoryImpl implements TimeKeepingRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TimeKeepingRepositoryImpl (@Qualifier("jdbcTemplateConfig")NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Timekeeping> getListTimekeepingOfPeople(String peopleId, int groupId, String fromDate,
                                                        String toDate, boolean isMorningLate) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        try {

            String sql = " select t.*,  "
                    + " 	trim(concat(if (morning_late < 0, CONCAT('Đi muộn ', abs(morning_late), ' phút. '),''), "
                    + " 	if (t.noon_time is null, CONCAT('Không có giờ trưa. '),''), "
                    + " 	if (t.early_leave > 0, CONCAT('Về sớm ', abs(early_leave), ' phút.'),''))) decription "
                    + " from (SELECT  "
                    + "    p.full_name AS full_name, "
                    + "    CONCAT('" + Constants.URL_IMAGE + "', image_path) AS url, "
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
                    + "    `groups` g ON p.group_id = g.group_id "
                    + "GROUP BY d.people_id , DATE(d.captured_time) , p.full_name , p.image_path "
                    + "ORDER BY DATE(d.captured_time) DESC, d.people_id  desc) t "
                    + "WHERE 1=1 ";

            if (!Utils.isEmpty(peopleId)) {
                sql += "AND t.people_id = :people_id ";
            }
            if (groupId != 0) {
                sql += "AND t.group_id = :group_id ";
            }
            if (!Utils.isEmpty(fromDate)) {
                sql += "AND t.ngay_cham_cong >= :ngay_cham_cong " ;
            }
            if (!Utils.isEmpty(toDate)) {
                sql += "AND t.ngay_cham_cong <= :ngay_cham_cong " ;
            }
            if (isMorningLate) {
                sql += "AND t.morning_late < 0 " ;
            }

            if (!Utils.isEmpty(peopleId)) {
                parameters.addValue("people_id", peopleId);
            }
            if (groupId != 0) {
                parameters.addValue("group_id", groupId);
            }
            if (!Utils.isEmpty(fromDate)) {
                parameters.addValue("ngay_cham_cong", fromDate);
            }
            if (!Utils.isEmpty(toDate)) {
                parameters.addValue("ngay_cham_cong", toDate);
            }

            return getSqlInsertTimekeeping(sql, parameters);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Timekeeping> getListTimekeeping() {
        try {
            String sql = "\n" +
                    "SELECT t.*,\n" +
                    "       trim(concat(if(morning_late < 0, CONCAT('Đi muộn ', abs(morning_late), ' phút. '), ''),\n" +
                    "                   if(t.early_leave > 0, CONCAT('Về sớm ', abs(early_leave), ' phút.'), ''))) decription\n" +
                    "FROM (\n" +
                    "         SELECT d.customer_code,\n" +
                    "                p.full_name,\n" +
                    "                CONCAT('http://192.168.1.19:8181/api/v1/file/', d.image_path)                    AS           url,\n" +
                    "                DATE_FORMAT(d.first_time_check_in, '%H:%i:%s')                              AS           check_in,\n" +
                    "                DATE_FORMAT(d.last_time_check_in, '%H:%i:%s')                               AS           check_out,\n" +
                    "                g.name as group_name,\n" +
                    "                DATE_FORMAT(STR_TO_DATE(d.captured_time, '%Y-%m-%d %H:%i:%s'), '%Y-%m-%d') AS ngay_cham_cong,\n" +
                    "             TIMESTAMPDIFF(MINUTE,\n" +
                    "                              STR_TO_DATE(DATE_FORMAT(d.first_time_check_in, '%H:%i:%s'), '%H:%i:%s'),\n" +
                    "                              STR_TO_DATE('08:00:00', '%H:%i:%s')\n" +
                    "                ) AS morning_late,\n" +
                    "                TIMESTAMPDIFF(MINUTE,\n" +
                    "                              STR_TO_DATE('17:00:00', '%H:%i:%s'),\n" +
                    "                              STR_TO_DATE(DATE_FORMAT(d.last_time_check_in, '%H:%i:%s'), '%H:%i:%s')\n" +
                    "                ) AS early_leave,\n" +
                    "             ROUND(TIMESTAMPDIFF(MINUTE, d.first_time_check_in, d.last_time_check_in) / 60, 2) AS           tong_gio,\n" +
                    "                ROW_NUMBER() OVER (\n" +
                    "                    PARTITION BY d.customer_code, DATE(d.created_time)\n" +
                    "                    ORDER BY d.created_time DESC\n" +
                    "                    ) AS rn\n" +
                    "         FROM detection d\n" +
                    "                  INNER JOIN customer p ON p.customer_code = d.customer_code\n" +
                    "                  LEFT JOIN customer_group g ON p.group_id = g.id\n" +
                    "         GROUP BY d.customer_code, p.full_name, g.name, d.image_path, d.created_time,\n" +
                    "                  d.last_time_check_in, d.first_time_check_in, d.captured_time\n" +
                    "     ) AS t\n" +
                    "WHERE rn = 1;\n";

            return getSqlInsertTimekeeping(sql, new MapSqlParameterSource());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<People> getPeopeIdList(String peopleId) {
        try {
            String sql = getString(peopleId);
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            if (!Utils.isEmpty(peopleId)) {
                parameters.addValue("people_id", peopleId);
            }

            return jdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
               People people = new People();
               people.setPeopleId(rs.getString("people_id"));
               people.setImagePath(rs.getString("url"));
               people.setFullName(rs.getString("full_name"));
               people.setGroupName(rs.getString("group_name"));
               return people;
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @NotNull
    private static String getString(String peopleId) {
        String sql = " SELECT  "
                + "    people_id, "
                + "    CONCAT('" + Constants.URL_IMAGE + "',image_path) AS url, "
                + "    full_name, "
                + "    g.group_name "
                + "FROM "
                + "    people p "
                + "        LEFT JOIN "
                + "    `groups` g ON p.group_id = g.group_id "
                + "    where p.status = 1 ";
        if (!Utils.isEmpty(peopleId)) {
            sql += "AND p.people_id like concat('%', :people_id, '%') ";
        }
        return sql;
    }

    private List<Timekeeping> getSqlInsertTimekeeping( String sql, MapSqlParameterSource parameters ) {
        return jdbcTemplate.query(sql , parameters, (rs, rowNum) -> {
            Timekeeping item = new Timekeeping();
            item.setFullName(rs.getString("full_name"));
            item.setImagePath(rs.getString("url"));
            item.setCheckIn(rs.getString("check_in"));
            item.setCheckOut(rs.getString("check_out"));
            item.setPeopleId(rs.getString("customer_code"));
            item.setDateTimeKeeping(rs.getString("ngay_cham_cong"));
            item.setTotalWork(rs.getString("tong_gio"));
            item.setGroupName(rs.getString("group_name"));
            item.setMorningLateV2(rs.getString("morning_late"));
            item.setEarlyLeave(rs.getString("early_leave"));
            item.setDescription(rs.getString("decription"));
            return item;
        });
    }

}
