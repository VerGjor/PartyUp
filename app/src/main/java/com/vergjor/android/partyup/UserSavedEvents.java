package com.vergjor.android.partyup;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = {"eventTitle"},
        unique = true)}
)
public class UserSavedEvents {

    @PrimaryKey
    @NonNull
    public String eventTitle;

    @ColumnInfo
    public String eventTime;

    @ColumnInfo
    public String eventDate;


    UserSavedEvents(String eventTitle, String eventDate, String eventTime){
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

}
