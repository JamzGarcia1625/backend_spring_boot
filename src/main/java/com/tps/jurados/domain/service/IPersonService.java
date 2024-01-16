package com.tps.jurados.domain.service;

import com.tps.jurados.domain.dto.PersonDto;

import java.util.List;

public interface IPersonService {
    List<PersonDto> listPersons ();
    PersonDto findByPersonId(int id);
}
