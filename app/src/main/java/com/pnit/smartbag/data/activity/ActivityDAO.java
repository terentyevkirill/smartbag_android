package com.pnit.smartbag.data.activity;

import com.pnit.smartbag.Converters;
import com.pnit.smartbag.data.activity.model.Activity;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

@Dao
public interface ActivityDAO {

    @Query("SELECT * FROM activity")
    LiveData<List<Activity>> getAllActivities();

    @Query("SELECT * FROM activity WHERE id in (:userIds)")
    List<Activity> loadAllByUserIds(int[] userIds);

    @Query("SELECT * FROM activity WHERE start_time LIKE :time")
    @TypeConverters({Converters.class})
    List<Activity> findByDate(Date time);

    @Query("SELECT * FROM activity WHERE id LIKE :id")
    List<Activity> findById(String id);

    @Insert
    void insertAll(Activity... activity);

    @Query("DELETE FROM activity WHERE id = :name")
    void delete(String name);
}
