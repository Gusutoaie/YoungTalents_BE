package com.example.YoungTalens.service;


import com.example.YoungTalens.entity.Role;
import com.example.YoungTalens.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.save(new Role(null, "UVT Admin"));
            roleRepository.save(new Role(null, "Decan"));
            roleRepository.save(new Role(null, "Pro Decan"));
            roleRepository.save(new Role(null, "Simple User"));
            roleRepository.save(new Role(null, "Partner"));
        }
    }
}