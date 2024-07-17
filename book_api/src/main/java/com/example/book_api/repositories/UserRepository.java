package com.example.book_api.repositories;


import com.example.book_api.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

   Optional<User> findByEmail(String email);

   List<User> findAllByRoles_Role(String role);

}
