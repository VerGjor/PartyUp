package com.vergjor.android.partyup;

public class ListEvents {

    private String eventName;
    private String dateOfEvent;
    private String timeOfEvent;
    //private String eventPoster;
    //private int eventTax;
    private int eventNumberOfReservations;


    public ListEvents(String eventName, String dateOfEvent, String timeOfEvent, int eventNumberOfReservations){
        this.eventName = eventName;
        this.dateOfEvent = dateOfEvent;
        this.timeOfEvent = timeOfEvent;
       // this.eventPoster = eventPoster;
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

    public int getEventNumberOfReservations(){
        return eventNumberOfReservations;
    }

    public void setEventNumberOfReservations(){
        eventNumberOfReservations++;
    }

}
