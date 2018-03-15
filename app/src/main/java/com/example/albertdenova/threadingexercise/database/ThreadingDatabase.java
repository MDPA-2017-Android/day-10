package com.example.albertdenova.threadingexercise.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.albertdenova.threadingexercise.database.dao.ThreadingDao;
import com.example.albertdenova.threadingexercise.model.ThreadCounter;

@Database(entities = {ThreadCounter.class}, version = 1)
public abstract class ThreadingDatabase extends RoomDatabase {

    public abstract ThreadingDao getThreadingDao();

}
