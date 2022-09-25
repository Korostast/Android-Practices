package com.example.linkopener.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Database instance
 */
@Database(entities = {LinkCard.class}, version = 2, autoMigrations = {@AutoMigration(from = 1, to = 2)})
public abstract class AppDatabase extends RoomDatabase {
    public abstract LinkCardDao linkCardDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
