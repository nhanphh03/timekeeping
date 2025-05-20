package proton.face.repository.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import proton.face.entity.Groups;
import proton.face.repository.GroupRepository;

import java.util.List;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GroupRepositoryImpl(@Qualifier("jdbcTemplateConfig")NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Groups> getGroupList() {
        String sql = " SELECT id, code, name FROM customer_group WHERE status = 1 ";
        return jdbcTemplate.query(sql, new MapSqlParameterSource(), (rs, rowNum) -> {
            Groups item = new Groups();
            item.setGroupId(rs.getInt("id"));
            item.setGroupCode(rs.getString("code"));
            item.setGroupName(rs.getString("name"));
            return item;
        });
    }
}
