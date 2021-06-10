package com.example.thirdexample;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import static android.content.Context.NOTIFICATION_SERVICE;

public class page1Fragment extends Fragment {
    public static boolean state;

    public page1Fragment() {
        super(R.layout.page1_fragment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState != null) {
//            state = savedInstanceState.getBoolean("switchState");
//            getActivity().findViewById(R.id.switch2).setSelected(state);
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page1_fragment, null);
        Switch swtch = view.findViewById(R.id.switch2);

        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(getContext(), MusicService.class);
//                state = isChecked;

                if (isChecked)
                    getActivity().startService(intent);
                else
                    getActivity().stopService(intent);
            }
        });
        return view;
    }

//    @Override
//    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean("switchState", state);
//    }
}
