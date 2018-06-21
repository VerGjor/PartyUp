package com.vergjor.android.partyup;

public class Events {

    public String eventTitle;
    public String eventTime;
    public String eventDate;
    public String taxNumber;
    public String location;

    public Events(String eventTitle, String eventDate, String eventTime, String taxNumber,String location){
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.taxNumber = taxNumber;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
