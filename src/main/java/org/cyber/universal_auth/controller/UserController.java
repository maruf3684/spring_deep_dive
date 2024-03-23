package org.cyber.universal_auth.controller;

import lombok.RequiredArgsConstructor;
import org.cyber.universal_auth.model.user.User;
import org.cyber.universal_auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")  //notify in queue user is connected
    public User addUser(@Payload User user){
        userService.saveUser(user);
        return user;
    }
    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")  //notify in queue user is disconnected
    public User disconnect(@Payload User user){
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers(){
        return ResponseEntity.ok(userService.findConnectedUser());
    }
}
