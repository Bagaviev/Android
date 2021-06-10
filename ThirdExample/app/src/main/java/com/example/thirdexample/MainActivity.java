package com.example.thirdexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {       // делаем BottomNavigationMenu (отдельным .xml компоненты)
    private Fragment fragment1 = new page1Fragment();
    private Fragment fragment2 = new page2Fragment();
    private Fragment fragment3 = new page3Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

//        if (savedInstanceState != null)
//            fragment1 = getSupportFragmentManager().getFragment(savedInstanceState, "page1Fragment");
    }


    public BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            Fragment selectedFragment;
            if (item.getItemId() == R.id.page1)
                selectedFragment = fragment1;
            else if (item.getItemId() == R.id.page2)
                selectedFragment = fragment2;
            else
                selectedFragment = fragment3;

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

//    @Override
//    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        getSupportFragmentManager().putFragment(outState, "page1Fragment", fragment1);
//    }
}