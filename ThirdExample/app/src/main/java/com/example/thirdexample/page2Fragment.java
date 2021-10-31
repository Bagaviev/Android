package com.example.thirdexample;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Calendar;

public class    page2Fragment extends Fragment {
    Calendar calendar = Calendar.getInstance();
    Button setTime;
    Button cancel;
    Button now;
    TextView textView;

    public page2Fragment() {
        super(R.layout.page2_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page2_fragment, null);

        setTime = view.findViewById(R.id.settime);
        cancel = view.findViewById(R.id.cancel);
        now = view.findViewById(R.id.now);
        textView = view.findViewById(R.id.timepickerText);

        setTime.setOnClickListener(v -> setTime());
        cancel.setOnClickListener(v -> cancelAlarm());
        now.setOnClickListener(v -> notifyManual());
        return view;    // тут обязательно надо возвращать наше рабочее вью, а не super.bla bla
    }

    private void notifyManual() {
        startAlarm(Calendar.getInstance().getTimeInMillis() + 500);
    }

    public void setTime() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                if (calendar.before(Calendar.getInstance())) {
                    textView.setText("Время в прошлом, попробуйте еще раз.");
                    return;
                }

                startAlarm(calendar.getTimeInMillis());     // СТАААРТУЕМ НАТАЛЬЯ МОРСКАЯ ПЕХОТА
                textView.setText(String.format("Timer set on: %d : %d", hourOfDay, minute));
            }
        };

        new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true).show();
    }

    public void startAlarm(long millis) {
        AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, millis, pendingIntent);
    }

    public void cancelAlarm() {
        AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
        alarmMgr.cancel(pendingIntent);
        textView.setText("Alarm canceled");
    }
}
