package com.example.fourthexample;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
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