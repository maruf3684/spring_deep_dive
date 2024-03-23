package org.cyber.universal_auth.service;

import lombok.RequiredArgsConstructor;
import org.cyber.universal_auth.model.user.Status;
import org.cyber.universal_auth.model.user.User;
import org.cyber.universal_auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void saveUser(User user){
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    public void disconnect(User user){
        var storedUser = userRepository.findById(user.getNickName()).orElse(null);
        if(storedUser!=null){
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    public List<User> findConnectedUser(){
        return userRepository.findAllByStatus(Status.ONLINE);
    }
}
