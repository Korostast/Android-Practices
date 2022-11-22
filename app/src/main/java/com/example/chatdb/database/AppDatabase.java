package com.example.chatdb.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.chatdb.database.Dao.FriendListDao;
import com.example.chatdb.database.Dao.MessageDao;
import com.example.chatdb.database.Dao.RequestListDao;
import com.example.chatdb.database.Dao.UserDao;
import com.example.chatdb.database.Entities.FriendList;
import com.example.chatdb.database.Entities.Message;
import com.example.chatdb.database.Entities.RequestList;
import com.example.chatdb.database.Entities.User;

@Database(entities = {User.class, Message.class, FriendList.class, RequestList.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract MessageDao messageDao();

    public abstract FriendListDao friendListDao();

    public abstract RequestListDao requestListDao();

    private static AppDatabase INSTANCE;

    // We should know who we are
    private Long currentUserId = null;
    private String currentUserName = null;
    private String currentPhone = null;

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCurrentPhone() {
        return currentPhone;
    }

    public void setCurrentPhone(String currentPhone) {
        this.currentPhone = currentPhone;
    }
}
