package com.example.book_api.models.dto;

import com.example.book_api.models.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("ALL")
public class BookDto {

    private int id;
    @NotNull(message = "title is required")
    private String title;
    @NotNull(message = "author is required")
    private String author;
    @NotNull(message = "publication year is required")
    private LocalDate publication_year;
    @NotNull(message = "printing date is required")
    private LocalDate  printing_date;


    public static BookDto to_Dto(Book book){
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publication_year(book.getPublication_year())
                .printing_date(book.getPrinting_date())
                .build();
    }


}
