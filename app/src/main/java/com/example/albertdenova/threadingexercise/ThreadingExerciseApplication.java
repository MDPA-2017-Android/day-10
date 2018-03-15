package com.example.albertdenova.threadingexercise;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.albertdenova.threadingexercise.database.ThreadingDatabase;

import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.config.Module;

public class ThreadingExerciseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Scope scope = Toothpick.openScopes(this);
        scope.installModules(new Module() {{
            bind(ThreadingDatabase.class).toInstance(createDatabaseInstance());
        }});
    }

    private ThreadingDatabase createDatabaseInstance() {
        return Room.databaseBuilder(getApplicationContext(),
                ThreadingDatabase.class, "database-name").build();
    }
}
