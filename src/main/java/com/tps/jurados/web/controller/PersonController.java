package com.tps.jurados.web.controller;

import com.tps.jurados.domain.dto.PersonDto;
import com.tps.jurados.domain.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/person", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    @Autowired
    private IPersonService personService;

    @GetMapping
    @PreAuthorize("hasAuthority('person-list:get')")
    public ResponseEntity<List<PersonDto>> getListPerson() {
        try {
            return ResponseEntity.ok(personService.listPersons());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('person-id:get')")
    public ResponseEntity<PersonDto> getPerson(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(personService.findByPersonId(id));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
