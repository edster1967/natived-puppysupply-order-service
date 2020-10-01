package com.natived.puppysupply.supplyorderservice.repositories;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;

import java.util.List;

public interface PuppySupplyCustomerRepository {

    PuppySupplyCustomer findCustomerByCustomerId(Integer customerId);

    List<PuppySupplyCustomer> findCustomersByLastName(String lastName);

}
