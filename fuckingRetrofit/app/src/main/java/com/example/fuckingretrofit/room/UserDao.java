package com.example.fuckingretrofit.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.fuckingretrofit.pojo.UserData;
import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    Flowable<List<UserData>> getAll();

    @Insert
    void insert(com.example.fuckingretrofit.pojo.UserData users);        // бля pizdec UserData такой есть уже класс
}
