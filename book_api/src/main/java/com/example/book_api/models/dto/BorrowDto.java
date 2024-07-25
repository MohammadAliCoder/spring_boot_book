package com.example.book_api.models.dto;

import com.example.book_api.models.entities.Borrow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("ALL")
public class BorrowDto {

    private int id;

    @NotNull(message = "return date is required")
    private Date return_date;

    @NotNull(message = "borrowing date is required")
    private Date  borrowing_date;

    public static BorrowDto to_Dto(Borrow borrow){
        return BorrowDto.builder()
                .id(borrow.getId())
                .return_date(borrow.getReturn_date())
                .borrowing_date(borrow.getBorrowing_date())
                .build();
    }
}
