package com.example.rentateamtask.model;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface reqresApi {
    @GET("/users")
    Call<List<User>> getUsers(@Query("page") int page);
    // https://reqres.in/api/users?page=1
}
