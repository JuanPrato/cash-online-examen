package com.cashonline.examen.mapper;

import com.cashonline.examen.dto.LoanDTO;
import com.cashonline.examen.dto.UserLoanDTO;
import com.cashonline.examen.model.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    LoanDTO loanToLoanDTO(Loan loan);

    UserLoanDTO loanToUserLoanDTO(Loan loan);

}
