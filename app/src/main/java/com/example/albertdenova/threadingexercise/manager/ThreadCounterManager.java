package com.example.albertdenova.threadingexercise.manager;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.IntDef;
import android.util.Log;

import com.example.albertdenova.threadingexercise.database.ThreadingDatabase;
import com.example.albertdenova.threadingexercise.model.ThreadCounter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;

public class ThreadCounterManager {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SHORT_TIME_MS, LONG_TIME_MS})
    public @interface ThreadCounterTimer {}

    public static final int SHORT_TIME_MS = 1000;
    public static final int LONG_TIME_MS = 3000;

    @Inject ThreadingDatabase threadingDatabase;

    private ThreadCounterAsync threadCounterAsyncShort;
    private ThreadCounterAsync threadCounterAsyncLong;

    public LiveData<ThreadCounter> getThreadCounter(@ThreadCounterTimer Integer timeMs) {
        return threadingDatabase.getThreadingDao().selectThreadCounter(timeMs);
    }

    public void start() {
        new ThreadCounterAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SHORT_TIME_MS);
        new ThreadCounterAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, LONG_TIME_MS);
    }

    private class ThreadCounterAsync extends AsyncTask<Integer, Void, Void>
    {
        private boolean isCancelled = false;

        @Override
        protected Void doInBackground(Integer... msValues) {
            ThreadCounter threadCounter = new ThreadCounter();
            threadCounter.setUpdateMs(msValues[0]);
            threadCounter.setCount(0);

            threadingDatabase.getThreadingDao().insert(threadCounter);

            int currentCounterCount = 0;

            while(!isCancelled)
            {
                try {
                    Thread.sleep(new Long(msValues[0]));
                    ++currentCounterCount;
                    threadCounter.setCount(currentCounterCount);
                    threadingDatabase.getThreadingDao().update(threadCounter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

}
