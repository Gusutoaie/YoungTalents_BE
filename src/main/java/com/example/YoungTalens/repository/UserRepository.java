package com.example.YoungTalens.repository;

import com.example.YoungTalens.entity.Faculty;
import com.example.YoungTalens.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    List<User> findByFacultyAndYearOfStudy(Faculty faculty, String yearOfStudy);

    User findUserByEmail(String email);
    User findByToken(String token);
}
