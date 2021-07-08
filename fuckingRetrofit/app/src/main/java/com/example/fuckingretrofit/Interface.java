package com.example.fuckingretrofit;

import com.example.fuckingretrofit.pojo.json.MainClass;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Interface {
/*
    @GET("/todos")
    Call<List<User>> getUsers();

    @GET("users")
    Call<MainClass> getUsers();
*/

    @GET("users")
    Observable<MainClass> getUsers();
}
