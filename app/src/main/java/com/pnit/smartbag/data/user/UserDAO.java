package com.pnit.smartbag.data.user;

import com.pnit.smartbag.data.user.model.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE id in (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE id IS 0")
    List<User> loadUserWithoutRegistration();

    @Query("SELECT * FROM user WHERE user_name LIKE :username")
    User findByName(String username);

    @Insert
    void insert(User user);

    @Insert
    void insertAll(User... users);

    @Query("DELETE FROM user WHERE user_name = :name")
    void delete(String name);
}
