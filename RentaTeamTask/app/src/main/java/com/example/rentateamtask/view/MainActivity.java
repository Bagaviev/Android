package com.example.rentateamtask.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.rentateamtask.R;
import com.example.rentateamtask.view.fragments.AboutFragment;
import com.example.rentateamtask.view.fragments.UserCardFragment;
import com.example.rentateamtask.view.fragments.UserListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment userListFragment = new UserListFragment();
    private Fragment aboutFragment = new AboutFragment();
    private Fragment userCardFragment = new UserCardFragment();

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
                selectedFragment = userListFragment;
            else
                selectedFragment = aboutFragment;

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in,
                            R.anim.fade_out
                    )
                    .replace(R.id.fragmentContainerView, selectedFragment)
                    .commit();
            return true;
        }
    };
}