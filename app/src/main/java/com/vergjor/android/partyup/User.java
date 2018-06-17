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

    public User(String userName, int user_type, String tax_owner){
        this.userName = userName;
        this.user_type = user_type;
        this.tax_owner = tax_owner;
    }

    public int getUserType(){
        return user_type;
    }
    public String getTax_owner(){ return tax_owner; }

}

