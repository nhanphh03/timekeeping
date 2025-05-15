package proton.face.repository.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import proton.face.entity.Role;
import proton.face.entity.User;
import proton.face.repository.LoginRepository;

@Repository
public class LoginRepositoryImpl implements LoginRepository {
     private final NamedParameterJdbcTemplate jdbcTemplate;

    public LoginRepositoryImpl(@Qualifier("jdbcTemplateConfig")NamedParameterJdbcTemplate jdbcTemplate) {
         this.jdbcTemplate = jdbcTemplate;
     }

    @Override
    public User validate(String username, String password) {
        String sql = "select u.id, u.username , u.password, r.id  "
                + " from users u inner join user_role ur on u.id = ur.user_id "
                + "			 	inner join roles r on ur.role_id  = r.id "
                + " where username = :username and password = :password";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        params.addValue("password", password);

        try {
            return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                User user = new User();
                Role role = findById(rs.getInt("r.id"));
                user.setId(rs.getInt("u.id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(role);
                return user;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Role findById(int id) {
        String sql = "select * from roles where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
                return role;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
