package com.cashonline.examen.mapper;

import com.cashonline.examen.dto.LoanDTO;
import com.cashonline.examen.dto.UserLoanDTO;
import com.cashonline.examen.model.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    LoanDTO loanToLoanDTO(Loan loan);

    UserLoanDTO loanToUserLoanDTO(Loan loan);

}
