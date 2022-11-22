package com.example.chatdb.database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a message in a chat
 */
@Entity(tableName = "messages")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "sender_id")
    private long senderId;

    @ColumnInfo(name = "receiver_id")
    private long receiverId;

    private String content;

    public Message(long senderId, long receiverId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
