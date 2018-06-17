package com.vergjor.android.partyup;

public class Events {

    public String eventTitle;
    public String eventTime;
    public String eventDate;
    public String taxNumber;

    public Events(String eventTitle, String eventDate, String eventTime, String taxNumber){
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.taxNumber = taxNumber;
    }
}
