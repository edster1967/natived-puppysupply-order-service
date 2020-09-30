package com.natived.puppysupply.supplyorderservice;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
public class PuppySupplyOrderRepositoryTest {


    private static final Logger testLog = LoggerFactory.getLogger(PuppySupplyOrderRepositoryTest.class);

    @Test
    public void testGetAllOrders(){

        testLog.info("starting the test Get All Orders ");

        testLog.info("ending the test Get All Orders ");

    }
}
