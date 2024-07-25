package com.example.book_api.models.entities;

import com.example.book_api.models.dto.BorrowDto;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "borrows")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuppressWarnings("ALL")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "return date is required")
    private Date return_date;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "borrowing date is required")
    private Date  borrowing_date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patron_id", nullable = false)
    Patron patron;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    Book book;



    public static Borrow to_Entity(BorrowDto borrowDto) {
        return Borrow.builder()
                .id(borrowDto.getId())
                .return_date(borrowDto.getReturn_date())
                .borrowing_date(borrowDto.getBorrowing_date())
                .build();
    }
}
