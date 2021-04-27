package com.example.alasti.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;

import com.example.alasti.R;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());

        int tone = intent.getIntExtra("Value",0);

        if(tone == 1){
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.classic_music);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.start();
        }
        else if(tone == 2){
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.crazy_music);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.start();
        }
        else if(tone == 3){
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.iphone_music);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.start();
        }
        else if(tone == 4){
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.trumpet_music);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.start();
        }
        else {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.rap_music);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.start();
        }

    }
}