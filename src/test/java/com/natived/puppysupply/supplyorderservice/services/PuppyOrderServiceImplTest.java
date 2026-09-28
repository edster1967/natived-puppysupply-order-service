package com.natived.puppysupply.supplyorderservice.services;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;
import com.natived.puppysupply.supplyorderservice.repositories.PuppySupplyCustomerRepository;
import com.natived.puppysupply.supplyorderservice.repositories.PuppySupplyOrderRepository;
import com.natived.puppysupply.supplyorderservice.services.impl.PuppyOrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PuppyOrderServiceImplTest {

    @Mock
    PuppySupplyCustomerRepository customerRepository;

    @Mock
    PuppySupplyOrderRepository orderRepository;

    PuppyOrderService service;

    @BeforeEach
    void setUp() {
        service = new PuppyOrderServiceImpl(customerRepository, orderRepository);
    }

    @Test
    void findByPuppySupplyOrderIdReturnsOrder() {
        PuppySupplyOrder order = order(2, 1122, 11);
        when(orderRepository.findById(2)).thenReturn(Optional.of(order));

        assertThat(service.findByPuppySupplyOrderId(2)).isSameAs(order);
    }

    @Test
    void findByPuppySupplyOrderIdReturnsNullWhenMissing() {
        when(orderRepository.findById(99)).thenReturn(Optional.empty());

        assertThat(service.findByPuppySupplyOrderId(99)).isNull();
    }

    @Test
    void findByOrderNumberDelegatesToRepository() {
        PuppySupplyOrder order = order(2, 1122, 11);
        when(orderRepository.findByOrderNumber(1122)).thenReturn(order);

        assertThat(service.findByOrderNumber(1122)).isSameAs(order);
    }

    @Test
    void findByBillingLastNameReturnsOrdersForMatchingCustomers() {
        when(customerRepository.findCustomersByLastName("smith")).thenReturn(List.of(customer(11), customer(12)));
        List<PuppySupplyOrder> orders = List.of(order(2, 1122, 11), order(21, 11221, 12));
        when(orderRepository.findByCustomerIdIn(List.of(11, 12))).thenReturn(orders);

        assertThat(service.findBybillingLastName("smith")).isEqualTo(orders);
    }

    @Test
    void findByBillingLastNameSkipsOrderLookupWhenNoCustomers() {
        when(customerRepository.findCustomersByLastName("nobody")).thenReturn(List.of());

        assertThat(service.findBybillingLastName("nobody")).isEmpty();
        verify(orderRepository, never()).findByCustomerIdIn(any());
    }

    @Test
    void findCustomerByCustomerIdDelegatesToRepository() {
        PuppySupplyCustomer customer = customer(11);
        when(customerRepository.findCustomerByCustomerId(11)).thenReturn(customer);

        assertThat(service.findCustomerByCustomerId(11)).isSameAs(customer);
    }

    private static PuppySupplyOrder order(int id, int orderNumber, int customerId) {
        PuppySupplyOrder order = new PuppySupplyOrder();
        order.setPuppyOrderId(id);
        order.setOrderNumber(orderNumber);
        order.setCustomerId(customerId);
        return order;
    }

    private static PuppySupplyCustomer customer(int customerId) {
        PuppySupplyCustomer customer = new PuppySupplyCustomer();
        customer.setCustomerId(customerId);
        return customer;
    }
}
