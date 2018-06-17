package com.vergjor.android.partyup;


public class CreatedEvents {

    public String eventTitle;
    public String eventTime;
    public String eventDate;
    public String taxNumber;
    public String numReservations;



    CreatedEvents(String eventTitle, String eventDate, String eventTime, String taxNumber, String numReservations){
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.taxNumber = taxNumber;
        this.numReservations = numReservations;
    }

}
