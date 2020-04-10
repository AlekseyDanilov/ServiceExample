package ru.alekseydanilov.serviceexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.core.app.NotificationCompat;

/**
 * Created by adanilov on 10,April,2020
 */
public class CountService extends Service {

    private static final String TAG = CountService.class.getSimpleName();
    public static final String TIME = "TIME";

    private ScheduledExecutorService scheduledExecutorService;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    public CountService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        scheduledExecutorService = Executors.newScheduledThreadPool(1);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = getNotificationBuilder();

        builder.setContentTitle("Count service notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return new NotificationCompat.Builder(this);
        } else {
            String my_channel_id = "my_channel_id";

            if (manager.getNotificationChannel(my_channel_id) == null) {
                NotificationChannel channel = new NotificationChannel(my_channel_id, "Text for user", NotificationManager.IMPORTANCE_LOW);
                manager.createNotificationChannel(channel);
            }

            return new NotificationCompat.Builder(this, my_channel_id);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        startForeground(1, getNotification("start notification"));

        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: " + System.currentTimeMillis());

                manager.notify(1, getNotification("time is " + System.currentTimeMillis()));
                Intent intentToSend = new Intent(SimpleReceiver.SIMPLE_ACTION);
                intentToSend.putExtra(TIME, String.valueOf(System.currentTimeMillis()));
                sendBroadcast(intentToSend);
            }
        }, 1000, 4000, TimeUnit.MILLISECONDS);
        return START_STICKY;
    }

    private Notification getNotification(String contentText) {
        Intent intent = new Intent(this, TimeActivity.class);
        intent.putExtra(TIME, contentText);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 12, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return builder.setContentText(contentText)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public void onDestroy() {
        scheduledExecutorService.shutdownNow();
        Log.d(TAG, "onDestroy");
    }


}
