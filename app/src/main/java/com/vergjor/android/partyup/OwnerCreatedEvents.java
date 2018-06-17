package com.vergjor.android.partyup;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = {"eventTitle"},
        unique = true)}
)
public class OwnerCreatedEvents {

    @PrimaryKey
    @NonNull
    public String eventTitle;

    @ColumnInfo
    public String eventTime;

    @ColumnInfo
    public String eventDate;

    @ColumnInfo
    public String taxNumber;

    @ColumnInfo
    public String numReservations;



    OwnerCreatedEvents(String eventTitle, String eventDate, String eventTime, String taxNumber, String numReservations){
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.taxNumber = taxNumber;
        this.numReservations = numReservations;
    }

}
