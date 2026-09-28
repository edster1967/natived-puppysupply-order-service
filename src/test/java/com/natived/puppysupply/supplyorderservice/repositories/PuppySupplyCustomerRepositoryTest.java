package com.natived.puppysupply.supplyorderservice.repositories;

import com.natived.puppysupply.supplyorderservice.domain.PuppySupplyCustomer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
class PuppySupplyCustomerRepositoryTest {

    @Autowired
    PuppySupplyCustomerRepository puppySupplyCustomerRepository;

    @Test
    void findCustomersByLastNameReturnsAllMatches() {
        List<PuppySupplyCustomer> resultList = puppySupplyCustomerRepository.findCustomersByLastName("smith");

        assertThat(resultList).extracting(PuppySupplyCustomer::getCustomerId).containsExactlyInAnyOrder(1, 11, 12);
    }

    @Test
    void findCustomersByLastNameIsCaseInsensitive() {
        assertThat(puppySupplyCustomerRepository.findCustomersByLastName("JONES")).hasSize(1);
    }

    @Test
    void findCustomersByLastNameReturnsEmptyListWhenNoMatch() {
        assertThat(puppySupplyCustomerRepository.findCustomersByLastName("nobody")).isEmpty();
    }

    @Test
    void findCustomerByCustomerIdMapsColumns() {
        PuppySupplyCustomer result = puppySupplyCustomerRepository.findCustomerByCustomerId(11);

        assertThat(result).isNotNull();
        assertThat(result.getFirst_name()).isEqualTo("John");
        assertThat(result.getLast_name()).isEqualTo("smith");
        assertThat(result.getAddress_1()).isEqualTo("1231 test drive");
        assertThat(result.getCity()).isEqualTo("brunswick");
        assertThat(result.getState()).isEqualTo("OH");
        assertThat(result.getZip()).isEqualTo("44212");
    }

    @Test
    void findCustomerByCustomerIdReturnsNullWhenMissing() {
        assertThat(puppySupplyCustomerRepository.findCustomerByCustomerId(999)).isNull();
    }

}
