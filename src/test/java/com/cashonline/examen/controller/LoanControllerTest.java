package com.cashonline.examen.controller;

import com.cashonline.examen.common.ResponseError;
import com.cashonline.examen.common.loan.LoanPage;
import com.cashonline.examen.common.loan.PagingData;
import com.cashonline.examen.dto.LoanDTO;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.model.Loan;
import com.cashonline.examen.repository.LoanRepository;
import com.cashonline.examen.repository.UserRepository;
import com.cashonline.examen.service.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LoanController.class)
public class LoanControllerTest {

    private LoanDTO loanDTO;
    private Loan loan;
    private LoanPage loanPage;
    private ResponseError error;

    private final String baseUri = "/loans";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LoanRepository loanRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoanService loanService;

    @BeforeEach
    public void setUp() {

        loanDTO = new LoanDTO(1L, 1000.00F, 1L);
        loan = new Loan(1L, 1000.00F, 1L);
        loanPage = new LoanPage(Collections.singletonList(loanDTO), new PagingData(1, 1, 1));
    }

    @Test
    public void testGetLoansOk () throws Exception {

        Mockito.when(loanService.getLoansPage(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(loanPage);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(this.baseUri + "?page=1&size=1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isOk())
                                        .andReturn();

        Assertions.assertEquals(objectMapper.writeValueAsString(loanPage), result.getResponse().getContentAsString());

    }

    @Test
    public void testGetLoansBadRequest () throws Exception {

        Mockito.when(loanService.getLoansPage(Mockito.any(), Mockito.any(), Mockito.any()))
                    .thenThrow(new BadRequestException("Invalid page or size"));

        error = new ResponseError(new BadRequestException("Invalid page or size"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(this.baseUri + "?page=1&size1")
                                        .accept(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isBadRequest())
                                        .andReturn();


        Assertions.assertEquals(objectMapper.writeValueAsString(error), result.getResponse().getContentAsString());
    }

    @Test
    public void testGetLoansInternalServerError () throws Exception {

        Mockito.when(loanService.getLoansPage(Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(RuntimeException.class);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(this.baseUri)
                                        .accept(MediaType.APPLICATION_JSON))
                                        .andExpect(status().isInternalServerError())
                                        .andReturn();

    }

}
