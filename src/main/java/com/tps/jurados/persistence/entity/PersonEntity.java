package com.tps.jurados.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "person")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer personId;
    @Column(name = "person_name")
    private String personName;
    @Column(name = "person_lastname")
    private String personLastname;
    @Column(name = "person_dni")
    private String personDni;
    @Column(name = "person_email")
    private String personEmail;
    @Column(name = "person_phone")
    private String personPhone;
    @Column(name = "person_create_at")
    private LocalDateTime personCreateAt;

}
