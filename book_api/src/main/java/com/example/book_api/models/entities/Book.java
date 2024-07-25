package com.example.book_api.models.entities;

import com.example.book_api.models.dto.BookDto;
import com.example.book_api.models.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuppressWarnings("ALL")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "title is required")
    private String title;
    @NotNull(message = "author is required")
    private String author;
    @NotNull(message = "publication year is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publication_year;
    @NotNull(message = "Printing date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate printing_date;

    @JsonIgnore
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Borrow> borrows;

    public Book(int id, @NotNull(message = "title is required") String title, @NotNull(message = "author is required") String author, @NotNull(message = "publication year is required") LocalDate publication_year, @NotNull(message = "Printing date is required") LocalDate printing_date) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publication_year = publication_year;
        this.printing_date = printing_date;
    }

    public static Book to_Entity(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .publication_year(bookDto.getPublication_year())
                .printing_date(bookDto.getPrinting_date())
                .build();
    }
}
