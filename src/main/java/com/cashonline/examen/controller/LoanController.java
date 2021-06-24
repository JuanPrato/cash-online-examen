package com.cashonline.examen.controller;

import com.cashonline.examen.common.ResponseError;
import com.cashonline.examen.common.loan.LoanPage;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final Logger logger = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping()
    public ResponseEntity<?> getLoans(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "size", required = false) Integer size,
                                      @RequestParam(value = "user_id", required = false) Long userId) {

        LoanPage response;
        try {
            response = this.loanService.getLoansPage(userId, page, size);
        } catch (BadRequestException ex) {
            logger.error(String.format("BAD REQUEST EXCEPTION WITH id: %s, page: %s and size: %s => ", userId, page, size), ex);
            return ResponseEntity.badRequest().body(new ResponseError(ex));
        } catch (Exception ex) {
            logger.error(String.format("UNKNOWN EXCEPTION WITH id: %s, page: %s and size: %s => ", userId, page, size), ex);
            return ResponseEntity.internalServerError().body(new ResponseError(ex));
        }

        return ResponseEntity.ok(response);
    }

}
