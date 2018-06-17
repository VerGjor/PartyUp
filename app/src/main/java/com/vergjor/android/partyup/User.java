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

    @ColumnInfo(name = "tax_owner")
    public String tax_owner;

    @ColumnInfo(name = "addressOwner")
    public String addressOwner;

    public User(String userName, int user_type, String tax_owner, String addressOwner){
        this.userName = userName;
        this.user_type = user_type;
        this.tax_owner = tax_owner;
        this.addressOwner = addressOwner;
    }

}

