package com.cashonline.examen.service;

import com.cashonline.examen.common.loan.LoanPage;
import com.cashonline.examen.common.loan.PagingData;
import com.cashonline.examen.dto.LoanDTO;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.mapper.LoanMapper;
import com.cashonline.examen.mapper.PagingMapper;
import com.cashonline.examen.model.Loan;
import com.cashonline.examen.repository.LoanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LoanServiceTest {

    private Loan loan1;
    private LoanDTO loanDTO1;
    private PagingData pagingData;
    private LoanPage loanPage;

    @MockBean
    private LoanRepository loanRepository;

    @MockBean
    private LoanMapper loanMapper;

    @MockBean
    private PagingMapper pagingMapper;

    @Autowired
    private LoanService loanService;

    @BeforeEach
    public void setUp () {

        loan1 = new Loan(1L, 100.00F, 1L);
        loanDTO1 = new LoanDTO(1L, 100.00F, 1L);
        pagingData = new PagingData(1L, 1L, 10L);
        loanPage = new LoanPage(Collections.singletonList(loanDTO1), pagingData);
    }

    @Test
    public void testGetLoansPageUserNullOK () throws BadRequestException {

        Page<Loan> pageMock = Mockito.mock(Page.class);

        Mockito.when(loanRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(pageMock);

        Mockito.when(loanMapper.loanListToLoanDTOList(Mockito.anyList())).thenReturn(Collections.singletonList(loanDTO1));

        Mockito.when(pagingMapper.pageToPaggingData(Mockito.any())).thenReturn(pagingData);

        LoanPage response = loanService.getLoansPage(null, 1, 1);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(loanPage, response);
    }

    @Test
    public void testGetLoansPageUserPageNullShouldThrows () {

        Assertions.assertThrows(BadRequestException.class, () ->
                loanService.getLoansPage(null, null, 1));

    }

    @Test
    public void testGetLoansPageSizeNullShouldThrow () {

        Assertions.assertThrows(BadRequestException.class, () ->
                loanService.getLoansPage(null, 1, null));

    }

    @Test
    public void testGetLoansPageInvalidPageNumberShouldThrow () {

        Assertions.assertThrows(BadRequestException.class, () ->
                loanService.getLoansPage(null, 0, 1));

    }

    @Test
    public void testGetLoansPageInvalidSizeShouldThrow () {


        Assertions.assertThrows(BadRequestException.class, () ->
                loanService.getLoansPage(null, 1, -1));

    }
}
