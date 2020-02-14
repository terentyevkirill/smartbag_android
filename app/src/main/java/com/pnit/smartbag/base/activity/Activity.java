package com.pnit.smartbag.base.activity;

import com.pnit.smartbag.base.user.User;

import java.util.Date;

public class Activity {

    private long id;
    private Date date;
    private int steps;
    private User user;

    public Activity(long id, Date date, int steps, User user) {
        this.id = id;
        this.date = date;
        this.steps = steps;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
