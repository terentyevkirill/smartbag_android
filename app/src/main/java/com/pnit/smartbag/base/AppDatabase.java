package com.pnit.smartbag.base;

import android.content.Context;

import com.pnit.smartbag.base.user.User;
import com.pnit.smartbag.base.user.UserDAO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDao();

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
        ).allowMainThreadQueries().build();
    }
}

