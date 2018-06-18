package com.vergjor.android.partyup;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OwnerCreatedEventsActivity extends AppCompatActivity{

    public List<CreatedEvents> listEvents = new ArrayList<>();
    public RecyclerView recyclerView;
    static RecyclerView.Adapter adapter;
    public static TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

        TextView title = findViewById(R.id.title_saved_activity);
        title.setText("Created Events");
        updateData();
    }

    protected void onStart(){
        super.onStart();

        listEvents.clear();
        updateData();
    }

    public TextView getTextView(){
        return textView;
    }


    public void updateData(){

        recyclerView = findViewById(R.id.recycler_saved_events);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").allowMainThreadQueries().build();
        int n = db.userInfoDao().numberOfOwnerCreatedEvents();
        textView = findViewById(R.id.SavedEventsMsg);

        if(n == 0){
            textView.setText("You have no events");
        }
        else {
            textView.setText("");
            for (CreatedEvents e : db.userInfoDao().createdEvents()) {
                listEvents.add(new CreatedEvents(e.eventTitle, e.eventDate, e.eventTime, e.taxNumber, e.numReservations));
            }
            db.close();
        }
        adapter = new RecyclerAdapterOwnerCreatedEvents(listEvents, this);
        recyclerView.setAdapter(adapter);
    }



}
