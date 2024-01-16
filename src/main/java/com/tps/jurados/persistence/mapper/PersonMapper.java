package com.tps.jurados.persistence.mapper;

import com.tps.jurados.domain.dto.PersonDto;
import com.tps.jurados.persistence.entity.PersonEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    @InheritInverseConfiguration
    List<PersonDto> toListPersonDto(List<PersonEntity> persons);
    @InheritInverseConfiguration
    PersonDto toPersonDto(PersonEntity person);
}
