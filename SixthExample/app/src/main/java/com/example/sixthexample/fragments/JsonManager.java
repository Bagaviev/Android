package com.example.sixthexample.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sixthexample.R;

public class JsonManager extends Fragment {
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.json_manager_fragment, null);
        textView = view.findViewById(R.id.tv_1_JM);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }
}
