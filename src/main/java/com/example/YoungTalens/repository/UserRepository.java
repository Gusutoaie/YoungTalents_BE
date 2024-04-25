package com.example.YoungTalens.repository;

import com.example.YoungTalens.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);

    User findUserByEmail(String email);
    User findByToken(String token);
}
