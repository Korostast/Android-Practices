package com.example.chatdb.database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chatdb.database.Entities.Message;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void sendMessage(Message message);

    @Query("select * from messages " +
            "where sender_id = :currentUserId and receiver_id = :userId or sender_id = :userId and receiver_id = :currentUserId")
    List<Message> getMessagesWithUser(long currentUserId, long userId);
}
