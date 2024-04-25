package com.example.YoungTalens.repository;


import com.example.YoungTalens.entity.Faculty;
import com.example.YoungTalens.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

}
