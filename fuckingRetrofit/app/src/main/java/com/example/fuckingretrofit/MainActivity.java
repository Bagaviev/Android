package com.example.fuckingretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    NetworkService networkService = NetworkService.getInstance();
    Button button;
    TextView textView;
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            request();
        });
        super.onStart();
    }

    private void request() {
        Call<List<User>> callList = networkService.getJSONApi().getUsers(3);
        callList.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {  // ого работает из UI
                if(response.isSuccessful() & response != null) {
                    List<User> user = response.body();
                    userList.addAll(user);      // сначала по тупому руками поля вытаскивал, но не нужно retrofit это делает за нас
                    textView.setText(Arrays.toString(userList.toArray()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });
    }
}