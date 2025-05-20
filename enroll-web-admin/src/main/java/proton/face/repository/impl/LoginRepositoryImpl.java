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
        String sql = "select u.id, u.username, u.password\n" +
                "from users u\n" +
                "where username = :username\n" +
                "  and password = :password";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        params.addValue("password", password);

        try {
            return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("u.id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
