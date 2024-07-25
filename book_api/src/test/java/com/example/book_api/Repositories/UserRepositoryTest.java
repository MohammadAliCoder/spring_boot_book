package com.example.book_api.Repositories;

import com.example.book_api.models.entities.User;
import com.example.book_api.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserRepositoryTest {
    @MockBean
    private UserRepository userRepository;
    private User user ;

    @BeforeEach
    public void setup(){
        user=new User(1,"Mohammad","mohammad@gmail.com","12345678",true);
    }

    @Test
    @DisplayName("Check user by email with correct value")
    @Order(1)
    void findByEmailCorrectValue(){
        given(userRepository.findByEmail("mohammad@gmail.com")).willReturn(Optional.ofNullable(user));
        Optional<User> userOptional=userRepository.findByEmail("mohammad@gmail.com");
        assertThat(userOptional).isEqualTo(Optional.ofNullable(user));
    }

    @Test
    @DisplayName("Check user by email with wrong value")
    @Order(2)
    void findByEmailWrongValue(){
        given(userRepository.findByEmail("mohammad@gmail.com")).willReturn(Optional.ofNullable(user));
        Optional<User> userOptional=userRepository.findByEmail("ali@gmail.com");
        assertThat(userOptional).isNotEqualTo(Optional.ofNullable(user));
    }

}
