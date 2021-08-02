package com.example.seventhexample.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seventhexample.R;

public class IdeTestFragment extends Fragment {
    Button submit;
    TextView textView;
    EditText editText;

    public IdeTestFragment() {
        super(R.layout.fragment_ide_test);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ide_test, container, false);
        submit = view.findViewById(R.id.button3);
        submit.setOnClickListener(v -> doSmth());
        textView = view.findViewById(R.id.textView3);
        editText = view.findViewById(R.id.editTextText1);
        return view;
    }

    private void doSmth() {
        textView.setText(editText.getText());
    }
}