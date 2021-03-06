package com.example.thirdexample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
    private MediaPlayer musicService;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        musicService = MediaPlayer.create(this, R.raw.music);
        musicService.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        musicService.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        musicService.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}