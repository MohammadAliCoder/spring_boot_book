package com.example.book_api.controllers;

import com.example.book_api.controllers.api.BookController;
import com.example.book_api.models.entities.Book;
import com.example.book_api.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1,"Book 1","Author 1", LocalDate.now(),LocalDate.now()),
                new Book(2,"Book 2","Author 2", LocalDate.now(),LocalDate.now())
        );
        when(bookService.getAll()).thenReturn(books);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("api/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Author 2"));

    }



}
