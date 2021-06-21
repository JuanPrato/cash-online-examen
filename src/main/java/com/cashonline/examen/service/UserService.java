package com.cashonline.examen.service;

import com.cashonline.examen.dto.CreateUserDTO;
import com.cashonline.examen.dto.UserDTO;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.exception.InternalServerException;
import com.cashonline.examen.mapper.UserMapper;
import com.cashonline.examen.model.User;
import com.cashonline.examen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getOne(Long id) throws BadRequestException {
        if (id == null) {
            throw new BadRequestException("Invalid id");
        }
        User user = userRepository.getById(id);
        return userMapper.userToUserDTO(user);
    }

    public UserDTO createOne(CreateUserDTO userDTO) throws BadRequestException, InternalServerException {

        if (
            userDTO == null ||
            userDTO.getEmail() == null ||
            userDTO.getFirstName() == null ||
            userDTO.getLastName() == null
        ) {
            throw new BadRequestException("Invalid input");
        }

        User userToSaved = userMapper.createUserDTOToUser(userDTO);

        if (userToSaved == null) {
            throw new InternalServerException("Error mapping the user");
        }

        User userSaved = userRepository.save(userToSaved);

        return userMapper.userToUserDTO(userSaved);
    }

}
