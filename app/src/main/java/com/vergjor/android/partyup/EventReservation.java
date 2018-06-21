package com.vergjor.android.partyup;

public class EventReservation {

    String eventName;
    private String caffeTaxNo;
    String clientName;

    EventReservation(String clientName, String eventName, String caffeTaxNo){
        this.eventName = eventName;
        this.clientName = clientName;
        this.caffeTaxNo = caffeTaxNo;
    }
}
