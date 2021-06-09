package com.example.thirdexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {       // делаем BottomNavigationMenu (отдельным .xml компоненты)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setupBottomNavigation(View view) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bnv);
        MenuItem item1 = findViewById(R.id.page1);
        MenuItem item2 = findViewById(R.id.page2);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                if (item.getItemId() == R.id.page1) {

                } else {

                }
                return false;
            }
        });
    }

    public void runPlayer(View v) {
        Intent intent = new Intent(this, MusicService.class);

        Switch swtch = findViewById(R.id.switch1);
        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    startService(intent);
                else
                    stopService(intent);
            }
        });
    }
}