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
        String sql = " SELECT group_id, group_code, group_name FROM `groups` WHERE status = 1 ";
        return jdbcTemplate.query(sql, new MapSqlParameterSource(), (rs, rowNum) -> {
            Groups item = new Groups();
            item.setGroupId(rs.getInt("group_id"));
            item.setGroupCode(rs.getString("group_code"));
            item.setGroupName(rs.getString("group_name"));
            return item;
        });
    }
}
