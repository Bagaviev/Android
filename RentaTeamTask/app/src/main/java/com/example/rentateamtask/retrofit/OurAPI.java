package com.example.rentateamtask.retrofit;

import com.example.rentateamtask.pojo.MainClass;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface OurAPI {
    @GET("users")
    Observable<MainClass> getUsers();
}
