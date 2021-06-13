package com.example.thirdexample;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class page3Fragment extends Fragment {
    Constraints constraints;
    WorkRequest myWorkRequest;
    String task;
    Spinner spinner;
    Switch zwitch;
    TextView textview;
    Button button;

    public page3Fragment() {
        super(R.layout.page3_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(this.getClass().getSimpleName(), "Started render");
        View view = inflater.inflate(R.layout.page3_fragment, null);
        spinner = view.findViewById(R.id.jobSpin);
        spinner.setOnItemSelectedListener(sListener);

        zwitch = view.findViewById(R.id.jobSwtch);
        zwitch.setOnCheckedChangeListener(zListener);

        textview = view.findViewById(R.id.statusView);

        button = view.findViewById(R.id.statusBtn);
        button.setOnClickListener(bListener);

        Log.d(this.getClass().getSimpleName(), "Stopped render");
        return view;
    }

    public void initWorker() {
        switch (task) {
            case "1":
                myWorkRequest = new PeriodicWorkRequest.Builder(BackgroundWorker.class,
                        60, TimeUnit.MINUTES, 55, TimeUnit.MINUTES)
                        .build();
                break;
            case "2":
                constraints = new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.UNMETERED)
                        .build();      // на reboot хотел, но этого из коробки нет, только intent подобный видел

                myWorkRequest = new OneTimeWorkRequest.Builder(BackgroundWorker.class)
                        .setConstraints(constraints)
                        .build();
                break;
            default: {
                constraints = new Constraints.Builder()
                        .setRequiresCharging(true)
                        .build();

                myWorkRequest = new OneTimeWorkRequest.Builder(BackgroundWorker.class)
                        .setConstraints(constraints)
                        .build();
            }
        }

        WorkManager.getInstance(getContext()).enqueue(myWorkRequest);
    }

    public void stopWorker(WorkRequest workRequest) {
        if (workRequest != null)
            WorkManager.getInstance(getContext()).cancelWorkById(workRequest.getId());
    }

    public void statusWorker() {
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(myWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                textview.setText(workInfo.getState().toString());
            }
        });
    }


    AdapterView.OnItemSelectedListener sListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 1:
                    task = "2";
                    Log.d(this.getClass().getSimpleName(), "Second item selected");
                    break;
                case 2:
                    task = "3";
                    Log.d(this.getClass().getSimpleName(), "Third item selected");
                    break;
                default:
                    task = "1";
                    Log.d(this.getClass().getSimpleName(), "First item selected");
            }
            stopWorker(myWorkRequest);
            zwitch.setPressed(false);   // todo не работает почему не выключается
            textview.setText("");
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    CompoundButton.OnCheckedChangeListener zListener = (buttonView, isChecked) -> {
        if (isChecked)
            initWorker();
        else {
            stopWorker(myWorkRequest);
            textview.setText("");
        }
    };

    View.OnClickListener bListener = (v -> {
       statusWorker();
    });
}
