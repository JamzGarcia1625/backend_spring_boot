package com.tps.jurados.domain.repository;

import com.tps.jurados.domain.dto.PersonDto;

import java.util.List;

public interface IPersonRepository {
    List<PersonDto> getList();
    PersonDto findByPersonId(Integer id);
}
