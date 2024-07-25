package com.example.book_api.controllers;

import com.example.book_api.configurations.TokenUtil;
import com.example.book_api.controllers.api.BorrowController;
import com.example.book_api.models.dto.BorrowDto;
import com.example.book_api.models.entities.*;
import com.example.book_api.services.BorrowService;
import com.example.book_api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(BorrowController.class)
@AutoConfigureMockMvc
public class BorrowControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private BorrowService borrowService;
    @MockBean
    private TokenUtil tokenUtil;
    private String token;
    private User userAuth;
    private ObjectMapper mapper;
    private Borrow borrow,borrow1;
    private List<Borrow> borrows;

    @BeforeEach
    public void setup() {
        userAuth = new User(1, "Mohammad", "mohammad@gmail.com", "12345678", true);
        userAuth.setRoles(new HashSet<Role>(Arrays.asList(new Role(1, "User"))));
        UserDetailsImpl userDetails = new UserDetailsImpl(userAuth);
        token = tokenUtil.generateToken(userDetails);

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Book book = new Book(1, "book 1", "author 1", LocalDate.now(), LocalDate.now());
        Patron patron = new Patron(1, "Mohammad Ali", "mohammad@gmail.com", "+96911111111", "Damascus");

        borrow = new Borrow(1, new Date(2024, 7, 17), new Date(2024, 7, 17), patron, book);
        borrow1 = new Borrow(2, new Date(2024, 7, 17), new Date(2024, 7, 17), patron, book);

        borrows=Arrays.asList(borrow,borrow1);
    }


    @Test
    @Order(1)
    @DisplayName("Check set borrow")
    void setBorrowTest() throws Exception {
        given(borrowService.save(anyInt(), anyInt(),any(BorrowDto.class))).willReturn(borrow);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/borrow/{bookId}/patron/{patronId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(BorrowDto.to_Dto(borrow)))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)));

    }

    @Test
    @Order(2)
    @DisplayName("Check get borrow book")
    void getBorrowBookTest() throws Exception {
        given(borrowService.findByBookAndPatron(1,1)).willReturn(borrows);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/return/{bookId}/patron/{patronId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(2)))
                .andExpect(jsonPath("$.data.[0].id", is(1)))
                .andExpect(jsonPath("$.data.[1].id", is(2)));
    }
}
