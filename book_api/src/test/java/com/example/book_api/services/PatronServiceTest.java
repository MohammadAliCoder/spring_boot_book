package com.example.book_api.services;

import com.example.book_api.exceptions.RecordNotFoundException;
import com.example.book_api.models.dto.PatronDto;
import com.example.book_api.models.entities.Patron;
import com.example.book_api.models.entities.User;
import com.example.book_api.repositories.PatronRepository;
import com.example.book_api.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatronServiceTest {
    @Mock
    private PatronRepository patronRepository;
    @InjectMocks
    private PatronService patronService;
    @Mock
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private Patron patron;
    private PatronDto patronDto;
    private PatronDto patronDto2;

    @BeforeEach
    public void setup() {
        user1 = new User(1, "Mohammad Ali", "mohammad@gmail.com", "12345678", true);
        patronDto = new PatronDto(1, "Mohammad Ali", "mohammad@gmail.com", "+96911111111", "Damascus");
        patronDto2 = new PatronDto(2, "Ali", "ali@gmail.com", "+96911111111", "Damascus");
        user2 = new User(2, "Mohammad", "mohammad2@gmail.com", "12345678", true);
    }

    @Test
    @Order(1)
    void saveCorrectValue() {
        // precondition
        given(userRepository.save(user1)).willReturn(user1);

        //action
        patron = Patron.to_Entity(patronDto);
        patron.setUser(user1);
        user1.setPatron(patron);
        Patron savedPatron = patronService.save(patronDto, user1);

        assertThat(savedPatron).isEqualTo(patron);
    }

    @Test
    @Order(2)
    void saveWrongValue() {
        given(userRepository.save(user1)).willReturn(user1);

        patron = Patron.to_Entity(patronDto);
        patron.setUser(user2);
        user2.setPatron(patron);
        Patron savedPatron = patronService.save(patronDto, user1);

        assertThat(savedPatron).isNotEqualTo(patron);
    }

    @Test
    @Order(3)
    void findByIdCorrectValue() {
        patron = Patron.to_Entity(patronDto);
        patron.setUser(user1);
        user1.setPatron(patron);
        given(patronRepository.findById(1)).willReturn(Optional.of(patron));

        Patron existingPatron = patronService.findById(1);

        assertThat(existingPatron).isNotNull();
    }

    @Test
    @Order(4)
    void findByIdWrongValue() {
        given(patronRepository.findById(anyInt())).willReturn(Optional.empty());

        Patron existingPatron = patronService.findById(2);

        assertThat(existingPatron).isNull();
    }

    @Test
    @Order(5)
    void deleteByIdCorrectValue() {
        patron = Patron.to_Entity(patronDto);
        patron.setUser(user1);
        user1.setPatron(patron);
        given(patronRepository.findById(1)).willReturn(Optional.of(patron));
        // action
        patronService.deleteById(1);

        // verify
        verify(patronRepository, times(1)).deleteById(1);

    }

    @Test
    @Order(6)
    void deleteByIdWrongValue() {
        when(patronRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> {
            patronService.deleteById(1);
        });
    }

    @Test
    void getAllCorrectValue() {
        given(patronRepository.findAll()).willReturn(Arrays.asList(Patron.to_Entity(patronDto),
                Patron.to_Entity(patronDto2)));

        List<Patron> patrons = patronService.getAll();

        assertThat(patrons).isNotNull();
        assertThat(patrons.size()).isEqualTo(2);
    }

    @Test
    void getAllWrongValue() {
        List<Patron> patronList = Arrays.asList(Patron.to_Entity(patronDto),
                Patron.to_Entity(patronDto2));
        given(patronRepository.findAll()).willReturn(Collections.emptyList());

        List<Patron> patrons = patronService.getAll();

        assertThat(patrons).isNotEqualTo(patronList);
    }
}
