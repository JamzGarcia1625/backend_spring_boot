package com.tps.jurados.domain.service.impl;

import com.tps.jurados.domain.dto.PersonDto;
import com.tps.jurados.domain.repository.IPersonRepository;
import com.tps.jurados.domain.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private IPersonRepository personRepository;

    @Override
    public List<PersonDto> listPersons() {
        return personRepository.getList();
    }

    @Override
    public PersonDto findByPersonId(int id) {
        return personRepository.findByPersonId(id);
    }
}
