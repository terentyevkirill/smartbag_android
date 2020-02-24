package com.pnit.smartbag.data.activity;

import com.pnit.smartbag.Converters;
import com.pnit.smartbag.data.activity.model.Activity;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

@Dao
public interface ActivityDAO {

    @Query("SELECT * FROM activity")
    LiveData<List<Activity>> getAllActivities();

    @Query("SELECT SUM(steps) from Activity WHERE start_time = :date")
    LiveData<Integer> getStepsForDay(long date);

    @Query("SELECT SUM(steps) from Activity WHERE start_time = :date")
    int getStepsForDayInt(long date);

    @Query("SELECT * FROM activity WHERE id in (:userIds)")
    List<Activity> loadAllByUserIds(int[] userIds);

    @Query("SELECT * FROM activity WHERE start_time LIKE :time")
    @TypeConverters({Converters.class})
    List<Activity> findByDate(Date time);

    @Query("SELECT * FROM activity WHERE id LIKE :id")
    Activity findById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Activity activity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Activity... activity);

    @Query("DELETE FROM activity WHERE id = :id")
    void delete(long id);
}
