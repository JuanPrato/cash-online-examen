package com.cashonline.examen.common.loan;

import com.cashonline.examen.dto.LoanDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class LoanPage {


    @JsonProperty("items")
    private List<LoanDTO> loans;

    @JsonProperty("paging")
    private PagingData pagingData;

}
