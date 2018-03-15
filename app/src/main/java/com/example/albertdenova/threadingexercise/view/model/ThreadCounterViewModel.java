package com.example.albertdenova.threadingexercise.view.model;

import android.app.Application;
import android.app.NotificationManager;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.example.albertdenova.threadingexercise.ThreadingExerciseApplication;
import com.example.albertdenova.threadingexercise.manager.ThreadCounterManager;
import com.example.albertdenova.threadingexercise.model.ThreadCounter;

import java.util.UUID;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;

public class ThreadCounterViewModel extends AndroidViewModel {

    @Inject ThreadCounterManager threadCounterManager;

    @Inject NotificationManager notificationManager;

    private int notificationId = 0;


    public ThreadCounterViewModel(@NonNull Application application) {
        super(application);

        Scope scope = Toothpick.openScopes(application, this);
        Toothpick.inject(this, scope);

        threadCounterManager.start();
    }

    public LiveData<ThreadCounter> getShortThreadCounter() {
        return Transformations.map(
                threadCounterManager.getThreadCounter(ThreadCounterManager.SHORT_TIME_MS),
                threadCounter -> {
                    if(threadCounter != null && threadCounter.getCount() % 10 == 0)
                    {
                        SetupCelebrationNotification(threadCounter.getCount());
                    }
                    return threadCounter;
                });
    }

    public LiveData<ThreadCounter> getLongThreadCounter() {
        return threadCounterManager.getThreadCounter(ThreadCounterManager.LONG_TIME_MS);
    }

    public void SetupCelebrationNotification(Integer threadCount) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplication(), ThreadingExerciseApplication.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(android.support.v4.R.drawable.notification_icon_background)
                .setContentTitle("Short thread celebration")
                .setContentText(String.format("We have achieved %d number", threadCount))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        ++notificationId;

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

}
