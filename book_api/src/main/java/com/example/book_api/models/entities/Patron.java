package com.example.book_api.models.entities;

import com.example.book_api.models.dto.PatronDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "patrons")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuppressWarnings("ALL")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "mobile is required")
    private String mobile;
    @NotNull(message = "address is required")
    private String address;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @OneToMany(mappedBy = "patron", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    List<Borrow> borrows;

    public static Patron to_Entity(PatronDto patronDto) {
        return Patron.builder()
                .id(patronDto.getId())
                .name(patronDto.getName())
                .email(patronDto.getEmail())
                .mobile(patronDto.getMobile())
                .address(patronDto.getAddress())
                .build();
    }



}
