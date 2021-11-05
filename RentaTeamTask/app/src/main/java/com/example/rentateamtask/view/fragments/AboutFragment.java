package com.example.rentateamtask.view.fragments;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.example.rentateamtask.R;

public class AboutFragment extends Fragment {
    TextView textViewAF;
    Button buttonAF;
    MediaPlayer musicService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, null);
        textViewAF = view.findViewById(R.id.aboutTW);
        buttonAF = view.findViewById(R.id.buttonAF);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonAF.setOnClickListener(v -> bomb());
        super.onViewCreated(view, savedInstanceState);
    }

    public void bomb() {
        musicService = MediaPlayer.create(getContext(), R.raw.music);
        musicService.start();
        musicService.setLooping(true);
        requireView().setBackgroundColor(Color.parseColor("#ff3333"));

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            throw new StackOverflowError();
        }, 7000);
    }
}
