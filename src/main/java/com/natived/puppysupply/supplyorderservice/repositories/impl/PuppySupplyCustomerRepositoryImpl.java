package com.natived.puppysupply.supplyorderservice.repositories.impl;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import com.natived.puppysupply.supplyorderservice.repositories.PuppySupplyCustomerRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class PuppySupplyCustomerRepositoryImpl implements PuppySupplyCustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public PuppySupplyCustomerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PuppySupplyCustomer findCustomerByCustomerId(Integer customerId) {
        String sql = "select * from customer where customer_id = ?";
        List<PuppySupplyCustomer> resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PuppySupplyCustomer.class), customerId);

        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public List<PuppySupplyCustomer> findCustomersByLastName(String lastName) {
        String sql = "select * from customer where lower(last_name) = lower(?)";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PuppySupplyCustomer.class), lastName);

    }

    /*
    // this is one way to call a stored procedure  - based off an oracle sp call.
    @Override
    public PuppySupplyCustomer findCustomerByCustomerId(Integer customerId) {
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_get_customer").returningResultSet("RESULTSET", BeanPropertyRowMapper.newInstance(PuppySupplyCustomer.class));
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("customerId", customerId);

        Map out = simpleJdbcCall.execute(parameters);
        return null;
    }
     */
}
