package com.pnit.smartbag.data;

import android.content.Context;
import android.util.Log;

import com.pnit.smartbag.data.activity.model.Activity;
import com.pnit.smartbag.data.activity.ActivityDAO;
import com.pnit.smartbag.data.user.model.User;
import com.pnit.smartbag.data.user.UserDAO;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Activity.class}, version = 4, exportSchema = false)
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
        .allowMainThreadQueries()
        .addCallback(new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                populateInitialData(context);
            }
        })
        .build();

    }

    private static void populateInitialData(Context context) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AppDatabase db = getInstance(context);
            db.userDao().insert(new User(User.DEFAULT_USER_ID, "", "", 0, 0F, 0F));
            Log.v("DATABASE", "Pre-populated!");
        });

    }

}

