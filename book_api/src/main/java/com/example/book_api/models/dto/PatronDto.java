package com.example.book_api.models.dto;

import com.example.book_api.models.entities.Book;
import com.example.book_api.models.entities.Patron;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("ALL")
public class PatronDto {

    private int id;
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "mobile is required")
    private String mobile;
    @NotNull(message = "address is required")
    private String address;


    public static PatronDto to_Dto(Patron patron){
        return PatronDto.builder()
                .id(patron.getId())
                .name(patron.getName())
                .email(patron.getEmail())
                .mobile(patron.getMobile())
                .address(patron.getAddress())
                .build();
    }
}
