package com.tps.jurados.web.controller;

import com.tps.jurados.domain.dto.PersonDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpensesController {
    @GetMapping("/{dni}")
    @PreAuthorize("hasAuthority('expenses-id:get')")
    public ResponseEntity<String> getPerson(@PathVariable("dni") Integer id) {
        return ResponseEntity.ok("Ok");
    }
}
