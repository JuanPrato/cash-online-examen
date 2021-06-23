package com.cashonline.examen.controller;

import com.cashonline.examen.common.ResponseError;
import com.cashonline.examen.common.loan.LoanPage;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping()
    public ResponseEntity<?> getLoans(@RequestParam("page") Integer page, @RequestParam("size") Integer size, @RequestParam(value = "user_id", required = false) Long userId) {

        LoanPage response;
        try {
            response = this.loanService.getLoansPage(userId, page, size);
        } catch (BadRequestException ex) {
            return ResponseEntity.badRequest().body(new ResponseError(ex));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new ResponseError(ex));
        }

        return ResponseEntity.ok(response);
    }

}
