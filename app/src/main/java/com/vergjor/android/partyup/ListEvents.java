package com.vergjor.android.partyup;

public class ListEvents {

    private String eventName;
    private String dateOfEvent;
  //  private String eventPoster;

    public ListEvents(String eventName, String dateOfEvent){
        this.eventName = eventName;
        this.dateOfEvent = dateOfEvent;
    }

    public String getEventName(){
        return eventName;
    }

    public String getDateOfEvent(){
        return dateOfEvent;
    }

    /*public String getEventPoster(){
        return eventPoster;
    }*/
}
