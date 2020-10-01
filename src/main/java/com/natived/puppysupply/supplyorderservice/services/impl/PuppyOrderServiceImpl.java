package com.natived.puppysupply.supplyorderservice.services.impl;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;
import com.natived.puppysupply.supplyorderservice.repositories.PuppySupplyCustomerRepository;
import com.natived.puppysupply.supplyorderservice.repositories.PuppySupplyOrderRepository;
import com.natived.puppysupply.supplyorderservice.services.PuppyOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuppyOrderServiceImpl  implements PuppyOrderService {
    private final PuppySupplyCustomerRepository puppySupplyCustomerRepository;
    private final PuppySupplyOrderRepository puppySupplyOrderRepository;

    private final Logger log = LoggerFactory.getLogger(PuppyOrderServiceImpl.class);

    public PuppyOrderServiceImpl(PuppySupplyCustomerRepository puppySupplyCustomerRepository, PuppySupplyOrderRepository puppySupplyOrderRepository) {
        this.puppySupplyCustomerRepository = puppySupplyCustomerRepository;
        this.puppySupplyOrderRepository = puppySupplyOrderRepository;
    }

    @Override
    public PuppySupplyOrder findByPuppySupplyOrderId(Integer puppySupplyOrderId) {
        log.info("Begin find By Puppy Supply Order Id with value - "+ puppySupplyOrderId);

        log.info("End find By Puppy Supply Order Id");
        return null;
    }

    @Override
    public PuppySupplyOrder findByOrderNumber(Integer orderNumber) {
        log.info("Begin find By Order Number with value - "+ orderNumber);
        PuppySupplyOrder result = puppySupplyOrderRepository.findByOrderNumber(orderNumber);
        log.info("End find By Order Number");
        return result;

    }

    @Override
    public List<PuppySupplyOrder> findBybillingLastName(String billingLastName) {
        return null;
    }

    @Override
    public PuppySupplyCustomer findCustomerByCustomerId(Integer customerId) {
        log.info("Begin find Customer By Customer Id with value - "+ customerId);
        PuppySupplyCustomer result = puppySupplyCustomerRepository.findCustomerByCustomerId(customerId);
        log.info("End find Customer By Customer Id with value - "+ customerId);
        return result;
    }
}
