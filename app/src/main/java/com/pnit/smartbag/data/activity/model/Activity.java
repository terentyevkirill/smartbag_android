package com.pnit.smartbag.data.activity.model;

import com.pnit.smartbag.Converters;
import com.pnit.smartbag.data.user.model.User;

import java.sql.Time;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"),
        indices = {@Index(value = {"user_id"})})
public class Activity {

    public static final long DEFAULT_ACTIVITY_ID = 0;

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "start_time")
    @TypeConverters({Converters.class})
    private Date startTime;
    @ColumnInfo(name = "end_time")
    @TypeConverters({Converters.class})
    private Date endTime;
    @ColumnInfo(name = "steps")
    private int steps;
    @ColumnInfo(name = "user_id")
    private long userId;

    public Activity(long id, Date startTime, Date endTime, int steps, long userId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.steps = steps;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
