package com.pnit.smartbag.database.user;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE id in (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE user_name LIKE :username LIMIT 1")
    User findByName(String username);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
