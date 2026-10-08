package ru.gelman.messenger.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.gelman.messenger.dto.CreateUserDto;
import ru.gelman.messenger.dto.UserDto;
import ru.gelman.messenger.entity.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User convertToEntity(CreateUserDto userDto);

    UserDto convertToDto(User user);

}
