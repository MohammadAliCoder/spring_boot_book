package com.example.book_api.repositories;

import com.example.book_api.models.entities.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

    Optional<List<Borrow>> findByBook_IdAndPatron_Id(int bookId, int partonId);
}
