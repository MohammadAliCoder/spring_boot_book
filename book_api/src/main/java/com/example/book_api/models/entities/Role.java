package com.example.book_api.models.entities;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuppressWarnings("ALL")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String role;


}
