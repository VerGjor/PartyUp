package com.vergjor.android.partyup;

public class ListEvents {

    private String eventName;
    private String dateOfEvent;
    private String timeOfEvent;
    //private String eventPoster;
    //private int eventTax;
    private int eventNumberOfReservations;
    private String btax;
    private String location;
    public ListEvents(String eventName, String dateOfEvent, String timeOfEvent, int eventNumberOfReservations,String b_tax,String location){
        this.eventName = eventName;
        this.dateOfEvent = dateOfEvent;
        this.timeOfEvent = timeOfEvent;
       // this.eventPoster = eventPoster;
        this.eventNumberOfReservations = eventNumberOfReservations;
        this.btax=b_tax;
        this.location=location;
    }


    public String getLocation() {
        return location;
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

    public int getEventNumberOfReservations(){
        return eventNumberOfReservations;
    }

    public void setEventNumberOfReservations(){
        eventNumberOfReservations++;
    }

    public String getBtax() {
        return btax;
    }
}
