package com.example.albertdenova.threadingexercise.view.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.albertdenova.threadingexercise.manager.ThreadCounterManager;
import com.example.albertdenova.threadingexercise.model.ThreadCounter;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;


public class ThreadCounterViewModel extends AndroidViewModel {

    @Inject ThreadCounterManager threadCounterManager;

    public ThreadCounterViewModel(@NonNull Application application) {
        super(application);

        Scope scope = Toothpick.openScopes(application, this);
        Toothpick.inject(this, scope);

        threadCounterManager.start();
    }

    public LiveData<ThreadCounter> getShortThreadCounter() {
        return threadCounterManager.getThreadCounter(ThreadCounterManager.SHORT_TIME_MS);
    }

    public LiveData<ThreadCounter> getLongThreadCounter() {
        return threadCounterManager.getThreadCounter(ThreadCounterManager.LONG_TIME_MS);
    }

}
