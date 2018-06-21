package com.vergjor.android.partyup;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OwnerCreatedEventsActivity extends AppCompatActivity{

    private static final String RESERVATIONS_REQUEST_URL = "https://mpip.000webhostapp.com/getReservations.php";

    public List<CreatedEvents> listEvents = new ArrayList<>();
    public RecyclerView recyclerView;
    RecyclerView recyclerViewReservations;
    static RecyclerView.Adapter adapter, adapterReservations;
    public static TextView textView;

    TextView createdEventTitle;
    TextView createdEventDate;
    TextView createdEventTime;
    TextView createdEventResNo;
    TextView createResMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_events);

        createResMsg = findViewById(R.id.CreatedEventsMsgFr);
        createdEventTitle = findViewById(R.id.title_created_event_fr);
        createdEventTime = findViewById(R.id.time_created_event_fr);
        createdEventDate = findViewById(R.id.date_created_event_fr);
        createdEventResNo = findViewById(R.id.numRes_created_event_fr);
        recyclerViewReservations = findViewById(R.id.recycler_created_events_fr);

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

        }


        createdEventTitle.setText(listEvents.get(0).eventTitle);
        createdEventTime.setText(listEvents.get(0).eventTime);
        createdEventDate.setText(listEvents.get(0).eventDate);
        StringBuilder sb = new StringBuilder();

        final List<EventReservation> listReservations = new ArrayList<>();

        recyclerViewReservations.setHasFixedSize(true);
        recyclerViewReservations.setLayoutManager(new LinearLayoutManager(this));

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                RESERVATIONS_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray events = new JSONArray(response);
                            for(int i=0; i < events.length(); i++){
                                JSONObject eventsObject = events.getJSONObject(i);
                                String clientName = eventsObject.getString("Cl_Name");
                                String eventName = eventsObject.getString("E_name");
                                String b_tax=eventsObject.getString("B_Tax");
                                if(listEvents.get(0).eventTitle.equals(eventName))
                                    listReservations.add(new EventReservation(clientName, eventName, b_tax));

                            }

                            adapterReservations = new RecyclerAdapterEventReservations(listReservations, getApplicationContext());
                            recyclerViewReservations.setAdapter(adapterReservations);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                OwnerCreatedEventsActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Volley.newRequestQueue(this).add(stringRequest);


        sb.append(listReservations.size());
        sb.append(" / ");
        sb.append(db.userInfoDao().getEventNumResertvations(listEvents.get(0).eventTitle));

        createdEventResNo.setText(sb.toString());

        if(listReservations.size() == 0){
            createResMsg.setText("This event has no reservations");
        }
        else
            createResMsg.setText("");

        adapter = new RecyclerAdapterOwnerCreatedEvents(listEvents, createdEventTitle, createdEventDate, createdEventTime , createdEventResNo, recyclerViewReservations, createResMsg, this);
        recyclerView.setAdapter(adapter);

    }




}
