package com.hepsiemlak.todo.mapper;

import com.hepsiemlak.todo.model.User;
import com.hepsiemlak.todo.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDto dto);
}