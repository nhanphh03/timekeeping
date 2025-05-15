package proton.face.repository.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import proton.face.entity.CustomerType;
import proton.face.repository.CustomerTypeRepository;

import java.util.List;

@Repository
public class CustomerTypeRepositoryImpl implements CustomerTypeRepository {

	NamedParameterJdbcTemplate jdbcTemplate;

	public CustomerTypeRepositoryImpl (
			@Qualifier("jdbcTemplateConfig") NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<CustomerType> getCustomerTypeList() {
		List<CustomerType> customerTypeList;
			String sql = " SELECT id, name FROM customertype WHERE status = 1 ";
			customerTypeList = jdbcTemplate.query(sql, new MapSqlParameterSource(), (rs, i) ->{
						CustomerType type = new CustomerType();
						type.setCustomerTypeId(rs.getInt("id"));
						type.setCustomerTypeName(rs.getString("name"));
						return type;
					});
		return customerTypeList;
	}
}