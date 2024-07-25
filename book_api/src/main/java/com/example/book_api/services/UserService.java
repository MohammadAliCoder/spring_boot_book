package com.example.book_api.services;


import com.example.book_api.exceptions.DuplicateRecordException;
import com.example.book_api.models.dto.UserDto;
import com.example.book_api.models.entities.Role;
import com.example.book_api.models.entities.User;
import com.example.book_api.models.entities.UserDetailsImpl;
import com.example.book_api.repositories.RoleRepository;
import com.example.book_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserDto userDto) {
        if (findByEmail(userDto.getEmail()) == null) {
            userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            User user = User.to_Entity(userDto);
            Role userRole = roleRepository.findByRole("User");
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            user.setEnabled(true);
            userRepository.save(user);
        } else {
            throw new DuplicateRecordException("This's email: " + userDto.getEmail() + " already found");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void saveAdmin(UserDto userDto) {
        if (findByEmail(userDto.getEmail()) == null) {


            userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            User user = User.to_Entity(userDto);
            Role adminRole = roleRepository.findByRole("Admin");
            user.setRoles(new HashSet<Role>(Arrays.asList(adminRole)));
            user.setEnabled(true);
            userRepository.save(user);
        } else {
            throw new DuplicateRecordException("This's email:" + userDto.getEmail() + " already found");
        }
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Cacheable(cacheNames = "users", key = "#email")
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username);
        if (user != null) {
            return new UserDetailsImpl(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }



}
