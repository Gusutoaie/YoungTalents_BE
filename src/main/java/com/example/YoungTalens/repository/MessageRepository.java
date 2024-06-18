package com.example.YoungTalens.repository;

import com.example.YoungTalens.entity.Message;
import com.example.YoungTalens.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoom(ChatRoom chatRoom);
}
