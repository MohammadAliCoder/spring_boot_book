package com.example.book_api.Repositories;

import com.example.book_api.models.entities.Book;
import com.example.book_api.models.entities.Borrow;
import com.example.book_api.models.entities.Patron;
import com.example.book_api.models.entities.User;
import com.example.book_api.repositories.BorrowRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class BorrowRepositoryTest {
    @MockBean
    private BorrowRepository borrowRepository;
    private Book book;
    private Patron patron1;
    private User user1;

    private Optional<List<Borrow>> borrows;

    @BeforeEach
    public void setup() {
        user1 = new User(1, "Mohammad Ali", "mohammad@gmail.com", "12345678", true);

        book = new Book(1, "Book 1", "Author 1", LocalDate.now(), LocalDate.now());

        patron1 = new Patron(1, "Mohammad Ali", "mohammad@gmail.com", "+96911111111", "Damascus", user1);

        List<Borrow> list = Arrays.asList(
                new Borrow(1, new Date(2024, 7, 17), new Date(2024, 7, 17), patron1, book),
                new Borrow(1, new Date(2024, 7, 17), new Date(2024, 7, 17), patron1, book));

        borrows = Optional.ofNullable(list);
    }

    @Test
    @DisplayName("check findByBook_IdAndPatron_Id with correct value")
    @Order(1)
    void findByBook_IdAndPatron_Id_CorrectValue() {
        given(borrowRepository.findByBook_IdAndPatron_Id(1, 1))
                .willReturn(borrows);
        Optional<List<Borrow>> borrowList=  borrowRepository.findByBook_IdAndPatron_Id(1,1);
        assertThat(borrows).isEqualTo(borrowList);
    }

    @Test
    @DisplayName("check findByBook_IdAndPatron_Id with wrong value")
    @Order(2)
    void findByBook_IdAndPatron_Id_WrongValue() {
        given(borrowRepository.findByBook_IdAndPatron_Id(1, 1))
                .willReturn(borrows);
        Optional<List<Borrow>> borrowList=  borrowRepository.findByBook_IdAndPatron_Id(2,1);
        assertThat(borrows).isNotEqualTo(borrowList);
    }
}
