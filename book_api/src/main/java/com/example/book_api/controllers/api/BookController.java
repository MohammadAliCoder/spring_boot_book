package com.example.book_api.controllers.api;

import com.example.book_api.models.dto.BookDto;
import com.example.book_api.models.otd.MyResponse;
import com.example.book_api.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("")
    public ResponseEntity<?> getBooks(){
        return new ResponseEntity<>(new MyResponse("Successfully",
                true,
                bookService.getAll()
                ), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getBook(@PathVariable("id")  int id){
        return new ResponseEntity<>(new MyResponse("Successfully",
                true,
                bookService.findById(id)
                ), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody @Valid BookDto bookDto){
        return new ResponseEntity<>(new MyResponse("Successfully",
                true,
                bookService.save(bookDto)
        ), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id")  int id,@RequestBody @Valid BookDto bookDto){
        bookDto.setId(id);
        return new ResponseEntity<>(new MyResponse("Successfully",
                true,
                bookService.update(id,bookDto)
        ), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id")  int id){
        bookService.deleteById(id);
        return new ResponseEntity<>(new MyResponse("Successfully", true), HttpStatus.OK);
    }

}
