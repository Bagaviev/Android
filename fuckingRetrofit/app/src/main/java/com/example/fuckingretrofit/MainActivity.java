package com.example.fuckingretrofit;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.fuckingretrofit.pojo.MainClass;
import com.example.fuckingretrofit.pojo.UserData;
import com.example.fuckingretrofit.retrofit.NetworkService;
import com.example.fuckingretrofit.room.AppDatabase;
import com.example.fuckingretrofit.room.UserDao;
import com.example.fuckingretrofit.utils.ListAdapter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*
public class MainActivity extends AppCompatActivity {       // [Простой json]
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

public class MainActivity extends AppCompatActivity {       // [Сложный json]
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

public class MainActivity extends AppCompatActivity {       // [c RxJava]
    NetworkService networkService = NetworkService.getInstance();
    Button button;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        listView = findViewById(R.id.listview);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            request();
        });
        super.onStart();
    }

    private void request() {
        networkService.getJSONApi().getUsers()          // мало понятно, но работает
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<MainClass>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(MainClass mainClass) {       // как и retrofit async request (и okhttp) сами все делают за нас в
                String userContent = "";                    // бекграунд треде, при этом callback отрабатывает на UI треде.
                List<UserData> list = mainClass.getData();  // те post и handler не нужны, все удобно

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
*/

public class MainActivity extends AppCompatActivity {       // с listView
    NetworkService netService = NetworkService.getInstance();
//    AppDatabase dbService = DatabaseService.getInstance(this).getDb();
    AppDatabase db;
    UserDao userDao;
    ListAdapter adapter;
    ProgressBar progressBar;
    Button button;
    ListView listView;
    TextView textViewMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         db = Room.databaseBuilder(this, AppDatabase.class, "app.db")   // ошибка SIGEGV была когда это di было
                .allowMainThreadQueries()           // когда context передавал
                .build();
//        userDao = dbService.userDao();
        userDao = db.userDao();
//        dbService.connect();
//        dbService.create();
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        textViewMsg = findViewById(R.id.textViewMsg);
        textViewMsg.setMovementMethod(new ScrollingMovementMethod());
        progressBar.setVisibility(View.INVISIBLE);
        setupListView();
        Log.e("LOGG", "активити стартовала");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        button.setOnClickListener(v -> {
            textViewMsg.setText("");
            netRequest();
        });
        super.onStart();
    }

    private void netRequest() {
        Log.e("LOGG", "запрос отправлен");
        netService.getJSONApi().getUsers()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MainClass>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(MainClass mainClass) {
                        Log.e("LOGG", "запрос получен");
                        List<UserData> list = mainClass.getData();

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < list.size(); i++) {
                            UserData tmp = list.get(i);
                            UserData newUser = new UserData(tmp.getId(),
                                    tmp.getEmail(),
                                    tmp.getFirstName(),
                                    tmp.getLastName(),
                                    tmp.getAvatar());

                            userDao.insert(newUser);
                            Log.e("LOGG", newUser.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SQLiteConstraintException)
                            textViewMsg.setText("Данные обновлены");
                        else if (e instanceof UnknownHostException)
                            textViewMsg.setText("Нет интернета");
                        else
                            textViewMsg.setText(e.getMessage());
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.INVISIBLE);
                        listView.post(() -> adapter.notifyDataSetChanged());
                        Log.e("LOGG", "запрос получен распарсен и вставлен");
                    }
                });
    }

    private void dbRequest() {
        Log.e("LOGG", "запрос в бд");
        userDao.getAll()
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<UserData>>() {
                               @Override
                               public void accept(List<UserData> userData) throws Exception {
                                   adapter.addAll(userData);
                                   listView.post(() -> adapter.notifyDataSetChanged());
                               }
                           }

                );
    }

    private void setupListView() {
        Log.e("LOGG", "адаптер начал работу");
        adapter = new ListAdapter(this, new ArrayList<UserData>());
        Log.e("LOGG", userDao.getAll().toString());
        listView.setAdapter(adapter);
        Log.e("LOGG", "адаптер закончил работу");
    }

    @Override
    protected void onDestroy() {
        netService.getJSONApi().getUsers().unsubscribeOn(Schedulers.io());
        db.close();
        super.onDestroy();
    }
}