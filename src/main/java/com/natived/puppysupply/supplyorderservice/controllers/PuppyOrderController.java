package com.natived.puppysupply.supplyorderservice.controllers;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;
import com.natived.puppysupply.supplyorderservice.model.PuppyCustomerModel;
import com.natived.puppysupply.supplyorderservice.model.PuppyOrderModel;
import com.natived.puppysupply.supplyorderservice.services.PuppyOrderService;
import com.natived.puppysupply.supplyorderservice.services.impl.PuppyOrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("puppysupply")
public class PuppyOrderController {

    private final PuppyOrderService puppyOrderService;

    private final Logger log = LoggerFactory.getLogger(PuppyOrderController.class);

    public PuppyOrderController(PuppyOrderService puppyOrderService) {
        this.puppyOrderService = puppyOrderService;
    }

    @GetMapping("/puppyorder/{ordernumber}")
    public PuppyOrderModel getPuppyOrderByOrderNumber(@PathVariable Integer ordernumber){
        log.info("starting controller call to get order by order number-" + ordernumber);
        PuppyOrderModel result = new PuppyOrderModel();
        PuppySupplyOrder order =  puppyOrderService.findByOrderNumber(ordernumber);
        if(order !=null){
            BeanUtils.copyProperties(order,result);
            PuppySupplyCustomer customer = puppyOrderService.findCustomerByCustomerId(order.getCustomerId());
            if(customer!=null){
                PuppyCustomerModel custModel = new PuppyCustomerModel();
                BeanUtils.copyProperties(customer,custModel);
                result.setCustomer(custModel);
            }
        }

        return result;
    }
}
