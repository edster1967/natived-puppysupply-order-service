package com.natived.puppysupply.supplyorderservice.repositories;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JPA slice test - loads only the JPA repositories against the H2 schema and sample data.
 */
@DataJpaTest
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
    void findByIdReturnsOrder() {
        assertThat(puppySupplyOrderRepository.findById(21))
                .hasValueSatisfying(order -> assertThat(order.getOrderNumber()).isEqualTo(11221));
    }

    @Test
    void findByIdReturnsEmptyWhenMissing() {
        assertThat(puppySupplyOrderRepository.findById(999)).isEmpty();
    }

    @Test
    void findByCustomerIdInReturnsEmptyListWhenNoMatch() {
        assertThat(puppySupplyOrderRepository.findByCustomerIdIn(List.of(1, 999))).isEmpty();
    }

    @Test
    void findByCustomerIdInReturnsMatchingOrders() {
        List<PuppySupplyOrder> result = puppySupplyOrderRepository.findByCustomerIdIn(List.of(11, 12));

        assertThat(result).extracting(PuppySupplyOrder::getOrderNumber).containsExactlyInAnyOrder(1122, 11221);
    }
}
