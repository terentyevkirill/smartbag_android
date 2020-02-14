package com.pnit.smartbag.database.activity;

import com.pnit.smartbag.Converters;
import com.pnit.smartbag.database.user.User;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity
public class Activity {

    @PrimaryKey
    private long id;
    @ColumnInfo(name = "date")
    @TypeConverters({Converters.class})
    private Date date;
    @ColumnInfo(name = "steps")
    private int steps;
    @ColumnInfo(name = "user_id")
    private long userId;

    public Activity(long id, Date date, int steps, User user) {
        this.id = id;
        this.date = date;
        this.steps = steps;
        this.userId = user.getId();
    }

    public Activity(long id, Date date, int steps, long userId) {
        this.id = id;
        this.date = date;
        this.steps = steps;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
