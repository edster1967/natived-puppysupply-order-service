package com.natived.puppysupply.supplyorderservice.model;

import java.math.BigDecimal;
import java.util.Date;

public class PuppyOrderModel {

    private Integer puppyOrderId;
    private Integer orderNumber;
    private Date orderDate;
    private Integer customerId;
    private BigDecimal subTotal;
    private BigDecimal shippingCost;
    private BigDecimal tax;
    private BigDecimal total;

    public Integer getPuppyOrderId() {
        return puppyOrderId;
    }

    public void setPuppyOrderId(Integer puppyOrderId) {
        this.puppyOrderId = puppyOrderId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public PuppyCustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(PuppyCustomerModel customer) {
        this.customer = customer;
    }

    private PuppyCustomerModel customer;
}
