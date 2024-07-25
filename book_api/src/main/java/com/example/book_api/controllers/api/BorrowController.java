package com.example.book_api.controllers.api;

import com.example.book_api.models.dto.BorrowDto;
import com.example.book_api.models.dto.PatronDto;
import com.example.book_api.models.otd.MyResponse;
import com.example.book_api.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class BorrowController {
    @Autowired
    BorrowService borrowService;

    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> setBorrow(
            @PathVariable("bookId")  int bookId,
            @PathVariable("patronId")  int patronId,
            @RequestBody @Valid BorrowDto borrowDto){
        return new ResponseEntity<>(new MyResponse("Successfully",
                true,
                borrowService.save(bookId,patronId,borrowDto)
        ), HttpStatus.OK);
    }


    @PutMapping ("return/{bookId}/patron/{patronId}")
    public ResponseEntity<?> getBorrowBook(@PathVariable("bookId")  int bookId,
                                           @PathVariable("patronId")  int patronId){
        return new ResponseEntity<>(new MyResponse("Successfully",
                true,
                borrowService.findByBookAndPatron(bookId,patronId)
        ), HttpStatus.OK);
    }
}
