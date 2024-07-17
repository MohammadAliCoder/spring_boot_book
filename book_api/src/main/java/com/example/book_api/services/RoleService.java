package com.example.book_api.services;

import com.example.book_api.models.entities.Role;
import com.example.book_api.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public Role save(Role role){
        return roleRepository.save(role);
    }

    public void delete(Role role){
        roleRepository.delete(role);
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
