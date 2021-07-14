package com.example.rentateamtask.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import com.example.rentateamtask.Contract;
import com.example.rentateamtask.model.pojo.MainClass;
import com.example.rentateamtask.model.pojo.UserData;
import com.example.rentateamtask.model.service.db.AppDatabase;
import com.example.rentateamtask.model.service.db.UserDao;
import com.example.rentateamtask.model.service.network.regresInApi;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model implements Contract.Model {
    private static final String BASE_URL = "https://reqres.in/api/";
    private static FragmentActivity activityContext;
    private static Model model;
    private Contract.Presenter presenter;
    private regresInApi net;
    private UserDao db;

    private Model() {
        db = Room.databaseBuilder(activityContext, AppDatabase.class, "app.db").build().userDao();
        net = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(regresInApi.class);
    }

    public static Model getInstance(FragmentActivity activity) {
        if (model == null) {
            activityContext = activity;
            model = new Model();
        }
        return model;
    }

    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void loadData() {
        if (isNetConnected()) {
            netRequest();
        } else {
            presenter.sendMessage("Нет интернета");
            dbRequest();
        }
    }

    public boolean isNetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activityContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void netRequest() {
        net.getUsers()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MainClass>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        presenter.enableProgress();
                    }

                    @Override
                    public void onNext(MainClass mainClass) {
                        List<UserData> list = mainClass.getData();

                        for (int i = 0; i < list.size(); i++) {
                            UserData tmp = list.get(i);
                            UserData newUser = new UserData(tmp.getId(),
                                    tmp.getEmail(),
                                    tmp.getFirstName(),
                                    tmp.getLastName(),
                                    tmp.getAvatar());

                            presenter.getUserListState().add(newUser);
                            db.insert(newUser);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SQLiteConstraintException)
                            presenter.sendMessage("Данные обновлены");
                        else
                            presenter.sendMessage(e.getMessage());

                        dbRequest();
                        presenter.disableProgress();
                    }

                    @Override
                    public void onComplete() {
                        presenter.disableProgress();
                        dbRequest();
                        activityContext.runOnUiThread(() -> presenter.getAdapter().notifyDataSetChanged());
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void dbRequest() {
        db.getAll()
                .subscribeOn(Schedulers.io())
                .subscribe(userData -> {
                    presenter.getUserListState().clear();
                    presenter.getUserListState().addAll(userData);
                    activityContext.runOnUiThread(() -> presenter.getAdapter().notifyDataSetChanged());
                });
    }
}