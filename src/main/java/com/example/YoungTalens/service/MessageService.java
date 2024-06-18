package com.example.YoungTalens.service;

import com.example.YoungTalens.entity.ChatRoom;
import com.example.YoungTalens.entity.Message;
import com.example.YoungTalens.entity.User;
import com.example.YoungTalens.repository.MessageRepository;
import com.example.YoungTalens.repository.ChatRoomRepository;
import com.example.YoungTalens.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    public Message sendMessage(Long userId, Long chatRoomId, String content) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new RuntimeException("Chat room not found"));

        Message message = new Message();
        message.setContent(content);
        message.setTimestamp(new Date());
        message.setUser(user);
        message.setChatRoom(chatRoom);

        return messageRepository.save(message);
    }

    public List<Message> getMessages(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new RuntimeException("Chat room not found"));
        return messageRepository.findByChatRoom(chatRoom);
    }
}
