package com.cashonline.examen.repository;

import com.cashonline.examen.model.Loan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class LoanRepositoryTest {

    @Resource
    private LoanRepository loanRepository;

    private List<Loan> loanToInsert;

    @BeforeEach
    public void setUp() {

        loanToInsert = new ArrayList<>();

        final Loan loan1 = new Loan();
        loan1.setId(1L);
        loan1.setTotal(1.0F);
        loan1.setUserId(1L);
        final Loan loan2 = new Loan();
        loan2.setId(2L);
        loan2.setTotal(2.0F);
        loan2.setUserId(1L);
        final Loan loan3 = new Loan();
        loan3.setId(3L);
        loan3.setTotal(3.2F);
        loan3.setUserId(2L);

        loanToInsert.add(loan1);
        loanToInsert.add(loan2);
        loanToInsert.add(loan3);

        loanRepository.saveAll(loanToInsert);
    }

    @Test
    public void testFindAllByUserIdShouldReturnAllUserWithId1() throws Exception {

        long user_id = 1;

        Pageable firstPage = PageRequest.of(0, 1);

        Page<Loan> response = loanRepository.findAllByUserId(user_id, firstPage);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2 ,response.getTotalElements());
        Assertions.assertEquals(2, response.getTotalPages());
        Assertions.assertEquals(loanToInsert.get(0), response.get().findFirst().orElseThrow(Exception::new));
    }
}
