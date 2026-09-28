package com.natived.puppysupply.supplyorderservice.repositories;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
class PuppySupplyOrderRepositoryTest {

    @Autowired
    PuppySupplyOrderRepository puppySupplyOrderRepository;

    @Test
    void findAllReturnsSeededOrders() {
        assertThat(puppySupplyOrderRepository.findAll()).hasSize(4);
    }

    @Test
    void findByOrderNumberReturnsOrder() {
        PuppySupplyOrder result = puppySupplyOrderRepository.findByOrderNumber(1122);

        assertThat(result).isNotNull();
        assertThat(result.getPuppyOrderId()).isEqualTo(2);
        assertThat(result.getCustomerId()).isEqualTo(11);
        assertThat(result.getSubTotal()).isEqualByComparingTo(new BigDecimal("123.00"));
        assertThat(result.getOrderDate()).isNotNull();
    }

    @Test
    void findByOrderNumberReturnsNullWhenMissing() {
        assertThat(puppySupplyOrderRepository.findByOrderNumber(-1)).isNull();
    }

    @Test
    void findByCustomerIdInReturnsMatchingOrders() {
        List<PuppySupplyOrder> result = puppySupplyOrderRepository.findByCustomerIdIn(List.of(11, 12));

        assertThat(result).extracting(PuppySupplyOrder::getOrderNumber).containsExactlyInAnyOrder(1122, 11221);
    }
}
