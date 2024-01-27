package com.learn.spring.controller;

import com.learn.spring.enums.UserRelationStatus;
import com.learn.spring.service.UserRelationService;
import com.learn.spring.wrapper.RequestedFriendResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-relation")
@AllArgsConstructor
public class UserRelationController {
    private final UserRelationService userRelationService;

    @PostMapping("/add-friend/{senderUserId}/{receiverUserId}")
    public ResponseEntity<String> addFriend(@RequestParam("senderUserId")Long senderUserId, @RequestParam("receiverUserId") Long receiverUserId){
        if(senderUserId==receiverUserId)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both Ids are identical");
        if(!userRelationService.addFriend(senderUserId,receiverUserId))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        return ResponseEntity.ok("Succeed");
    }

    @PutMapping("/update-status/{id}/{status}")
    public ResponseEntity<Boolean> updateFriendStatus(@PathVariable("id")Long id,@PathVariable("status")Integer status){
        if(!userRelationService.updateRelationStatus(id, UserRelationStatus.values()[status]))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/get-requested-list/{userId}")
    public ResponseEntity<List<RequestedFriendResponse>> getRequestedList(@PathVariable("userId")Long userId){
        List<RequestedFriendResponse> responses= userRelationService.getRequestedFriendResponse(userId);
        if(responses==null||responses.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        return ResponseEntity.ok(responses);
    }
}
