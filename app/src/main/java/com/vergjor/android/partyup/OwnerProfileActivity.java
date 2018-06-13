package com.vergjor.android.partyup;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    }

    public void getMyReservations(View v){

    }

    public void createNewEvent(View v){

    }

    public void eventsOverview(View v){

    }
}
