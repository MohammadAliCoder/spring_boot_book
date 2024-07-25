package com.example.book_api.services;

import com.example.book_api.exceptions.RecordNotFoundException;
import com.example.book_api.models.dto.BookDto;
import com.example.book_api.models.entities.Book;
import com.example.book_api.repositories.BookRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BookServiceTest {
    @MockBean
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    private Book book;
    private Book book2;

    @BeforeEach
    public void setup() {
        book = new Book(1, "book 1", "author 1", LocalDate.now(), LocalDate.now());
        book2 = new Book(2, "book 2", "author 2", LocalDate.now(), LocalDate.now());

    }

    @Test
    @Order(1)
    void saveCorrectValue() {
        given(bookRepository.save(book)).willReturn(book);

        Book savedBook = bookService.save(BookDto.to_Dto(book));

        assertThat(savedBook).isEqualTo(book);
    }

    @Test
    @Order(2)
    void saveWrongValue() {
        given(bookRepository.save(any(Book.class))).willReturn(book2);

        Book savedBook = bookService.save(BookDto.to_Dto(book2));

        assertThat(savedBook).isNotEqualTo(book);
    }

    @Test
    @Order(3)
    void updateCorrectValue() {
        book2 = new Book(1, "book 2", "author 2", LocalDate.now(), LocalDate.now());
        given(bookRepository.findById(1)).willReturn(Optional.ofNullable(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book2);

        Book existingBook = bookService.update(1, BookDto.to_Dto(book2));

        assertThat(existingBook).isEqualTo(book2);
    }

    @Test
    @Order(4)
    void updateWrongValue() {
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> {
            bookService.update(1, BookDto.to_Dto(new Book(1, "book 2", "author 2", LocalDate.now(), LocalDate.now())));
        });
    }

    @Test
    @Order(5)
    void findByIdCorrectValue() {
        given(bookRepository.findById(1)).willReturn(Optional.ofNullable(book));

        Book existingBook = bookService.findById(1);

        assertThat(existingBook).isNotNull();
    }

    @Test
    @Order(6)
    void findByIdWrongValue() {
        given(bookRepository.findById(anyInt())).willReturn(Optional.empty());

        Book existingBook = bookService.findById(2);

        assertThat(existingBook).isNull();
    }

    @Test
    @Order(7)
    void deleteByIdCorrectValue() {
        given(bookRepository.findById(1)).willReturn(Optional.of(book));
        // action
        bookService.deleteById(1);

        // verify
        verify(bookRepository, times(1)).deleteById(1);
    }

    @Test
    @Order(8)
    void deleteByIdWrongValue() {
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> {
            bookService.deleteById(1);
        });
    }

    @Test
    @Order(9)
    void getAllCorrectValue() {
        List<Book> bookList = Arrays.asList(book, book2);
        given(bookRepository.findAll()).willReturn(bookList);

        List<Book> books = bookService.getAll();

        assertThat(books).isEqualTo(bookList);
    }

    @Test
    @Order(10)
    void getAllWrongValue() {
        List<Book> bookList = Arrays.asList(book, book2);
        given(bookRepository.findAll()).willReturn(Collections.emptyList());

        List<Book> books = bookService.getAll();

        assertThat(books).isNotEqualTo(bookList);
    }


}
