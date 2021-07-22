package com.example.reclinetask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

// https://app.onesignal.com/apps/a5ee2618-e10e-4da3-aa1e-9a2b2a746855#outcomes=os__click__count
// https://documentation.onesignal.com/docs/android-sdk-setup

public class LoadActivity extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "a5ee2618-e10e-4da3-aa1e-9a2b2a746855";
    ProgressBar bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        bar = findViewById(R.id.progressBar2);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);   // Enable verbose OneSignal logging.
        OneSignal.initWithContext(this);                                                // OneSignal Initialization
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        bar.setVisibility(View.VISIBLE);

        if (isOpenedFromPush()) {
            bar.setVisibility(View.GONE);
            startAds();
        } else {
            bar.setVisibility(View.GONE);
            startGame();
        }

        OneSignal.setNotificationOpenedHandler(new OneSignal.OSNotificationOpenedHandler() {
            @Override
            public void notificationOpened(OSNotificationOpenedResult result) {
                saveState(true);
                startAds();
            }
        });
    }

    private void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void startAds() {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    private void saveState(boolean openedFromPush) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("openedFromPush", openedFromPush);
        editor.apply();
    }

    private boolean isOpenedFromPush() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean("openedFromPush", false);
    }
}