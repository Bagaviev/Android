package com.example.rentateamtask.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.rentateamtask.pojo.UserData;
import java.util.List;
import io.reactivex.Flowable;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    Flowable<List<UserData>> getAll();

    @Insert
    void insert(UserData users);        // pizdec UserData такой есть уже класс
}
