package com.example.rentateamtask.model.service.network;

import com.example.rentateamtask.model.pojo.MainClass;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface regresInApi {
    @GET("users")
    Observable<MainClass> getUsers();
}
