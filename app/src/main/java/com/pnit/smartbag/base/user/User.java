package com.pnit.smartbag.base.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private long id;
    @ColumnInfo(name = "user_name")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "is_registrated")
    private boolean isRegistrated;
    @ColumnInfo(name = "daily_goal")
    private int dailyGoal;
    @ColumnInfo(name = "weight")
    private float weight;
    @ColumnInfo(name = "height")
    private float height;

    public User(long id, String username, String password, boolean isRegistrated, int dailyGoal, float weight, float height) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isRegistrated = isRegistrated;
        this.dailyGoal = dailyGoal;
        this.weight = weight;
        this.height = height;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRegistrated() {
        return isRegistrated;
    }

    public void setRegistrated(boolean registrated) {
        isRegistrated = registrated;
    }

    public int getDailyGoal() {
        return dailyGoal;
    }

    public void setDailyGoal(int dailyGoal) {
        this.dailyGoal = dailyGoal;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weigth) {
        this.weight = weigth;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}