package com.example.afishaandroidapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.example.afishaandroidapp.R;
import com.example.afishaandroidapp.streamActivity;

public class ticketsFragment extends Fragment {
    private Button buttonWatch;

    public ticketsFragment() {
        super(R.layout.ticket_fragment);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ticket_fragment, null);
        buttonWatch = view.findViewById(R.id.buttonWatch);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonWatch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), streamActivity.class);
            startActivity(intent);
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
