package com.example.rentateamtask.presenter;

import com.example.rentateamtask.model.NetworkService;
import com.example.rentateamtask.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter {
    static final NetworkService networkService = NetworkService.getInstance();
    static List<User> users = new ArrayList<>();

    /*
    public void doRequest() {
        try {
            Response<List<User>> response = networkService.getJSONApi().getUsers(1).execute();  // с pagination хз как быть - в цикле проверять уже считанное и сверять с total полем в json...
            users.addAll(response.body());
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
    */

    public void makeRequest() {
        Call<List<User>> call = networkService.getJSONApi().getUsers(1);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful())
                    users.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public List<User> getUserList() {
        return users;
    }
}
