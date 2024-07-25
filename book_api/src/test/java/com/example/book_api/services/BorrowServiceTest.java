package com.example.book_api.services;

import com.example.book_api.exceptions.RecordNotFoundException;
import com.example.book_api.models.dto.BorrowDto;
import com.example.book_api.models.entities.Book;
import com.example.book_api.models.entities.Borrow;
import com.example.book_api.models.entities.Patron;
import com.example.book_api.repositories.BookRepository;
import com.example.book_api.repositories.BorrowRepository;
import com.example.book_api.repositories.PatronRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BorrowServiceTest {
    @MockBean
    private BorrowRepository borrowRepository;
    @MockBean
    private PatronRepository patronRepository;
    @MockBean
    private BookRepository bookRepository;
    @InjectMocks
    private BorrowService borrowService;

    private Borrow borrow;
    private Patron patron;
    private Patron patron1;
    private Book book;
    private Optional<List<Borrow>> borrows;


    @BeforeEach
    public void setup() {
        book = new Book(1, "book 1", "author 1", LocalDate.now(), LocalDate.now());
        patron = new Patron(1, "Mohammad Ali", "mohammad@gmail.com", "+96911111111", "Damascus");
        patron = new Patron(2, "Ali", "ali@gmail.com", "+96922222222", "Damascus");
        borrow = new Borrow(1, new Date(2024, 7, 17), new Date(2024, 7, 17), patron, book);

        List<Borrow> list = Arrays.asList(
                borrow,
                new Borrow(1, new Date(2024, 7, 17), new Date(2024, 7, 17), patron1, book));
        borrows = Optional.ofNullable(list);
    }

    @Test
    @Order(1)
    void saveCorrectValue() {
        given(patronRepository.findById(1)).willReturn(Optional.of(patron));
        given(bookRepository.findById(1)).willReturn(Optional.of(book));
        when(borrowRepository.save(borrow)).thenReturn(borrow);

        Borrow borrowSaved = borrowService.save(1, 1, BorrowDto.to_Dto(borrow));

        assertThat(borrowSaved).isEqualTo(borrow);

    }

    @Test
    @Order(2)
    void saveWrongValue() {
        given(patronRepository.findById(1)).willReturn(Optional.of(patron));
        given(bookRepository.findById(1)).willReturn(Optional.of(book));
        when(borrowRepository.save(borrow)).thenReturn(borrow);

        assertThrows(RecordNotFoundException.class, () -> {
            borrowService.save(2, 1, BorrowDto.to_Dto(borrow));
        });

        assertThrows(RecordNotFoundException.class, () -> {
            borrowService.save(1, 2, BorrowDto.to_Dto(borrow));
        });
    }

    @Test
    @Order(3)
    void findByBookAndPatron_CorrectValue() {
        given(borrowRepository.findByBook_IdAndPatron_Id(1, 1)).willReturn(borrows);

        List<Borrow> borrowList = borrowService.findByBookAndPatron(1, 1);

        assertThat(borrowList).isEqualTo(borrows.get());
    }

    @Test
    @Order(4)
    void findByBookAndPatron_WrongValue() {
        given(borrowRepository.findByBook_IdAndPatron_Id(1, 1)).willReturn(borrows);

        List<Borrow> borrowList = borrowService.findByBookAndPatron(2, 2);

        assertThat(borrowList).isNotEqualTo(borrows.get());
        assertThat(borrowList).isNull();
    }


}
