package com.example.fourthexample.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fourthexample.R;

public class AsyncTaskFragment extends Fragment {
    TextView textView;
    ProgressBar progressBar;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.asynctask_fragment, null);
        textView = view.findViewById(R.id.textView1);
        progressBar = view.findViewById(R.id.progressBar1);
        button = view.findViewById(R.id.button);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button.setOnClickListener(v -> {
            AsyncTask task = new sleepTask();
            task.execute();
        });
        progressBar.setMax(9);  // всегда на единицу меньше повторов (ну или буквально как индекс)
    }

    public class sleepTask extends AsyncTask<Object, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setText("Started");
        }

        @Override
        protected Void doInBackground(Object... voids) {
            try {
                for (int i = 0; i < 10; i++) {
                    publishProgress(i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           textView.setText("Finished");
        }
    }
}
