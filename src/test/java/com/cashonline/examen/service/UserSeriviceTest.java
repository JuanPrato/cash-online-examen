package com.cashonline.examen.service;

import com.cashonline.examen.dto.CreateUserDTO;
import com.cashonline.examen.dto.UserDTO;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.exception.InternalServerException;
import com.cashonline.examen.mapper.UserMapper;
import com.cashonline.examen.model.User;
import com.cashonline.examen.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class UserSeriviceTest {

    private User user1;
    private UserDTO userDTO1;
    private CreateUserDTO createUserDTO;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {

        user1 = new User(1L, "email@email.com", "Juan", "Proto", new ArrayList<>());
        userDTO1 = new UserDTO(1L, "email@email.com", "Juan", "Prato", new ArrayList<>());
        createUserDTO = new CreateUserDTO("email@email.com", "Juan", "Proto");

    }

    @Test
    public void testGetOneOk () throws BadRequestException {

        Mockito.when(userRepository.getById(Mockito.any())).thenReturn(user1);

        UserDTO response = userService.getOne(Mockito.any());

        Mockito.verify(userMapper, Mockito.times(1)).userToUserDTO(Mockito.any());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(userDTO1, response);
    }

    @Test
    public void testGetOneIdNullShouldThrow () {
        Assertions.assertThrows(BadRequestException.class, () -> userService.getOne(null));
    }

    @Test
    public void testCreateOneOk () throws InternalServerException, BadRequestException {

        Mockito.when(userMapper.createUserDTOToUser(createUserDTO)).thenReturn(user1);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user1);
        Mockito.when(userMapper.userToUserDTO(user1)).thenReturn(userDTO1);

        Mockito.verify(userMapper, Mockito.times(1)).createUserDTOToUser(Mockito.any());
        Mockito.verify(userMapper, Mockito.times(1)).userToUserDTO(Mockito.any());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

        UserDTO response = userService.createOne(createUserDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(userDTO1, response);
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
    public void testCreateOneErrorMappingShoudlThrowException () {
        Mockito.when(userMapper.createUserDTOToUser(Mockito.any())).thenReturn(null);

        Assertions.assertThrows(InternalServerException.class, () -> userService.createOne(createUserDTO));
    }

}
