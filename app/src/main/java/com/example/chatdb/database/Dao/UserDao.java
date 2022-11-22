package com.example.chatdb.database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.chatdb.database.Entities.User;

import java.util.List;
import java.util.Set;

@Dao
public interface UserDao {
    @Insert
    long insert(User user);

    @Query("select * from users")
    List<User> getAll();

    @Query("delete from users where id = :userId")
    void deleteUserById(long userId);

    @Query("select * from users where :id <> id")
    List<User> getAllUsersExceptSelf(long id);

    @Query("select * from users where lower(name) = lower(:name)")
    List<User> findUsersByName(String name);

    @Query("select * from users where lower(name) like '%' || :pattern || '%' and :id <> id")
    List<User> findUsersByPatternExceptSelf(long id, String pattern);

    @Query("select * from users where phone_number = :number")
    List<User> findUsersByPhoneNumber(String number);

    @Query("update users set name = :newLogin where id = :id")
    void updateUsername(long id, String newLogin);

    @Query("select id, name, phone_number from users where phone_number in(:numbers)")
    List<User> findByPhoneNumbers(Set<String> numbers);
}
