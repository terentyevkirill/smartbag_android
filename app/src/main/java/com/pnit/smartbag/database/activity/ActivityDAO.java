package com.pnit.smartbag.database.activity;

import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ActivityDAO {

    @Query("SELECT * FROM activity")
    List<Activity> getAllUsers();

    @Query("SELECT * FROM activity WHERE id in (:userIds)")
    List<Activity> loadAllByUserIds(int[] userIds);

    @Query("SELECT * FROM activity WHERE date LIKE :date LIMIT 1")
    Activity findByDate(Date date);

    @Insert
    void insertAll(Activity... activity);

    @Delete
    void delete(Activity activity);
}
