package com.example.albertdenova.threadingexercise.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ThreadCounter {

    @PrimaryKey
    private int updateMs;
    private int count;



    public int getUpdateMs() {
        return updateMs;
    }

    public void setUpdateMs(int updateMs) {
        this.updateMs = updateMs;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
