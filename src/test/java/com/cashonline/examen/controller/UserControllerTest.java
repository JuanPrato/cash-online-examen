package com.cashonline.examen.controller;

import com.cashonline.examen.common.ResponseError;
import com.cashonline.examen.dto.CreateUserDTO;
import com.cashonline.examen.dto.UserDTO;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.exception.InternalServerException;
import com.cashonline.examen.exception.NotFoundException;
import com.cashonline.examen.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    private UserDTO userDTO;
    private CreateUserDTO createUserDTO;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private String user1String;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO(1L, "email@email.com.ar", "Juan", "Proto", new ArrayList<>());
        createUserDTO = new CreateUserDTO("email@email.com.ar", "Juan", "Proto");
    }

    @Test
    public void testGetOneUserOk () throws Exception {

        final long id = 1L;

        Mockito.when(userService.getOne(Mockito.any())).thenReturn(userDTO);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertNotNull(result);
    }

    @Test
    public void testGetOneUserBadRequest() throws Exception {
        Mockito.when(userService.getOne(Mockito.any())).thenThrow(BadRequestException.class);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users/" + 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertNotNull(result);
    }

    @Test
    public void testGetOneUserUserNotFound () throws Exception {

        Mockito.when(userService.getOne(Mockito.any())).thenThrow(NotFoundException.class);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users/" + 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        Assertions.assertNotNull(result);

    }

    @Test
    public void testGetOneUserInternalServerError () throws Exception {
        Mockito.when(userService.getOne(Mockito.any())).thenThrow(Mockito.mock(DataAccessException.class));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/users/" + 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        Assertions.assertNotNull(result);
    }

    @Test
    public void testCreateOneUserOk () throws Exception {
        Mockito.when(userService.createOne(Mockito.any())).thenReturn(userDTO);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(objectMapper.writeValueAsString(userDTO), result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateOneUserBadRequest () throws Exception {

        Mockito.when(userService.createOne(Mockito.any())).thenThrow(new BadRequestException("Invalid input"));

        ResponseError response = new ResponseError(new BadRequestException("Invalid input"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(objectMapper.writeValueAsString(response), result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateOneUserInternalServerError () throws Exception {

        Mockito.when(userService.createOne(Mockito.any())).thenThrow(Exception.class);

        ResponseError response = new ResponseError(new Exception());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(objectMapper.writeValueAsString(response), result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteOneUserOk () throws Exception {

        Mockito.when(userService.deleteOne(Mockito.any())).thenReturn(userDTO);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/users/" + 1)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertNotNull(result);
    }

    @Test
    public void testDeleteOneUserBadRequest () throws Exception {

        Mockito.when(userService.deleteOne(Mockito.any())).thenThrow(new BadRequestException("Invalid id"));

        ResponseError response = new ResponseError(new BadRequestException("Invalid id"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/users/" + 1)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertNotNull(result);
    }

    @Test
    public void testDeleteOneUserInternalServerError () throws Exception {

        Mockito.when(userService.deleteOne(Mockito.any())).thenThrow(Mockito.mock(DataAccessException.class));

        ResponseError response = new ResponseError(new InternalServerException("unknown exception"));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/users/" + 1)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        Assertions.assertNotNull(result);

    }
}
