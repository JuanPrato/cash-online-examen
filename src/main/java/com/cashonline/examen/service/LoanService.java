package com.cashonline.examen.service;

import com.cashonline.examen.common.loan.LoanPage;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.mapper.LoanMapper;
import com.cashonline.examen.mapper.PagingMapper;
import com.cashonline.examen.model.Loan;
import com.cashonline.examen.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final PagingMapper pagingMapper;

    @Autowired
    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper, PagingMapper pagingMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.pagingMapper = pagingMapper;
    }

    public LoanPage getLoansPage(Long userId, Integer page, Integer size) throws BadRequestException {

        if (page == null || size == null) {
            throw new BadRequestException("Invalid input");
        }
        Pageable pageable;
        try {
             pageable = PageRequest.of(page - 1, size);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid page or size");
        }
        LoanPage loansPage = new LoanPage();
        Page<Loan> loansPageDB;

        if (userId == null) {
            loansPageDB = loanRepository.findAll(pageable);
        } else {
            loansPageDB = loanRepository.findAllByUserId(userId, pageable);
        }

        loansPage.setLoans(loanMapper.loanListToLoanDTOList(loansPageDB.getContent()));
        loansPage.setPagingData(pagingMapper.pageToPaggingData(loansPageDB));

        return loansPage;
    }

}
