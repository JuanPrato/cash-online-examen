package com.cashonline.examen.controller;

import com.cashonline.examen.common.ResponseError;
import com.cashonline.examen.dto.CreateUserDTO;
import com.cashonline.examen.dto.UserDTO;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.exception.NotFoundException;
import com.cashonline.examen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneUser(@PathVariable("id") Long id) {
        UserDTO user;
        try {
            user = userService.getOne(id);
        } catch (BadRequestException ex) {
            logger.error("BAD REQUEST ERROR: GET ONE USER with id " + id, ex);
            return ResponseEntity.badRequest().body(new ResponseError(ex));
        } catch (NotFoundException ex) {
            logger.error("USER NOT FOUND WITH ID " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            logger.error("INTERNAL ERROR: GET ONE USER with id " + id, ex);
            return ResponseEntity.internalServerError().body(new ResponseError(ex));
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> createOneUser(@RequestBody CreateUserDTO userDTO) {

        UserDTO user;
        try {
            user = userService.createOne(userDTO);
        } catch (BadRequestException ex) {
            logger.error("BAD REQUEST ERROR: CREATE ONE USER with data: " + userDTO.toString(), ex);
            return ResponseEntity.badRequest().body(new ResponseError(ex));
        } catch (Exception ex) {
            logger.error("INTERNAL ERROR: CREATE ONE USER with data: " + userDTO.toString(), ex);
            return ResponseEntity.internalServerError().body(new ResponseError(ex));
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneUser(@PathVariable("id") Long id) {
        return null;
    }

}
