package com.example.fuckingretrofit;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Interface {
    @GET("/todos")
    public Call<List<User>> getUsers(@Query("userId") int id);
}
