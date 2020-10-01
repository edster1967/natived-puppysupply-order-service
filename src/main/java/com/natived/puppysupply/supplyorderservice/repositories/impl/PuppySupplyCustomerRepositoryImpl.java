package com.natived.puppysupply.supplyorderservice.repositories.impl;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import com.natived.puppysupply.supplyorderservice.repositories.PuppySupplyCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class PuppySupplyCustomerRepositoryImpl implements PuppySupplyCustomerRepository {

   @Autowired
   private JdbcTemplate jdbcTemplate;


    @Override
    public PuppySupplyCustomer findCustomerByCustomerId(Integer customerId) {
        PuppySupplyCustomer result = new PuppySupplyCustomer();

        String sql = "select * from customer where customer_id = ?";
        List<PuppySupplyCustomer> resultList = jdbcTemplate.query(sql,new Object[]{customerId}, new BeanPropertyRowMapper<>(PuppySupplyCustomer.class));

        if(resultList.size() > 0){
            result = resultList.get(0);
        }

        return result;
    }

    @Override
    public List<PuppySupplyCustomer> findCustomersByLastName(String lastName) {
        String sql = "Select * from customer where last_name = ?";

        return jdbcTemplate.query(sql,new Object[]{lastName}, new BeanPropertyRowMapper<>(PuppySupplyCustomer.class));

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
