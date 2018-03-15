package com.example.albertdenova.threadingexercise;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;

import com.example.albertdenova.threadingexercise.database.ThreadingDatabase;

import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

public class ThreadingExerciseApplication extends Application {

    public static final String NOTIFICATION_CHANNEL_ID = "ThreadingExerciseChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        Scope scope = Toothpick.openScopes(this);
        scope.installModules(new Module() {{
            bind(ThreadingDatabase.class).toInstance(createDatabaseInstance());
            bind(NotificationManager.class).toInstance((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
        }});

        CreateNotificationChannel();
    }

    private ThreadingDatabase createDatabaseInstance() {
        return Room.databaseBuilder(getApplicationContext(),
                ThreadingDatabase.class, "database-name").build();
    }

    private void CreateNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    NOTIFICATION_SERVICE);
            assert notificationManager != null;

            notificationManager.createNotificationChannel(channel);
        }
    }
}
