package com.learn.spring.controller;

import com.learn.spring.service.UserService;
import com.learn.spring.wrapper.UserRequest;
import com.learn.spring.wrapper.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody @Valid UserRequest userRequest){
        userService.signUp(userRequest);
    }

    @PutMapping("/edit-profile/{id}")
    public ResponseEntity<Boolean> editProfile(@PathVariable(required = true) Long id, @RequestBody UserRequest userRequest){
        Boolean result= userService.editUser(id,userRequest);
        if(!result)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest){
        UserResponse response= userService.login(userRequest);
        if(response==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserResponse());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserResponse> profile(@PathVariable("id")Long id){
        UserResponse response = userService.getById(id);
        if(response==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserResponse());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/friend-list/{id}")
    public ResponseEntity<List<UserResponse>> getFriendList(@PathVariable("id")Long id){
        List<UserResponse> responses= userService.getUserFriendList(id);
        if(responses==null||responses.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        return ResponseEntity.ok(responses);
    }
}
