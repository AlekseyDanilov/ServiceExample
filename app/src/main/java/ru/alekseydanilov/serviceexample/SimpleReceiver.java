package ru.alekseydanilov.serviceexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by adanilov on 10,April,2020
 */
public class SimpleReceiver extends BroadcastReceiver {

    public static final String SIMPLE_ACTION = "ru.alekseydanilov.serviceexample.SIMPLE_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        String time = intent.getStringExtra(CountService.TIME);
        Toast.makeText(context, "Current time is: " + time, Toast.LENGTH_SHORT).show();

        Intent launchActivity = new Intent(context, TimeActivity.class);
        launchActivity.putExtra(CountService.TIME, time);
        context.startActivity(launchActivity);
    }
}
