package com.example.YoungTalens.repository;

import com.example.YoungTalens.entity.ForumMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumMessageRepository extends JpaRepository<ForumMessage, Long> {
}
