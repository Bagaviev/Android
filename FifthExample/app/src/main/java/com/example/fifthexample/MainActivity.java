package com.example.fifthexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.fifthexample.fragments.DBFragment;
import com.example.fifthexample.fragments.DialogFragment;
import com.example.fifthexample.fragments.FileFragment;
import com.example.fifthexample.fragments.PermissionFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentPagerAdapter.addFragment(new FileFragment(), "File");
        fragmentPagerAdapter.addFragment(new DialogFragment(), "Dialog");
        fragmentPagerAdapter.addFragment(new PermissionFragment(), "Dangerous permission");
        fragmentPagerAdapter.addFragment(new DBFragment(), "DB Sqlite");
        viewPager.setAdapter(fragmentPagerAdapter);
    }
}