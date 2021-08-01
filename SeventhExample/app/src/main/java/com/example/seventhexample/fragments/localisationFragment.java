package com.example.seventhexample.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.seventhexample.R;

public class localisationFragment extends Fragment {
    Button button;

    public localisationFragment() {
        super(R.layout.fragment_localisation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localisation, container, false);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(v -> openLocales());
        return view;
    }

    public void openLocales() {
         startActivity(new Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS));
    }
}