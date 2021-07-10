package com.example.fuckingretrofit.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.fuckingretrofit.pojo.UserData;

@Database(entities = {UserData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}