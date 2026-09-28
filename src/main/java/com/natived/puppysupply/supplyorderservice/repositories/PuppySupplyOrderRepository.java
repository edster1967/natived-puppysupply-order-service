package com.natived.puppysupply.supplyorderservice.repositories;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface PuppySupplyOrderRepository extends CrudRepository<PuppySupplyOrder, Integer> {

    List<PuppySupplyOrder> findAll();

    PuppySupplyOrder findByOrderNumber(Integer orderNumber);

    List<PuppySupplyOrder> findByCustomerIdIn(Collection<Integer> customerIds);

}
