package com.learn.spring.repository;

import com.learn.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    final String getFriendListByUserId="SELECT * FROM user " +
            "WHERE id IN(  SELECT receiver_user_id as friend_id FROM user_relation " +
            "              WHERE user_relation_status = 1 AND receiver_user_id != :uId AND id IN(SELECT id FROM user_relation WHERE receiver_user_id= :uId OR sender_user_id= :uId) " +
            "              UNION " +
            "              SELECT sender_user_id FROM user_relation " +
            "              WHERE user_relation_status = 1 AND sender_user_id != :uId AND id IN(SELECT id FROM user_relation WHERE receiver_user_id= :uId OR sender_user_id= :uId)) ";

    public User findByEmailAndPassword(String email, String password);

    @Query(value = getFriendListByUserId, nativeQuery = true)
    public List<User> getByFriendList(@Param("uId") Long userId);
}
