package com.example.fuckingretrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

// todo 3) вмерджить в проект с меню
// todo 4) навести mvp

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.fuckingretrofit.MESSAGE";
    NetworkService netService = NetworkService.getInstance();
    //    AppDatabase dbService = DatabaseService.getInstance(this).getDb();
    List<UserData> userListState = new ArrayList<>();
    UserData selectedUser;
    AppDatabase db;
    UserDao userDao;
    ListAdapter adapter;
    ProgressBar progressBar;
    Button button;
    ListView listView;      // здесь бы мог быть recyclerview, но решил не усложнять
    TextView textViewMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = Room.databaseBuilder(this, AppDatabase.class, "app.db").build();   // страшная ошибка SIGSEGV была
        userDao = db.userDao();                                                               // когда context передавал как di
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        textViewMsg = findViewById(R.id.textViewMsg);
        textViewMsg.setMovementMethod(new ScrollingMovementMethod());
        progressBar.setVisibility(View.INVISIBLE);
        Log.e("LOGG", "активити стартовала");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        setupListView();

        button.setOnClickListener(v -> {
            textViewMsg.setText("");

            if(isNetConnected()) {
                netRequest();
            } else {
                textViewMsg.setText("Нет интернета");
                dbRequest();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedUser = userListState.get(position);
            openUserPage();
            textViewMsg.setText("Выбран: " + selectedUser.getFirstName());
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

                        try {                           // чтобы показать прогрессбар
                            Thread.sleep(1000);
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

                            userListState.add(newUser);     // in memory
                            userDao.insert(newUser);        // persist

                            Log.e("LOGG", newUser.toString());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SQLiteConstraintException)
                            textViewMsg.setText("Данные обновлены");
                        else
                            textViewMsg.setText(e.getMessage());

                        dbRequest();
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.INVISIBLE);
                        listView.post(() -> {
                            dbRequest();
                            adapter.notifyDataSetChanged();
                        });
                        Log.e("LOGG", "запрос получен распарсен и вставлен");
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void dbRequest() {
        Log.e("LOGG", "запрос в бд");
        userDao.getAll()
                .observeOn(Schedulers.io())             // тут можно как то захэндлить исключение, если уметь
                .subscribe(new Consumer<List<UserData>>() {
                    @Override
                    public void accept(List<UserData> userData) throws Exception {      // framework moment...
                        listView.post(() -> {
                            userListState.clear();            // раньше тут писал сразу в адаптер, но пришлось в отдельный список
                            userListState.addAll(userData);   // в памяти писать
                            adapter.notifyDataSetChanged();
                        });
                    }
                });
    }

    private void setupListView() {
        Log.e("LOGG", "адаптер начал работу");
        adapter = new ListAdapter(this, userListState);
        listView.setAdapter(adapter);
        Log.e("LOGG", "адаптер закончил работу");
    }

    @Override
    protected void onDestroy() {
        netService.getJSONApi().getUsers().unsubscribeOn(Schedulers.io());
        db.close();
        db = null;
        userDao = null;
        netService = null;
        adapter = null;
        super.onDestroy();
    }

    public boolean isNetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void openUserPage() {
        Intent intent = new Intent(this, UserPage.class);
        intent.putExtra(EXTRA_MESSAGE, selectedUser);
        startActivity(intent);
    }
}