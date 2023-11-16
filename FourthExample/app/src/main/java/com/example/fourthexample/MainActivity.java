package com.example.fourthexample;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.fourthexample.fragments.AsyncTaskFragment;
import com.example.fourthexample.fragments.HandlerFragment;
import com.example.fourthexample.fragments.ImplicitIntentFragment;
import com.example.fourthexample.fragments.ThreadPoolFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TestingEmptyCons instance = new TestingEmptyCons();
        instance.getAbg();
        TestingEmptyCons2 instance2 = new TestingEmptyCons2();
        instance2.getName();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {    // получили интент от события share
            if ("text/plain".equals(type) || "text/html".equals(type))
                viewPager.setCurrentItem(3);    // открыли 3ий фрагмент, вкладку
        }
    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentPagerAdapter.addFragment(new AsyncTaskFragment(), "AsyncTask");
        fragmentPagerAdapter.addFragment(new HandlerFragment(), "Handler");
        fragmentPagerAdapter.addFragment(new ThreadPoolFragment(), "ThreadPoolExecutor");
        fragmentPagerAdapter.addFragment(new ImplicitIntentFragment(), "Implicit Intent");
        viewPager.setAdapter(fragmentPagerAdapter);
    }
}