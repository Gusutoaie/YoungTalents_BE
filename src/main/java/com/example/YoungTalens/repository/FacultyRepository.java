package com.example.YoungTalens.repository;


import com.example.YoungTalens.entity.Faculty;
import com.example.YoungTalens.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
        Optional<Faculty> findByName(String name);
        boolean existsByName(String name);


}
