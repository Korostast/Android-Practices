package com.example.linkopener.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * SQL Queries class
 */
@Dao
public interface LinkCardDao {
    @Query("SELECT * FROM linkcard")
    List<LinkCard> getAll();

    @Insert
    void insertAll(LinkCard... linkCards);

    @Delete
    void delete(LinkCard card);

    @Query("DELETE FROM linkcard")
    void deleteAll();
}
