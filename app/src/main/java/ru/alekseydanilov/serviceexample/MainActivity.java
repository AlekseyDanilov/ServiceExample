package ru.alekseydanilov.serviceexample;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import ru.alekseydanilov.serviceexample.databinding.ActivityMainBinding;

/**
 * Created by adanilov on 10,April,2020
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private SimpleReceiver simpleReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Intent intent = new Intent(MainActivity.this, CountService.class);

        binding.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(intent);
            }
        });

        binding.stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
            }
        });

        simpleReceiver = new SimpleReceiver();
        intentFilter = new IntentFilter(SimpleReceiver.SIMPLE_ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(simpleReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(simpleReceiver);
    }
}
