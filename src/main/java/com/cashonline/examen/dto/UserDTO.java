package com.cashonline.examen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private List<UserLoanDTO> loans;

}
