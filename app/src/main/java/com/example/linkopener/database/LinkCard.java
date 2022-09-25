package com.example.linkopener.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Card entity which is showed in history activity
 */
@Entity
public class LinkCard {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "link")
    public String link;
}
