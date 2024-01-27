package com.learn.spring.service;

import com.learn.spring.enums.UserRelationStatus;
import com.learn.spring.model.User;
import com.learn.spring.model.UserRelation;
import com.learn.spring.repository.UserRelationRepository;
import com.learn.spring.repository.UserRepository;
import com.learn.spring.wrapper.RequestedFriendResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserRelationService {
    private final UserRelationRepository userRelationRepository;
    private final UserRepository userRepository;

    public Boolean addFriend(Long senderUserId, Long receiverUserId){
        List<User> users= userRepository.findAllById(Arrays.asList(senderUserId,receiverUserId));
        if(users.size()<2||checkUserRelationExisted(senderUserId,receiverUserId))
            return false;
        addNewRelation(senderUserId,receiverUserId);
        return true;
    }

    public void addNewRelation(Long senderId, Long receiverId){
        userRelationRepository.save(new UserRelation(senderId,receiverId));
    }

    public boolean updateRelationStatus(Long id, UserRelationStatus userRelationStatus){
        UserRelation userRelation= userRelationRepository.findById(id).orElse(null);
        if(userRelation==null)
            return false;
        userRelation.setUserRelationStatus(userRelationStatus);
        userRelationRepository.save(userRelation);
        return true;
    }

    public boolean checkUserRelationExisted(Long senderId, Long receiverId){
        return userRelationRepository.getUserRelationBySenderUserIdOrReceiverUserId(senderId,receiverId)!=null;
    }

    public List<UserRelation> getUserRelationByReceiverIdAndStatus(Long receiverId, UserRelationStatus status){
        return userRelationRepository.getUserRelationByReceiverIdAndStatus(receiverId,status);
    }

    public List<RequestedFriendResponse> getRequestedFriendResponse(Long receiverId){
        List<UserRelation> listOfUserRelation= getUserRelationByReceiverIdAndStatus(receiverId,UserRelationStatus.PENDING);
        List<Long> senderIds = listOfUserRelation.stream().map(userRelation -> userRelation.getSenderUserId()).collect(Collectors.toList());

        HashMap<Long,User> mapOfProfileName= new HashMap<>();
        userRepository.findAllById(senderIds).stream().forEach(user -> {
            mapOfProfileName.put(user.getId(), user);
        });
        return listOfUserRelation.stream()
                .map(userRelation ->{
                    User user= mapOfProfileName.get(userRelation.getSenderUserId());
                    return new RequestedFriendResponse(userRelation.getId(),
                        user.getId(),
                        user.getProfileName());})
                .collect(Collectors.toList());
    }
}
