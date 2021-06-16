package com.example.fourthexample.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fourthexample.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolFragment extends Fragment {
    static final int NUM_CORES = Runtime.getRuntime().availableProcessors();
    ExecutorService service;
    ScrollView scrollView;
    TextView textView;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.threadpool_fragment, null);
        scrollView = view.findViewById(R.id.scrollView);
        textView = view.findViewById(R.id.textView4);
        button = view.findViewById(R.id.button3);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = Executors.newFixedThreadPool(NUM_CORES);

        button.setOnClickListener(v -> {
            for (int i = 0; i < NUM_CORES; i++)   // 8 ядер 8 потоков 8 задач, по идее по задаче на ядро (если это так работает)
                service.execute(task);
            service.shutdown();
        });
    }

    public void renderUI(String str) {
        getActivity().runOnUiThread(() -> {
            textView.append(str + "\n");
        });
    }

    Runnable task = () -> {
        try {
            renderUI(Thread.currentThread().getName() + " is working");
            Thread.sleep(3000);
            renderUI(Thread.currentThread().getName() + " stopped");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };
}