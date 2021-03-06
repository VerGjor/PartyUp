package com.vergjor.android.partyup;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class OwnerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);
        TextView textView = findViewById(R.id.nameOfUserClient);
        StringBuilder sb = new StringBuilder();
        sb.append("Hello there ");
        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").allowMainThreadQueries().build();
        String name = db.userInfoDao().getUserName();
        sb.append(name);
        textView.setText(sb.toString());
    }


    public void getSavedEvents(View v){
        Intent savedEvents = new Intent(this, SavedEventsActivity.class);
        startActivity(savedEvents);
    }

    public void getMyReservations(View v){
        Intent myReservations = new Intent(this, UserReservationsActivity.class);
        startActivity(myReservations);
    }

    public void createNewEvent(View v){
        Intent registerIntent = new Intent(this, AddEventActivity.class);
        startActivity(registerIntent);
    }

    public void eventsOverview(View v){
        Intent ownerEventsIntent = new Intent(this, OwnerCreatedEventsActivity.class);
        startActivity(ownerEventsIntent);
    }
}
