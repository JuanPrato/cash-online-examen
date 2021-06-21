package com.cashonline.examen.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanDTO {

    private Long id;

    private Float total;

    private Long userId;

}
