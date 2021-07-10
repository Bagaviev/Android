package com.example.rentateamtask.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.rentateamtask.R;
import com.example.rentateamtask.UserPage;
import com.example.rentateamtask.pojo.MainClass;
import com.example.rentateamtask.pojo.UserData;
import com.example.rentateamtask.retrofit.NetworkService;
import com.example.rentateamtask.room.AppDatabase;
import com.example.rentateamtask.room.UserDao;
import com.example.rentateamtask.utils.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserListFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "com.example.rentateamtask.MESSAGE";
    NetworkService netService = NetworkService.getInstance();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userlist_fragment, null);
        Log.e("LOGG", "фрагмент стартовал");
        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "app.db").build();   // страшная ошибка SIGSEGV была
        userDao = db.userDao();                                                               // когда context передавал как di
        listView = view.findViewById(R.id.listview);
        button = view.findViewById(R.id.button);
        progressBar = view.findViewById(R.id.progressBar);
        textViewMsg = view.findViewById(R.id.textViewMsg);
        textViewMsg.setMovementMethod(new ScrollingMovementMethod());
        progressBar.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupListView();
        button.setOnClickListener(v -> {
            textViewMsg.setText("");

            if (isNetConnected()) {
                netRequest();
            } else {
                textViewMsg.setText("Нет интернета");
                dbRequest();
            }
        });

        listView.setOnItemClickListener((parent, viewL, position, id) -> {
            selectedUser = userListState.get(position);
            openUserPage();
            textViewMsg.setText("Выбран: " + selectedUser.getFirstName());
        });
        super.onViewCreated(view, savedInstanceState);
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
                .observeOn(Schedulers.io())               // тут можно как то захэндлить исключение, если уметь
                .subscribe(userData -> {                  // framework moment...
                    listView.post(() -> {
                        userListState.clear();            // раньше тут писал сразу в адаптер, но пришлось в отдельный список
                        userListState.addAll(userData);   // в памяти писать
                        adapter.notifyDataSetChanged();
                    });
                });
    }

    private void setupListView() {
        Log.e("LOGG", "адаптер начал работу");
        adapter = new ListAdapter(getActivity(), userListState);
        listView.setAdapter(adapter);
        Log.e("LOGG", "адаптер закончил работу");
    }

    public boolean isNetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void openUserPage() {
        Intent intent = new Intent(getActivity(), UserPage.class);
        intent.putExtra(EXTRA_MESSAGE, selectedUser);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("LOGG", "фрагмент погиб");
//        netService.getJSONApi().getUsers().unsubscribeOn(Schedulers.io());
//        db.close();
//        db = null;
//        userDao = null;
//        netService = null;
//        adapter = null;
        super.onDestroy();
    }
}
