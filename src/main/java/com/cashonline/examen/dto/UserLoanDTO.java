package com.cashonline.examen.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoanDTO {

    private Long id;

    private Integer total;

    private Long userId;

}
