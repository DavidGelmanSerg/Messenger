package ru.gelman.messenger.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.gelman.messenger.dto.ChatResponse;
import ru.gelman.messenger.entity.Chat;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class}
)
public interface ChatMapper {

    @Mapping(target = "userIds", source = "users", qualifiedByName = "userToId")
    ChatResponse toChatResponse(Chat chat);
}
