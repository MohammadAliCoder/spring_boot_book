package com.example.book_api.services;

import com.example.book_api.exceptions.RecordNotFoundException;
import com.example.book_api.models.dto.BookDto;
import com.example.book_api.models.entities.Book;
import com.example.book_api.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Book save(BookDto bookDto){
        return bookRepository.save(Book.to_Entity(bookDto));
    }

    @Transactional
    public Book update(int id,BookDto bookDto){
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return bookRepository.save(Book.to_Entity(bookDto));
        } else {
            throw new RecordNotFoundException("This book with id : " + id + " not found");
        }
    }


    @Transactional
    @Cacheable(cacheNames = "books", key = "#id")
    public Book findById(int id){
        return bookRepository.findById(id).orElse(null);
    }
    @Transactional
    public void deleteById(int id){
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("This book with id : " + id + " not found");
        }
    }

    @Transactional
    @Cacheable(cacheNames = "books")
    public List<Book> getAll(){
        return  bookRepository.findAll();
    }


}
