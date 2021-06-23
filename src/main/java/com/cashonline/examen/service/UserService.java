package com.cashonline.examen.service;

import com.cashonline.examen.dto.CreateUserDTO;
import com.cashonline.examen.dto.UserDTO;
import com.cashonline.examen.exception.BadRequestException;
import com.cashonline.examen.exception.InternalServerException;
import com.cashonline.examen.exception.NotFoundException;
import com.cashonline.examen.mapper.UserMapper;
import com.cashonline.examen.model.User;
import com.cashonline.examen.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO getOne(Long id) throws BadRequestException, NotFoundException, InternalServerException, DataAccessException {
        if (id == null) {
            throw new BadRequestException("Invalid id");
        }
        User user;
        try {
             user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException ex) {
            throw new NotFoundException("User not found");
        } catch (Exception ex) {
            logger.error("EXCEPTION: GET ONE => ", ex);
            throw new InternalServerException("Error searching for user with id " + id);
        }
        return userMapper.userToUserDTO(user);
    }

    public UserDTO createOne(CreateUserDTO userDTO) throws Exception {

        if (
            userDTO == null ||
            userDTO.getEmail() == null ||
            userDTO.getFirstName() == null ||
            userDTO.getLastName() == null
        ) {
            throw new BadRequestException("Invalid input");
        }

        User userToSaved = userMapper.createUserDTOToUser(userDTO);

        User userSaved = userRepository.save(userToSaved);

        return userMapper.userToUserDTO(userSaved);
    }

    public UserDTO deleteOne(Long id) throws BadRequestException, NotFoundException {

        if (id == null) {
            throw new BadRequestException("Invalid id");
        }

        User userFound = userRepository.findById(id).orElseThrow( () ->
                new NotFoundException("User not found")
        );

        userRepository.delete(userFound);

        return userMapper.userToUserDTO(userFound);
    }
}
