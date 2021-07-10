package com.example.rentateamtask;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.rentateamtask.fragments.AboutFragment;
import com.example.rentateamtask.fragments.UserListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    @Override
    protected void onDestroy() {
        Log.e("LOGG", "активити погибла");
        super.onDestroy();
    }
}