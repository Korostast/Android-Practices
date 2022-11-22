package com.example.chatdb.database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * A list of friend requests from user with 'userId' to user with 'requestedId'
 */
@Entity(tableName = "request_list", primaryKeys = {"user_id", "requested_id"}, foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "requested_id",
                onDelete = ForeignKey.CASCADE
        )})
public class RequestList {
    @ColumnInfo(name = "user_id")
    private long userId;

    @ColumnInfo(name = "requested_id")
    private long requestedId;

    public RequestList(long userId, long requestedId) {
        this.userId = userId;
        this.requestedId = requestedId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRequestedId() {
        return requestedId;
    }

    public void setRequestedId(long requestedId) {
        this.requestedId = requestedId;
    }
}
