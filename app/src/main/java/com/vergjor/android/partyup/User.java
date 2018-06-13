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

    @ColumnInfo(name = "user_type")
    public int user_type;

    public User(String userName, int user_type){
        this.userName = userName;
        this.user_type = user_type;
    }

    public int getUserType(){
        return user_type;
    }

}

