package com.bchat.model.po;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Repository;

@Data
@Entity
@Table(name = "roles")
@Repository
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role() {}
}