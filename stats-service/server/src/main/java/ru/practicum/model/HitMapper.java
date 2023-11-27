package ru.practicum.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import ru.practicum.HitDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HitMapper {
    HitMapper INSTANCE = Mappers.getMapper(HitMapper.class);

    @Mapping(source = "app.name", target = "app")
    HitDto toHitDto(Hit hit);

    @Mapping(source = "app", target = "app.name")
    Hit toHit(HitDto hitDto);
}
