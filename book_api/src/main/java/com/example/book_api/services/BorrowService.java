package com.example.book_api.services;

import com.example.book_api.exceptions.RecordNotFoundException;
import com.example.book_api.models.dto.BorrowDto;
import com.example.book_api.models.entities.Book;
import com.example.book_api.models.entities.Borrow;
import com.example.book_api.models.entities.Patron;
import com.example.book_api.repositories.BookRepository;
import com.example.book_api.repositories.BorrowRepository;
import com.example.book_api.repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private BookRepository bookRepository;




    @Transactional
    public Borrow save(int bookId, int patronId, BorrowDto borrowDto){
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Patron> patron = patronRepository.findById(patronId);
        if (!book.isPresent()){
            throw new RecordNotFoundException("This book with id : " + bookId + " not found");
        }else if (!patron.isPresent()){
            throw new RecordNotFoundException("This patron with id : " + patronId + " not found");
        }else {
            Borrow borrow=Borrow.to_Entity(borrowDto);
            borrow.setBook(book.get());
            borrow.setPatron(patron.get());
            return borrowRepository.save(borrow);
        }

    }

    @Transactional
    public List<Borrow> findByBookAndPatron(int bookId, int patronId){
        return borrowRepository.findByBook_IdAndPatron_Id(bookId,patronId).orElse(null);
    }
}
