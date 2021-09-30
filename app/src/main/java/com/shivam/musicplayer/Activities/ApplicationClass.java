package com.shivam.musicplayer.Activities;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class ApplicationClass extends Application {

    public static final String CHANNEL_ID_1 = "channel1";
    public static final String CHANNEL_ID_2 = "channel2";
    public static final String  ACTION_PREVIOUS = "actionPrev";
    public static final String ACTION_NEXT = "actionNext";
    public static final String ACTION_PLAY = "actionPlay";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O )
        {
            NotificationChannel notificationChannel1 = new NotificationChannel(CHANNEL_ID_1, "Channel-1", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel1.setDescription("Channel 1 Desc...");

            NotificationChannel notificationChannel2 = new NotificationChannel(CHANNEL_ID_2, "Channel-2", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel2.setDescription("Channel 2 Desc...");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel1);
            notificationManager.createNotificationChannel(notificationChannel2);

        }
    }
}
