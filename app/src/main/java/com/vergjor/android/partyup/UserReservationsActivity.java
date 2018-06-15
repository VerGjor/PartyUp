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

public class UserReservationsActivity extends AppCompatActivity {

    private List<Events> listEvents = new ArrayList<>();
    RecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;
    static TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservations);

        recyclerView = findViewById(R.id.recycler_reser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").allowMainThreadQueries().build();
        int n = db.userInfoDao().numberOfUserReservations();
        textView = findViewById(R.id.myReservationsMsg);

        if(n == 0){
            textView.setText("You have no reservations");
        }
        else {
            textView.setText("");
            for (Events e : db.userInfoDao().userReservations()) {
                listEvents.add(new Events(e.eventTitle, e.eventDate, e.eventTime));
            }
            db.close();
        }
        adapter = new RecyclerAdapterMyReservations(listEvents, this);
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
