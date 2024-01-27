package com.learn.spring.repository;

import com.learn.spring.enums.UserRelationStatus;
import com.learn.spring.model.UserRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRelationRepository extends JpaRepository<UserRelation, Long> {
     final  String queryGetBySUIdOrRUId= "SELECT * FROM user_relation " +
            "WHERE (receiver_user_id= :sUId AND sender_user_id= :rUId) " +
            "OR (receiver_user_id= :rUId AND sender_user_id= :sUId) ";

     final String queryGetByReceiverIdAndStatus= "SELECT * FROM user_relation " +
             "WHERE receiver_user_id= :rUId AND user_relation_status= :status ";

    @Query(value = queryGetBySUIdOrRUId, nativeQuery = true)
    UserRelation getUserRelationBySenderUserIdOrReceiverUserId(@Param("sUId") Long senderUserId, @Param("rUId") Long receiverUserId);

    @Query(value = queryGetByReceiverIdAndStatus, nativeQuery = true)
    List<UserRelation> getUserRelationByReceiverIdAndStatus(@Param("rUId")Long receiverUserId, @Param("status")UserRelationStatus status);
}
