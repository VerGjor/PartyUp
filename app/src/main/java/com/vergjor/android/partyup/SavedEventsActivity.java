package com.vergjor.android.partyup;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SavedEventsActivity extends AppCompatActivity{

    public List<Events> listEvents = new ArrayList<>();
    public RecyclerView recyclerView;
    static RecyclerView.Adapter adapter;
    public static TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);

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
        int n = db.userInfoDao().numberOfSavedEvents();
        textView = findViewById(R.id.SavedEventsMsg);

        if(n == 0){
            textView.setText("You have no saved events");
        }
        else {
            textView.setText("");
            for (Events e : db.userInfoDao().userSavedEvents()) {
                listEvents.add(new Events(e.eventTitle, e.eventDate, e.eventTime));
            }
            db.close();
        }
        adapter = new RecyclerAdapterSavedEvents(listEvents, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
