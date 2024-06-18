package com.example.YoungTalens.repository;

import com.example.YoungTalens.entity.ChatRoom;
import com.example.YoungTalens.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByYearOfStudyAndFaculty(String yearOfStudy, Faculty faculty);

}
