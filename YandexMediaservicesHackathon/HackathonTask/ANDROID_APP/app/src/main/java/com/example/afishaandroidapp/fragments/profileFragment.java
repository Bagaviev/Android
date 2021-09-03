package com.example.afishaandroidapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.afishaandroidapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class profileFragment extends Fragment {
    FloatingActionButton floatingActionButton;

    public profileFragment() {
        super(R.layout.profile_fragment);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, null);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("tv.twitch.android.app");
                if (launchIntent != null) {
                    launchIntent.setData(Uri.parse("twitch://broadcast?"));
                    startActivity(launchIntent);
                } else {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Чтобы запустить трансляцию, нужно скачать Twitch", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
