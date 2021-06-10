package com.example.thirdexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;
import static android.os.VibrationEffect.EFFECT_DOUBLE_CLICK;

public class AlarmReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("someId", "someChannel", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        Intent returnIntent = new Intent(context, MainActivity.class);     // открываем первую страницу приложения из нажатия на уведомл
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, returnIntent, 0);

        Notification.Builder notificationBuilder = new Notification.Builder(context, "someId")
                .setContentTitle("Warning")
                .setContentText("Your AlarmManager is working.")
                .setSmallIcon(R.drawable.ic_baseline_science_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(1, notificationBuilder.build());

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }
}
