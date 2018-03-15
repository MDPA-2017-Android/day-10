package com.example.albertdenova.threadingexercise.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.albertdenova.threadingexercise.R;
import com.example.albertdenova.threadingexercise.model.ThreadCounter;
import com.example.albertdenova.threadingexercise.view.model.ThreadCounterViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.short_counter) TextView shortCounterTextView;
    @BindView(R.id.long_counter) TextView longCounterTextView;

    private ThreadCounterViewModel threadCounterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        threadCounterViewModel = ViewModelProviders.of(this).get(ThreadCounterViewModel.class);

        threadCounterViewModel.getShortThreadCounter().observe(this, threadCounter -> {
            if(threadCounter != null)
            {
                String counter = String.format(Locale.ENGLISH, "%d", threadCounter.getCount());
                shortCounterTextView.setText(counter);
            }
        });

        threadCounterViewModel.getLongThreadCounter().observe(this, threadCounter -> {
            if(threadCounter != null)
            {
                String counter = String.format(Locale.ENGLISH, "%d", threadCounter.getCount());
                longCounterTextView.setText(counter);
            }
        });
    }
}
