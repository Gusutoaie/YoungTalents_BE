package com.example.YoungTalens.service;

import com.example.YoungTalens.entity.ChatRoom;
import com.example.YoungTalens.entity.Faculty;
import com.example.YoungTalens.entity.User;
import com.example.YoungTalens.repository.ChatRoomRepository;
import com.example.YoungTalens.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;

    public ChatRoom getOrCreateChatRoom(String yearOfStudy, Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Faculty cannot be null");
        }

        Optional<ChatRoom> chatRoom = chatRoomRepository.findByYearOfStudyAndFaculty(yearOfStudy, faculty);
        if (chatRoom.isPresent()) {
            ChatRoom existingRoom = chatRoom.get();
            List<User> members = userRepository.findByFacultyAndYearOfStudy(faculty, yearOfStudy);
            existingRoom.setMembers(new HashSet<>(members));
            return existingRoom;
        } else {
            ChatRoom newChatRoom = new ChatRoom();
            newChatRoom.setYearOfStudy(yearOfStudy);
            newChatRoom.setFaculty(faculty);
            newChatRoom.setName(faculty.getName() + " " + yearOfStudy + " Chat");

            List<User> members = userRepository.findByFacultyAndYearOfStudy(faculty, yearOfStudy);
            newChatRoom.setMembers(new HashSet<>(members));

            return chatRoomRepository.save(newChatRoom);
        }
    }

    public ChatRoom addUserToChatRoom(Long userId, Long chatRoomId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new RuntimeException("Chat room not found"));
        chatRoom.getMembers().add(user);
        return chatRoomRepository.save(chatRoom);
    }
}
