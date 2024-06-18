package com.example.YoungTalens.controller;

import com.example.YoungTalens.entity.ChatRoom;
import com.example.YoungTalens.entity.Message;
import com.example.YoungTalens.entity.User;
import com.example.YoungTalens.service.ChatRoomService;
import com.example.YoungTalens.service.MessageService;
import com.example.YoungTalens.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chats")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ChatRoom getUserChatRoom(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user.getFaculty() == null || user.getYearOfStudy() == null) {
            throw new IllegalArgumentException("User must have a faculty and year of study");
        }
        return chatRoomService.getOrCreateChatRoom(user.getYearOfStudy(), user.getFaculty());
    }

    @PostMapping("/{chatRoomId}/join")
    public ChatRoom joinChatRoom(@PathVariable Long chatRoomId, @RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        return chatRoomService.addUserToChatRoom(userId, chatRoomId);
    }

    @PostMapping("/{chatRoomId}/messages")
    public Message sendMessage(@PathVariable Long chatRoomId, @RequestBody Map<String, String> body) {
        Long userId = Long.valueOf(body.get("userId"));
        String content = body.get("content");
        return messageService.sendMessage(userId, chatRoomId, content);
    }

    @GetMapping("/{chatRoomId}/messages")
    public List<Message> getMessages(@PathVariable Long chatRoomId) {
        return messageService.getMessages(chatRoomId);
    }
}
