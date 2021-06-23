package com.cashonline.examen.service;

import com.cashonline.examen.dto.CreateUserDTO;
import com.cashonline.examen.dto.UserDTO;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.exception.InternalServerException;
import com.cashonline.examen.exception.NotFoundException;
import com.cashonline.examen.mapper.UserMapper;
import com.cashonline.examen.model.User;
import com.cashonline.examen.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    private User user1;
    private UserDTO userDTO1;
    private CreateUserDTO createUserDTO;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {

        user1 = new User(1L, "email@email.com", "Juan", "Proto", new ArrayList<>());
        userDTO1 = new UserDTO(1L, "email@email.com", "Juan", "Proto", new ArrayList<>());
        createUserDTO = new CreateUserDTO("email@email.com", "Juan", "Proto");

    }

    @Test
    public void testGetOneOk () throws BadRequestException, NotFoundException, InternalServerException {

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user1));
        Mockito.when(userMapper.userToUserDTO(Mockito.any())).thenReturn(userDTO1);

        UserDTO response = userService.getOne(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(userDTO1, response);
        Mockito.verify(userMapper, Mockito.times(1)).userToUserDTO(Mockito.any());
    }

    @Test
    public void testGetOneIdThatNotExists() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.getOne(1L));
    }

    @Test
    public void testGetOneIdNullShouldThrow () {
        Assertions.assertThrows(BadRequestException.class, () -> userService.getOne(null));
    }

    @Test
    public void testGetOneFindByIdThrowsShouldThrowException () {
        Mockito.when(userRepository.findById(Mockito.any())).thenThrow();

        Assertions.assertThrows(InternalServerException.class, () -> userService.getOne(1L));
    }

    @Test
    public void testCreateOneOk () throws Exception {

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user1);
        Mockito.when(userMapper.userToUserDTO(Mockito.any())).thenReturn(userDTO1);

        UserDTO response = userService.createOne(createUserDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(userDTO1, response);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void testCreateOnePassNullShouldThrowException() {
        Assertions.assertThrows(BadRequestException.class, () -> userService.createOne(null));
    }

    @Test
    public void testCreateOnePassEmailNullShouldThrowException () {
        Assertions.assertThrows(BadRequestException.class, () -> userService.createOne(new CreateUserDTO(null, "Juan", "Proto")));
    }

    @Test
    public void testCreateOnePassFirstNameNullShouldThrowException () {
        Assertions.assertThrows(BadRequestException.class, () -> userService.createOne(new CreateUserDTO("email", null, "Proto")));
    }

    @Test
    public void testCreateOnePassLastNameNullShouldThrowException () {
        Assertions.assertThrows(BadRequestException.class, () -> userService.createOne(new CreateUserDTO("email", "Juan", null)));
    }

    @Test
    public void testDeleteOneOk () throws BadRequestException, NotFoundException {

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user1));
        Mockito.when(userMapper.userToUserDTO(user1)).thenReturn(userDTO1);

        UserDTO response = userService.deleteOne(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(userDTO1, response);
    }

    @Test
    public void testDeleteOneIdNull () {

        Assertions.assertThrows(BadRequestException.class, () -> userService.deleteOne(null));

    }

    @Test
    public void testDeleteOneUserNotFound () {

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.deleteOne(1L));

    }
}
