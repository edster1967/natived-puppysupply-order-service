package com.natived.puppysupply.supplyorderservice.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Exercises every URL end to end (controller -> service -> repository -> H2).
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class PuppyOrderControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getOrderByOrderNumber() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorder/1122"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puppyOrderId").value(2))
                .andExpect(jsonPath("$.orderNumber").value(1122))
                .andExpect(jsonPath("$.customerId").value(11))
                .andExpect(jsonPath("$.subTotal").value(123.00))
                .andExpect(jsonPath("$.total").value(200.00))
                .andExpect(jsonPath("$.customer.first_name").value("John"))
                .andExpect(jsonPath("$.customer.last_name").value("smith"));
    }

    @Test
    void getOrderByOrderNumberReturns404WhenMissing() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorder/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getOrderByOrderNumberReturns400ForNonNumericValue() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorder/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getOrderById() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorder/id/21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(11221))
                .andExpect(jsonPath("$.customer.first_name").value("Jane"));
    }

    @Test
    void getOrderByIdReturns404WhenMissing() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorder/id/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getOrdersByLastName() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorders").param("lastName", "smith"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].orderNumber", containsInAnyOrder(1122, 11221)));
    }

    @Test
    void getOrdersByLastNameReturnsEmptyListWhenNoMatch() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorders").param("lastName", "nobody"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getOrdersByLastNameReturns400WhenParamMissing() throws Exception {
        mockMvc.perform(get("/puppysupply/puppyorders"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCustomerById() throws Exception {
        mockMvc.perform(get("/puppysupply/customer/13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("Jasmine"))
                .andExpect(jsonPath("$.last_name").value("Jones"))
                .andExpect(jsonPath("$.zip").value("44212"));
    }

    @Test
    void getCustomerByIdReturns404WhenMissing() throws Exception {
        mockMvc.perform(get("/puppysupply/customer/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void actuatorHealthIsUp() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void openApiDocsAreServed() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/puppysupply/puppyorder/{ordernumber}']").exists());
    }
}
