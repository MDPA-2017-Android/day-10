package com.example.albertdenova.threadingexercise.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albertdenova.threadingexercise.model.ThreadCounter;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ThreadingDao {

    @Query("Select * from threadcounter where updatems = (:updateMs)")
    LiveData<ThreadCounter> selectThreadCounter(Integer updateMs);

    @Insert(onConflict = REPLACE)
    void insert(ThreadCounter threadCounter);

    @Update
    void update(ThreadCounter threadCounter);
}
