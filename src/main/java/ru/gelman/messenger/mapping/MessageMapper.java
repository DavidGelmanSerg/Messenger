package ru.gelman.messenger.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.gelman.messenger.dto.MessageDto;
import ru.gelman.messenger.entity.Message;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface MessageMapper {
    MessageDto toMessageDto(Message message);
}
