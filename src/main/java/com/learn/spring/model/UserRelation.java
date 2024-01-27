package com.learn.spring.model;

import com.learn.spring.enums.UserRelationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_relation")
@NoArgsConstructor
@Setter
@Getter
public class UserRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_user_id")
    private Long senderUserId;
    @Column(name = "receiver_user_id")
    private Long receiverUserId;

    private UserRelationStatus userRelationStatus=UserRelationStatus.PENDING;

    public UserRelation(Long sUId, Long rUId){
        senderUserId=sUId;
        receiverUserId=rUId;
    }
}
