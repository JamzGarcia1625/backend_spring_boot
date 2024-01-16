package com.tps.jurados.persistence;

import com.tps.jurados.domain.dto.PersonDto;
import com.tps.jurados.domain.repository.IPersonRepository;
import com.tps.jurados.persistence.crud.PersonCrudRepository;
import com.tps.jurados.persistence.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository implements IPersonRepository {

    @Autowired
    private PersonCrudRepository personCrudRepository;

    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<PersonDto> getList() {
        return personMapper.toListPersonDto(personCrudRepository.findAll());
    }

    @Override
    public PersonDto findByPersonId(Integer id) {
        return personMapper.toPersonDto(personCrudRepository.findByPersonId(id));
    }

}
