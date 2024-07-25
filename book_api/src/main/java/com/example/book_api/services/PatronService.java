package com.example.book_api.services;

import com.example.book_api.exceptions.RecordNotFoundException;
import com.example.book_api.models.dto.PatronDto;
import com.example.book_api.models.entities.Patron;
import com.example.book_api.models.entities.User;
import com.example.book_api.repositories.PatronRepository;
import com.example.book_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatronService {
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private UserRepository userRepository;


    @Transactional
    public Patron save(PatronDto patronDto, User user) {
        Patron patron = Patron.to_Entity(patronDto);
        patron.setUser(user);
        user.setPatron(patron);
        return userRepository.save(user).getPatron();
    }


    @Transactional
    @Cacheable(cacheNames = "patrons", key = "#id")
    public Patron findById(int id) {
        return patronRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(int id) {
        Optional<Patron> patron = patronRepository.findById(id);
        if (patron.isPresent()) {
            patronRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("This patron with id : " + id + " not found");
        }
    }

    @Transactional
    @Cacheable(cacheNames = "patrons")
    public List<Patron> getAll() {
        return patronRepository.findAll();
    }

}
