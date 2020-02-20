package com.pnit.smartbag.data;

import android.content.Context;

import com.pnit.smartbag.data.activity.model.Activity;
import com.pnit.smartbag.data.activity.ActivityDAO;
import com.pnit.smartbag.data.user.model.User;
import com.pnit.smartbag.data.user.UserDAO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Activity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
    public abstract ActivityDAO activityDAO();

    public static final String DB_NAME = "smart_bag.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }
    public AppDatabase() {}

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context, AppDatabase.class, DB_NAME
        )
        .fallbackToDestructiveMigration()   // not for production
        .allowMainThreadQueries().build();

    }
}

