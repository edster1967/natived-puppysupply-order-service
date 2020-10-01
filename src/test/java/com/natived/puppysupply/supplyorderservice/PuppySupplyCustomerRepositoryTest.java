package com.natived.puppysupply.supplyorderservice;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import com.natived.puppysupply.supplyorderservice.repositories.PuppySupplyCustomerRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@SpringBootTest
@ActiveProfiles("dev")
public class PuppySupplyCustomerRepositoryTest {

    private static final Logger testLog = LoggerFactory.getLogger(PuppySupplyCustomerRepositoryTest.class);

    @Autowired
    PuppySupplyCustomerRepository puppySupplyCustomerRepository;

    @Test
    public void testGetCustomersByLastName(){
        testLog.info("starting the test Get Customers By Last Name");
        List<PuppySupplyCustomer> resultList = puppySupplyCustomerRepository.findCustomersByLastName("smith");
        assert resultList !=null;

        testLog.info("ending the test Get Customers By Last Name");

    }

    @Test
    public void testGetCustomerByCustomerId(){
        testLog.info("starting the test Get Customer bby customer id");
        Integer testId = 1;
        PuppySupplyCustomer result = puppySupplyCustomerRepository.findCustomerByCustomerId(testId);

        testLog.info("ending the test Get customer by customer id");
    }

}

