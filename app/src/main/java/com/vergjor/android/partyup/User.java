package com.vergjor.android.partyup;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int userID;

    @ColumnInfo(name = "user_name")
    public String userName;

    public User(String userName){
        this.userName = userName;
    }

}

