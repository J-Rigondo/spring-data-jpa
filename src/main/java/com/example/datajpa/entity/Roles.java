package com.example.datajpa.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Roles {
    @Id @GeneratedValue
    private Long id;
    private String name;

    @Builder
    public Roles(String name) {
        this.name = name;
    }
}
