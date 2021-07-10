package com.example.rentateamtask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.rentateamtask.fragments.AboutFragment;
import com.example.rentateamtask.fragments.UserListFragment;
import com.example.rentateamtask.pojo.MainClass;
import com.example.rentateamtask.pojo.UserData;
import com.example.rentateamtask.retrofit.NetworkService;
import com.example.rentateamtask.room.AppDatabase;
import com.example.rentateamtask.room.UserDao;
import com.example.rentateamtask.utils.ListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Fragment userListFragment = new UserListFragment();
    Fragment aboutFragment = new AboutFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("LOGG", "активити стартовала");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment;
        if (item.getItemId() == R.id.page1)
            selectedFragment = userListFragment;
        else
            selectedFragment = aboutFragment;

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out)
                .replace(R.id.fragmentContainerView, selectedFragment)
                .commit();
        return true;
    };
}