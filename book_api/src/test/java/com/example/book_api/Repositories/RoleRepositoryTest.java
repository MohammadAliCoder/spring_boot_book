package com.example.book_api.Repositories;
//USing BDD Mockito

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.book_api.models.entities.Role;
import com.example.book_api.repositories.RoleRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class RoleRepositoryTest {

    @MockBean
    private RoleRepository roleRepository;
    private  Role adminRole;

    @BeforeEach
    public void setup(){
        adminRole = new Role(1, "Admin");
    }


    @Test
    @DisplayName("Check role with correct value")
    @Order(1)
    void findByRoleCorrectValue() {
        given(roleRepository.findByRole("Admin")).willReturn(adminRole);
        Role role = roleRepository.findByRole("Admin");
        assertThat(role).isEqualTo(adminRole);
    }

    @Test
    @DisplayName("Check role with wrong value")
    @Order(2)
    void findByRoleWrongValue() {
        given(roleRepository.findByRole("Admin")).willReturn(adminRole);
        Role role = roleRepository.findByRole("User");
        assertThat(role).isNotEqualTo(adminRole);
    }

}
