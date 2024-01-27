package com.learn.spring.service;

import com.learn.spring.model.User;
import com.learn.spring.model.UserRelation;
import com.learn.spring.repository.UserRelationRepository;
import com.learn.spring.repository.UserRepository;
import com.learn.spring.wrapper.UserRequest;
import com.learn.spring.wrapper.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signUp(UserRequest userRequest){
        userRepository.save(requestToUser(userRequest));
    }

    public UserResponse login(UserRequest userRequest){
        User thisUser = userRepository.findByEmailAndPassword(userRequest.getEmail(), userRequest.getPassword());
        if(thisUser==null)
            return null;
        return userToResponse(thisUser);
    }

    public UserResponse getById(Long id) {
        return userToResponse(Objects.requireNonNull(userRepository.findById(id).orElse(null)));
    }

    public boolean editUser(Long id, UserRequest uR){
        User thisUser= userRepository.findById(id).orElse(null);
        if(thisUser==null)
            return false;
        if(uR.getEmail()!=null&&!uR.getEmail().isBlank())
            thisUser.setEmail(uR.getEmail());
        if(uR.getFirstName()!=null&&!uR.getFirstName().isBlank())
            thisUser.setFirstName(uR.getFirstName());
        if(uR.getLastName()!=null&&!uR.getLastName().isBlank())
            thisUser.setLastName(uR.getFirstName());
        if(uR.getProfileName()!=null&&!uR.getProfileName().isBlank())
            thisUser.setProfileName(uR.getProfileName());
        if(uR.getPhone()!=null&&!uR.getPhone().isBlank())
            thisUser.setPhone(uR.getPhone());
        if(uR.getPassword()!=null&&!uR.getPassword().isBlank())
            thisUser.setPassword(uR.getPassword());
        userRepository.save(thisUser);
        return true;
    }

    public List<UserResponse> getUserFriendList(Long userId){
        return userRepository.getByFriendList(userId).stream().map(user -> userToResponse(user)).collect(Collectors.toList());
    }


    public User requestToUser(UserRequest userRequest){
        return new User(userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getProfileName(),
                userRequest.getPhone(),
                userRequest.getEmail(),
                userRequest.getPassword());
    }

    public UserResponse userToResponse(User user){
        return new UserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfileName(),
                user.getEmail(),
                user.getPhone());
    }
}
