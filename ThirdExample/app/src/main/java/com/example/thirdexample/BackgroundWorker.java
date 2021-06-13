package com.example.thirdexample;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackgroundWorker extends Worker {
    Context context;

    public BackgroundWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public Result doWork() {
        context.getMainExecutor().execute(() -> Toast.makeText(context, "Сас ))", Toast.LENGTH_LONG).show());
        // тут надо в UI тред отправлять, таким полукостылем. todo А еще можно сделать через handler
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }
}
