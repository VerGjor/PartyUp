package com.vergjor.android.partyup;

public class ListEvents {

    private String eventName;
    private String dateOfEvent;
    private String timeOfEvent;
    //private String eventPoster;
    private int eventTax;
    private int eventNumberOfReservations;


    public ListEvents(String eventName, String dateOfEvent, int eventTax, int eventNumberOfReservations){
        this.eventName = eventName;
        String[] split = dateOfEvent.split(" ");
        this.dateOfEvent = split[0];
        this.timeOfEvent = split[1];
       // this.eventPoster = eventPoster;
        this.eventTax = eventTax;
        this.eventNumberOfReservations = eventNumberOfReservations;
    }

    public String getEventName(){
        return eventName;
    }

    public String getDateOfEvent(){
        return dateOfEvent;
    }

    public String getTimeOfEvent(){
        return timeOfEvent;
    }

   /* public String getEventPoster(){
        return eventPoster;
    }*/

    public int getEventTax(){
        return eventTax;
    }

    public int getEventNumberOfReservations(){
        return eventNumberOfReservations;
    }

    public void setEventNumberOfReservations(){
        eventNumberOfReservations++;
    }

}
