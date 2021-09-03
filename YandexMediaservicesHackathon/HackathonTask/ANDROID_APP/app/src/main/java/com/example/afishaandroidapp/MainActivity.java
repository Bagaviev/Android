package com.example.afishaandroidapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;
import com.example.afishaandroidapp.fragments.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static List<Fragment> fragmentList;

    static {
        fragmentList = new ArrayList<>();
        fragmentList.add(new afishaFragment());
        fragmentList.add(new ticketsFragment());
        fragmentList.add(new searchFragment());
        fragmentList.add(new profileFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }


    public BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment selectedFragment;
            if (item.getItemId() == R.id.page1)
                selectedFragment = fragmentList.get(0);
            else if (item.getItemId() == R.id.page2)
                selectedFragment = fragmentList.get(1);
            else if (item.getItemId() == R.id.page3)
                selectedFragment = fragmentList.get(2);
            else
                selectedFragment = fragmentList.get(3);

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in,
                            R.anim.fade_out
                    )
                    .replace(R.id.fragmentContainerView3, selectedFragment)
                    .commit();
            return true;
        }
    };
}