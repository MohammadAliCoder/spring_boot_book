package com.example.book_api.models.dto;

import com.example.book_api.models.entities.User;
import lombok.*;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("ALL")
public class UserDto {

    private int id;
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
    private String code;


    public static UserDto to_Dto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }


}
