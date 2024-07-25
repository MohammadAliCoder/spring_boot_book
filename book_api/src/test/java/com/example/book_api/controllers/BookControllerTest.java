package com.example.book_api.controllers;

import com.example.book_api.configurations.TokenUtil;
import com.example.book_api.controllers.api.BookController;
import com.example.book_api.models.dto.BookDto;
import com.example.book_api.models.entities.Book;
import com.example.book_api.models.entities.Role;
import com.example.book_api.models.entities.User;
import com.example.book_api.models.entities.UserDetailsImpl;
import com.example.book_api.services.BookService;
import com.example.book_api.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private UserService userService;

    @MockBean
    private TokenUtil tokenUtil;
    private User userAuth;
    private Book book;
    private List<Book> books;
    private String token;
    private  ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        userAuth = new User(1, "Mohammad", "mohammad@gmail.com", "12345678", true);
        userAuth.setRoles(new HashSet<Role>(Arrays.asList(new Role(1, "User"))));
        UserDetailsImpl userDetails = new UserDetailsImpl(userAuth);
        token = tokenUtil.generateToken(userDetails);

        book = new Book(1, "book 1", "author 1", LocalDate.now(), LocalDate.now());
        books = Arrays.asList(book,
                new Book(2, "book 2", "author 2", LocalDate.now(), LocalDate.now())
        );

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }


    @Test
    @Order(1)
    @DisplayName("Check all books")
    void getAllBooksTest() throws Exception {
        given(bookService.getAll()).willReturn(books);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()", is(2)))
                .andExpect(jsonPath("$.data.[0].id", is(1)))
                .andExpect(jsonPath("$.data.[0].title", is("book 1")))
                .andExpect(jsonPath("$.data.[1].id", is(2)))
                .andExpect(jsonPath("$.data.[1].title", is("book 2")));

    }

    @Test
    @Order(2)
    @DisplayName("Check get book by id")
    void getBookTest() throws Exception {
        given(bookService.findById(1)).willReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/books/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.title", is("book 1")))
        ;
    }

    @Test
    @Order(3)
    @DisplayName("Check create book")
    void createBookTest() throws Exception {
        given(bookService.save(BookDto.to_Dto(book))).willReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(BookDto.to_Dto(book)))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.title", is("book 1")))
        ;
    }

    @Test
    @Order(4)
    @DisplayName("Check update book")
    void updateBookTest() throws Exception {
        book.setTitle("Spring Boot");
        given(bookService.update(1,BookDto.to_Dto(book))).willReturn(book);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/books/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(BookDto.to_Dto(book)))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.title", is("Spring Boot")))
        ;
    }

    @Test
    @Order(5)
    @DisplayName("Check delete book by id")
    void deleteBookTest() throws Exception {
        doNothing().when(bookService).deleteById(anyInt());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/books/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .with(user(userAuth.getEmail()).roles("User"));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.message", is("Successfully")))
        ;
    }

}
