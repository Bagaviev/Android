package com.example.fuckingretrofit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fuckingretrofit.pojo.json.MainClass;
import com.example.fuckingretrofit.pojo.json.UserData;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Callback;

/*
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
        Call<List<User>> callList = networkService.getJSONApi().getUsers();     // с pagination хз как быть - в цикле проверять
        callList.enqueue(new Callback<List<User>>() {                           // уже считанное и сверять с total полем в json...
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {  // ого работает из UI
                if(response.isSuccessful() & response != null) {
                    List<User> user = response.body();
                    userList.addAll(user);      // сначала по тупому руками поля вытаскивал, но не нужно retrofit это делает за нас
                    textView.setText(Arrays.toString(userList.toArray()));      // причем можно не один, а список объектов за раз
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {     // походу сложный объект сериализовать надо как есть
            }                                                               // не пытаться вычленить отдельные поля
        });
    }
}

public class MainActivity extends AppCompatActivity {
    NetworkService networkService = NetworkService.getInstance();
    Button button;
    TextView textView;
    MainClass json;

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
        Call<MainClass> callList = networkService.getJSONApi().getUsers();
        callList.enqueue(new Callback<MainClass>() {
            @Override
            public void onResponse(Call<MainClass> call, Response<MainClass> response) {    // в общем это запрос по retrofit'овски
                if (!response.isSuccessful()){
                    textView.setText("Code: " + response.code());
                    return;
                }
                if (response.body().getData() != null){
                    String userContent = "";
                    List<UserData> list = response.body().getData();  // тут магия происходит - из json получается составной объект

                    for (int i = 0; i < list.size(); i++) {             // внутри у этого жирного объекта нужный список полей
                        userContent += list.get(i).getId() + "\n";      // тут берем подмножество атрибутов и по нему итерируемся
                        userContent += list.get(i).getEmail() + "\n";
                        userContent += list.get(i).getFirstName() + "\n";
                        userContent += list.get(i).getLastName() + "\n";
                        userContent += list.get(i).getAvatar() + "\n\n";
                    }
                    textView.setText(userContent);
                }
            }

            @Override
            public void onFailure(Call<MainClass> call, Throwable t) {
                textView.setText("Failure: " + t);
            }
        });
    }
}
 */

public class MainActivity extends AppCompatActivity {       // ok теперь с rxjava2
    NetworkService networkService = NetworkService.getInstance();
    Button button;
    TextView textView;

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

    private void request() {        // а это запрос по rxjava'овски
        networkService.getJSONApi().getUsers()          // мало понятно, но работает
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<MainClass>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(MainClass mainClass) {
                String userContent = "";
                List<UserData> list = mainClass.getData();

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < list.size(); i++) {
                    userContent += list.get(i).getId() + "\n";
                    userContent += list.get(i).getEmail() + "\n";
                    userContent += list.get(i).getFirstName() + "\n";
                    userContent += list.get(i).getLastName() + "\n";
                    userContent += list.get(i).getAvatar() + "\n\n";
                }
                textView.setText(userContent);
            }

            @Override
            public void onError(Throwable e) {
                textView.setText(e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
