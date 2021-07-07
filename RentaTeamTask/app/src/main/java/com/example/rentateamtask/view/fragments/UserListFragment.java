package com.example.rentateamtask.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.rentateamtask.R;
import com.example.rentateamtask.presenter.Presenter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class UserListFragment extends Fragment {
    Presenter presenter = new Presenter();
    TextView textViewUL;
    Button buttonUL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userlist_fragment, null);
        textViewUL = view.findViewById(R.id.textViewULmain);
        buttonUL = view.findViewById(R.id.buttonUL);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonUL.setOnClickListener((v) -> renderList());
        super.onViewCreated(view, savedInstanceState);
    }

    public void renderList() {
        presenter.makeRequest();
        presenter.getUserList();
        textViewUL.setText(presenter.getUserList().toString());
    }
}
