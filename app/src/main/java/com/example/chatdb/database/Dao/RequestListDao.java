package com.example.chatdb.database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chatdb.database.Entities.RequestList;
import com.example.chatdb.database.Entities.User;

import java.util.List;

@Dao
public interface RequestListDao {
    @Insert
    void insert(List<RequestList> requests);

    @Query("insert into request_list values(:currentUserId, :id)")
    void sendFriendRequest(long currentUserId, long id);

    @Query("select id, name, phone_number from users U join request_list RL on U.id = RL.user_id " +
            "where :currentUserId = RL.requested_id")
    List<User> getIncomings(long currentUserId);

    @Query("select id, name, phone_number from users U join request_list RL on U.id = RL.requested_id " +
            "where :currentUserId = RL.user_id")
    List<User> getOutgoings(long currentUserId);

    @Query("delete from request_list where user_id = :currentUserId and requested_id = :id")
    void deleteFriendRequest(long currentUserId, long id);
}
