package com.example.chatdb.database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * A list of friends
 */
@Entity(tableName = "friend_list", primaryKeys = {"first_id", "second_id"}, foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "first_id",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "second_id",
                onDelete = ForeignKey.CASCADE
        )})
public class FriendList {
    @ColumnInfo(name = "first_id")
    private long firstId;

    @ColumnInfo(name = "second_id")
    private long secondId;

    public long getFirstId() {
        return firstId;
    }

    public void setFirstId(long firstId) {
        this.firstId = firstId;
    }

    public long getSecondId() {
        return secondId;
    }

    public void setSecondId(long secondId) {
        this.secondId = secondId;
    }
}
