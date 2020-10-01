package com.natived.puppysupply.supplyorderservice.services;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;

import java.util.List;

public interface PuppyOrderService {

    PuppySupplyOrder findByPuppySupplyOrderId(Integer puppySupplyOrderId);

    PuppySupplyOrder findByOrderNumber(Integer orderNumber);

    List<PuppySupplyOrder> findBybillingLastName(String billingLastName);

    PuppySupplyCustomer findCustomerByCustomerId(Integer customerId);


}
