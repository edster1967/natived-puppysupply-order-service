package com.natived.puppysupply.supplyorderservice.services.impl;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;
import com.natived.puppysupply.supplyorderservice.repositories.PuppySupplyCustomerRepository;
import com.natived.puppysupply.supplyorderservice.repositories.PuppySupplyOrderRepository;
import com.natived.puppysupply.supplyorderservice.services.PuppyOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuppyOrderServiceImpl  implements PuppyOrderService {
    private final PuppySupplyCustomerRepository puppySupplyCustomerRepository;
    private final PuppySupplyOrderRepository puppySupplyOrderRepository;

    public PuppyOrderServiceImpl(PuppySupplyCustomerRepository puppySupplyCustomerRepository, PuppySupplyOrderRepository puppySupplyOrderRepository) {
        this.puppySupplyCustomerRepository = puppySupplyCustomerRepository;
        this.puppySupplyOrderRepository = puppySupplyOrderRepository;
    }

    @Override
    public PuppySupplyOrder findByPuppySupplyOrderId(Integer puppySupplyOrderId) {
        return null;
    }

    @Override
    public PuppySupplyOrder findByOrderNumber(String orderNumber) {
        return null;
    }

    @Override
    public List<PuppySupplyOrder> findBybillingLastName(String billingLastName) {
        return null;
    }

    @Override
    public PuppySupplyCustomer findCustomerByCustomerId(Integer customerId) {
        return null;
    }
}
