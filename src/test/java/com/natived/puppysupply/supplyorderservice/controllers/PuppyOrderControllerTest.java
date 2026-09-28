package com.natived.puppysupply.supplyorderservice.controllers;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;
import com.natived.puppysupply.supplyorderservice.services.PuppyOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the web layer only - the service is mocked, no database is used.
 */
@WebMvcTest(PuppyOrderController.class)
class PuppyOrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PuppyOrderService puppyOrderService;

    @Test
    void getOrderByOrderNumberReturnsOrderWithCustomer() throws Exception {
        when(puppyOrderService.findByOrderNumber(1122)).thenReturn(order(2, 1122, 11));
        when(puppyOrderService.findCustomerByCustomerId(11)).thenReturn(customer(11, "John", "smith"));

        mockMvc.perform(get("/puppysupply/puppyorder/1122"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puppyOrderId").value(2))
                .andExpect(jsonPath("$.orderNumber").value(1122))
                .andExpect(jsonPath("$.orderDate").value("2026-01-15"))
                .andExpect(jsonPath("$.customerId").value(11))
                .andExpect(jsonPath("$.subTotal").value(123.00))
                .andExpect(jsonPath("$.shippingCost").value(5.00))
                .andExpect(jsonPath("$.tax").value(1.00))
                .andExpect(jsonPath("$.total").value(129.00))
                .andExpect(jsonPath("$.customer.first_name").value("John"))
                .andExpect(jsonPath("$.customer.last_name").value("smith"))
                .andExpect(jsonPath("$.customer.city").value("brunswick"));
    }

    @Test
    void getOrderByOrderNumberOmitsCustomerWhenNotFound() throws Exception {
        when(puppyOrderService.findByOrderNumber(1122)).thenReturn(order(2, 1122, 11));
        when(puppyOrderService.findCustomerByCustomerId(11)).thenReturn(null);

        mockMvc.perform(get("/puppysupply/puppyorder/1122"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(1122))
                .andExpect(jsonPath("$.customer").doesNotExist());
    }

    @Test
    void getOrderByOrderNumberReturns404WhenMissing() throws Exception {
        when(puppyOrderService.findByOrderNumber(999)).thenReturn(null);

        mockMvc.perform(get("/puppysupply/puppyorder/999"))
                .andExpect(status().isNotFound());
        verify(puppyOrderService, never()).findCustomerByCustomerId(anyInt());
    }

    @Test
    void getOrderByOrderNumberReturns400ForNonNumericValue() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorder/abc"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(puppyOrderService);
    }

    @Test
    void getOrderByIdReturnsOrder() throws Exception {
        when(puppyOrderService.findByPuppySupplyOrderId(21)).thenReturn(order(21, 11221, 12));
        when(puppyOrderService.findCustomerByCustomerId(12)).thenReturn(customer(12, "Jane", "smith"));

        mockMvc.perform(get("/puppysupply/puppyorder/id/21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puppyOrderId").value(21))
                .andExpect(jsonPath("$.orderNumber").value(11221))
                .andExpect(jsonPath("$.customer.first_name").value("Jane"));
    }

    @Test
    void getOrderByIdReturns404WhenMissing() throws Exception {
        when(puppyOrderService.findByPuppySupplyOrderId(999)).thenReturn(null);

        mockMvc.perform(get("/puppysupply/puppyorder/id/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getOrderByIdReturns400ForNonNumericValue() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorder/id/abc"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(puppyOrderService);
    }

    @Test
    void getOrdersByLastNameReturnsAllOrders() throws Exception {
        when(puppyOrderService.findBybillingLastName("smith"))
                .thenReturn(List.of(order(2, 1122, 11), order(21, 11221, 12)));
        when(puppyOrderService.findCustomerByCustomerId(11)).thenReturn(customer(11, "John", "smith"));
        when(puppyOrderService.findCustomerByCustomerId(12)).thenReturn(customer(12, "Jane", "smith"));

        mockMvc.perform(get("/puppysupply/puppyorders").param("lastName", "smith"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].orderNumber").value(1122))
                .andExpect(jsonPath("$[0].customer.first_name").value("John"))
                .andExpect(jsonPath("$[1].orderNumber").value(11221))
                .andExpect(jsonPath("$[1].customer.first_name").value("Jane"));
    }

    @Test
    void getOrdersByLastNameReturnsEmptyList() throws Exception {
        when(puppyOrderService.findBybillingLastName("nobody")).thenReturn(List.of());

        mockMvc.perform(get("/puppysupply/puppyorders").param("lastName", "nobody"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getOrdersByLastNameReturns400WhenParamMissing() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorders"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(puppyOrderService);
    }

    @Test
    void getCustomerByIdReturnsCustomer() throws Exception {
        when(puppyOrderService.findCustomerByCustomerId(13)).thenReturn(customer(13, "Jasmine", "Jones"));

        mockMvc.perform(get("/puppysupply/customer/13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("Jasmine"))
                .andExpect(jsonPath("$.last_name").value("Jones"))
                .andExpect(jsonPath("$.address_1").value("123 test drive"))
                .andExpect(jsonPath("$.state").value("OH"))
                .andExpect(jsonPath("$.zip").value("44212"));
    }

    @Test
    void getCustomerByIdReturns404WhenMissing() throws Exception {
        when(puppyOrderService.findCustomerByCustomerId(999)).thenReturn(null);

        mockMvc.perform(get("/puppysupply/customer/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerByIdReturns400ForNonNumericValue() throws Exception {
        mockMvc.perform(get("/puppysupply/customer/abc"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(puppyOrderService);
    }

    private static PuppySupplyOrder order(int id, int orderNumber, int customerId) {
        PuppySupplyOrder order = new PuppySupplyOrder();
        order.setPuppyOrderId(id);
        order.setOrderNumber(orderNumber);
        order.setOrderDate(LocalDate.of(2026, 1, 15));
        order.setCustomerId(customerId);
        order.setSubTotal(new BigDecimal("123.00"));
        order.setShippingCost(new BigDecimal("5.00"));
        order.setTax(new BigDecimal("1.00"));
        order.setTotal(new BigDecimal("129.00"));
        return order;
    }

    private static PuppySupplyCustomer customer(int customerId, String firstName, String lastName) {
        PuppySupplyCustomer customer = new PuppySupplyCustomer();
        customer.setCustomerId(customerId);
        customer.setFirst_name(firstName);
        customer.setLast_name(lastName);
        customer.setAddress_1("123 test drive");
        customer.setCity("brunswick");
        customer.setState("OH");
        customer.setZip("44212");
        return customer;
    }
}
