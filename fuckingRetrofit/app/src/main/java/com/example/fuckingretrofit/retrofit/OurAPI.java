package com.example.fuckingretrofit.retrofit;

import com.example.fuckingretrofit.pojo.MainClass;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface OurAPI {
/*
    @GET("/todos")
    Call<List<User>> getUsers();

    @GET("users")
    Call<MainClass> getUsers();
*/

    @GET("users")
    Observable<MainClass> getUsers();
}
