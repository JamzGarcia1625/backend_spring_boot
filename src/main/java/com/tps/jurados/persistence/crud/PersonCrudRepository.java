package com.tps.jurados.persistence.crud;

import com.tps.jurados.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonCrudRepository extends JpaRepository<PersonEntity, Integer> {

    List<PersonEntity> findAll();
    PersonEntity findByPersonId(Integer id);
}
