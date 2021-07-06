package com.example.rentateamtask.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.rentateamtask.R;

public class UserCardFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.usercard_fragment, null);
//        textViewInfo = view.findViewById(R.id.textView1);
//        textViewShow = view.findViewById(R.id.textform);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        save.setOnClickListener((v) -> saveFile());
//        load.setOnClickListener((v) -> loadFile());
        super.onViewCreated(view, savedInstanceState);
    }
}