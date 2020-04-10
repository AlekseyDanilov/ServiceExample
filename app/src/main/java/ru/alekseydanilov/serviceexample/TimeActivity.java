package ru.alekseydanilov.serviceexample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import ru.alekseydanilov.serviceexample.databinding.ActivityTimeBinding;

/**
 * Created by adanilov on 10,April,2020
 */
public class TimeActivity extends AppCompatActivity {

    private ActivityTimeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String stringExtra = getIntent().getStringExtra(CountService.TIME);
        binding.textTime.setText("This is time from notification " + stringExtra);
    }
}
