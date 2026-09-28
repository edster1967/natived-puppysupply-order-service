package com.natived.puppysupply.supplyorderservice.controllers;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;
import com.natived.puppysupply.supplyorderservice.model.PuppyCustomerModel;
import com.natived.puppysupply.supplyorderservice.model.PuppyOrderModel;
import com.natived.puppysupply.supplyorderservice.services.PuppyOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/puppysupply")
public class PuppyOrderController {

    private final PuppyOrderService puppyOrderService;

    private final Logger log = LoggerFactory.getLogger(PuppyOrderController.class);

    public PuppyOrderController(PuppyOrderService puppyOrderService) {
        this.puppyOrderService = puppyOrderService;
    }

    @GetMapping("/puppyorder/{ordernumber}")
    public ResponseEntity<PuppyOrderModel> getPuppyOrderByOrderNumber(@PathVariable("ordernumber") Integer ordernumber){
        log.info("starting controller call to get order by order number-{}", ordernumber);
        return toResponse(puppyOrderService.findByOrderNumber(ordernumber));
    }

    @GetMapping("/puppyorder/id/{puppyorderid}")
    public ResponseEntity<PuppyOrderModel> getPuppyOrderById(@PathVariable("puppyorderid") Integer puppyOrderId){
        log.info("starting controller call to get order by puppy order id-{}", puppyOrderId);
        return toResponse(puppyOrderService.findByPuppySupplyOrderId(puppyOrderId));
    }

    @GetMapping("/puppyorders")
    public List<PuppyOrderModel> getPuppyOrdersByLastName(@RequestParam("lastName") String lastName){
        log.info("starting controller call to get orders by billing last name-{}", lastName);
        return puppyOrderService.findBybillingLastName(lastName).stream()
                .map(this::toOrderModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerid}")
    public ResponseEntity<PuppyCustomerModel> getCustomerById(@PathVariable("customerid") Integer customerId){
        log.info("starting controller call to get customer by customer id-{}", customerId);
        PuppySupplyCustomer customer = puppyOrderService.findCustomerByCustomerId(customerId);
        return customer == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(toCustomerModel(customer));
    }

    private ResponseEntity<PuppyOrderModel> toResponse(PuppySupplyOrder order) {
        return order == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(toOrderModel(order));
    }

    private PuppyOrderModel toOrderModel(PuppySupplyOrder order) {
        PuppyOrderModel result = new PuppyOrderModel();
        BeanUtils.copyProperties(order, result);
        PuppySupplyCustomer customer = puppyOrderService.findCustomerByCustomerId(order.getCustomerId());
        if(customer != null){
            result.setCustomer(toCustomerModel(customer));
        }
        return result;
    }

    private PuppyCustomerModel toCustomerModel(PuppySupplyCustomer customer) {
        PuppyCustomerModel custModel = new PuppyCustomerModel();
        BeanUtils.copyProperties(customer, custModel);
        return custModel;
    }
}
