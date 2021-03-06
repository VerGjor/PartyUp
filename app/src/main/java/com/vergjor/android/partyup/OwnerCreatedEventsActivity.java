package com.vergjor.android.partyup;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OwnerCreatedEventsActivity extends AppCompatActivity{

    private static final String RESERVATIONS_REQUEST_URL = "https://mpip.000webhostapp.com/getReservations.php";

    public List<CreatedEvents> listEvents = new ArrayList<>();
    public RecyclerView recyclerView;
    static RecyclerView.Adapter adapter;
    public static TextView textView;

    TextView createdEventTitle;
    TextView createdEventDate;
    TextView createdEventTime;

    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_events);

        scrollView = findViewById(R.id.scrollView);

        createdEventTitle = findViewById(R.id.title_created_event_fr);
        createdEventTime = findViewById(R.id.time_created_event_fr);
        createdEventDate = findViewById(R.id.date_created_event_fr);

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

        RecyclerView.LayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView = findViewById(R.id.recycler_created_events);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").allowMainThreadQueries().build();
        int n = db.userInfoDao().numberOfOwnerCreatedEvents();
        textView = findViewById(R.id.created_event_msg);

        if(n == 0){
            textView.setText("You have no events");
        }
        else {
            textView.setText("");
            for (CreatedEvents e : db.userInfoDao().createdEvents()) {
                listEvents.add(new CreatedEvents(e.eventTitle, e.eventDate, e.eventTime, e.taxNumber, e.numReservations));
            }

            createdEventTitle.setText(listEvents.get(0).eventTitle);
            createdEventTime.setText(listEvents.get(0).eventTime);
            createdEventDate.setText(listEvents.get(0).eventDate);

            adapter = new RecyclerAdapterOwnerCreatedEvents(listEvents, createdEventTitle, createdEventDate, createdEventTime , this);
            recyclerView.setAdapter(adapter);

        }




    }




}
