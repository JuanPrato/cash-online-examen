package com.cashonline.examen.repository;

import com.cashonline.examen.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends PagingAndSortingRepository<Loan, Long> {

    Page<Loan> findAllByUserId(Long user_id, Pageable pageable);

}
