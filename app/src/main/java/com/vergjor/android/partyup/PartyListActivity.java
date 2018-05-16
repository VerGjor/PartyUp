package com.vergjor.android.partyup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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

public class PartyListActivity extends AppCompatActivity {

    private static final String EVENTS_DATABASE = "https://mpip.000webhostapp.com/GetEvents.php";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    private List<ListEvents> listEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_list);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents();
    }

    private void loadEvents(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                EVENTS_DATABASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray events = new JSONArray(response);
                            for(int i=0; i < events.length(); i++){
                                JSONObject eventsObject = events.getJSONObject(i);
                                String eventName = eventsObject.getString("E_name");
                               // String eventPoster = eventsObject.getString("Picture");
                                int eventNumberOfReservations = eventsObject.getInt("Reservations");
                                String eventDate = eventsObject.getString("Time");
                                String[] split = eventDate.split(" ");
                                listEvents.add(new ListEvents(eventName, split[0], split[1], eventNumberOfReservations));

                            }

                            adapter = new RecyclerAdapter(listEvents, PartyListActivity.this);
                            recyclerView.setAdapter(adapter);
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
                                PartyListActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_party_list, menu);
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
