package com.example.chatdb.database.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.chatdb.database.Entities.User;

import java.util.List;

@Dao
public interface FriendListDao {
    @Query("select U.id, U.name, U.phone_number " +
            "from friend_list FL " +
            "join users U on U.id = FL.first_id " +
            "where :currentUserId = FL.second_id " +
            "union " +
            "select U.id, U.name, U.phone_number " +
            "from friend_list FL " +
            "join users U on U.id = FL.second_id " +
            "where :currentUserId = FL.first_id")
    List<User> getFriends(long currentUserId);

    @Query("insert into friend_list values(:currentUserId, :id)")
    void addFriend(long currentUserId, long id);

    @Query("delete from friend_list " +
            "where first_id = :currentUserId and second_id = :id or second_id = :currentUserId and first_id = :id")
    void deleteFriend(long currentUserId, long id);
}
