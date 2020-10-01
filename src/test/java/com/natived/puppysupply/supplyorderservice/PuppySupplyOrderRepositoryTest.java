package com.natived.puppysupply.supplyorderservice;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;
import com.natived.puppysupply.supplyorderservice.repositories.PuppySupplyOrderRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("dev")
public class PuppySupplyOrderRepositoryTest {

    @Autowired
    PuppySupplyOrderRepository puppySupplyOrderRepository;

    private static final Logger testLog = LoggerFactory.getLogger(PuppySupplyOrderRepositoryTest.class);

    @Test
    public void testGetAllOrders(){

        testLog.info("starting the test Get All Orders ");
        List<PuppySupplyOrder> resultList = puppySupplyOrderRepository.findAll();
        assert resultList !=null;

        testLog.info("ending the test Get All Orders ");

    }
}
