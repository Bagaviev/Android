package com.example.firstexample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestActivity extends AppCompatActivity {
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        textView3 = findViewById(R.id.textView3);
        new Thread(new HttpDownloaderThread()).start();
    }

    class HttpDownloaderThread implements Runnable {   // отдельный поток
        @Override
        public void run() {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://ya.ru")
                        .build();

                Response response = okHttpClient.newCall(request).execute();
                String result = response.body().string();

                Thread.sleep(3000); // imagine that this long processs
                textView3.post(() -> textView3.setText(result));       // вернули резалт в main UI
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
