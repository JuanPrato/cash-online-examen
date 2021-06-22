package com.cashonline.examen.mapper;

import com.cashonline.examen.dto.CreateUserDTO;
import com.cashonline.examen.dto.UserDTO;
import com.cashonline.examen.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = LoanMapper.class)
public interface UserMapper {

    @Mapping(source = "user_id", target = "id")
    UserDTO userToUserDTO(User user);

    User createUserDTOToUser(CreateUserDTO user);
}
