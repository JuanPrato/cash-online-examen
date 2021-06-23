package com.cashonline.examen.repository;

import com.cashonline.examen.model.Loan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @BeforeEach
    private void setUp() {
        loanRepository.save(new Loan(1L, 150.00F, 1L));
        loanRepository.save(new Loan(2L, 75.32F, 1L));
        loanRepository.save(new Loan(3L, 75.32F, 2L));
    }

    @Test
    public void testFindAllByUserIdShouldReturnAllUserWithId1() throws Exception {

        long user_id = 1;

        Pageable firstPage = PageRequest.of(0, 1);

        Page<Loan> response = loanRepository.findAllByUserId(user_id, firstPage);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2 ,response.getTotalElements());
        Assertions.assertEquals(2, response.getTotalPages());
        Assertions.assertEquals( new Loan(1L, 150.00F, 1L), response.get().findFirst().orElseThrow(Exception::new));
    }
}
