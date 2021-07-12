package com.example.rentateamtask.model.service.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.rentateamtask.model.pojo.UserData;

@Database(entities = {UserData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}