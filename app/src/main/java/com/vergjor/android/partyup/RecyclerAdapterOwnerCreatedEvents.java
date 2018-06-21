package com.vergjor.android.partyup;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


/**
 * Created by Veronika Gjoreva on 01/03/2018.
 */

class RecyclerAdapterOwnerCreatedEvents extends RecyclerView.Adapter<RecyclerAdapterOwnerCreatedEvents.ViewHolder> {


    private static final String RESERVATIONS_REQUEST_URL = "https://mpip.000webhostapp.com/getReservations.php";
    private static List<CreatedEvents> listItems;
    private static Context context;
    private static TextView createdEventTitle;
    private static TextView createdEventDate;
    private static TextView createdEventTime;
    private static TextView createdNoRes;
    private static TextView createResMsg;

    static public RecyclerView recyclerViewReservations;
    static RecyclerView.Adapter adapterReservations;


    RecyclerAdapterOwnerCreatedEvents(List<CreatedEvents> listItems, TextView createdEventTitle, TextView createdEventDate, TextView createdEventTime, TextView createdNoRes, RecyclerView recyclerViewReservations, TextView createResMsg, Context context){
        RecyclerAdapterOwnerCreatedEvents.listItems = listItems;
        RecyclerAdapterOwnerCreatedEvents.context = context;
        RecyclerAdapterOwnerCreatedEvents.createdEventTitle = createdEventTitle;
        RecyclerAdapterOwnerCreatedEvents.createdEventDate = createdEventDate;
        RecyclerAdapterOwnerCreatedEvents.createdEventTime = createdEventTime;
        RecyclerAdapterOwnerCreatedEvents.createdNoRes = createdNoRes;
        RecyclerAdapterOwnerCreatedEvents.recyclerViewReservations = recyclerViewReservations;
        RecyclerAdapterOwnerCreatedEvents.createResMsg = createResMsg;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardInfo;

        TextView itemTitle;
        public ImageView imageView;


        ViewHolder(final View itemView) {
            super(itemView);

            cardInfo = itemView.findViewById(R.id.card_view);
            itemTitle = itemView.findViewById(R.id.event_title);


            cardInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final CreatedEvents event = listItems.get(getAdapterPosition());

                    createdEventTitle.setText(event.eventTitle);
                    createdEventTime.setText(event.eventTime);
                    createdEventDate.setText(event.eventDate);

                    StringBuilder sb = new StringBuilder();

                    final List<EventReservation> listReservations = new ArrayList<>();

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
                                            String b_tax = eventsObject.getString("B_Tax");
                                            if(event.eventTitle.equals(eventName))
                                                listReservations.add(new EventReservation(clientName, eventName, b_tax));

                                        }

                                        adapterReservations = new RecyclerAdapterEventReservations(listReservations, context);
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
                                            context,
                                            error.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    Volley.newRequestQueue(context).add(stringRequest);


                    UserDatabase db = Room.databaseBuilder(context,
                            UserDatabase.class, "user-database").allowMainThreadQueries().build();

                    sb.append(listReservations.size());
                    sb.append(" / ");
                    sb.append(db.userInfoDao().getEventNumResertvations(event.eventTitle));
                    db.close();

                    if(listReservations.size() == 0){
                        createResMsg.setText("This event has no reservations");
                    }
                    else
                        createResMsg.setText("");

                    createdNoRes.setText(sb.toString());

                }
            });


            /*imageView =
                    (ImageView) itemView.findViewById(R.id.card_background_img);*/


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_created_layout, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final CreatedEvents event = listItems.get(i);

        viewHolder.itemTitle.setText(event.eventTitle);


       /* Picasso.with(context)
                .load(listItem.getEventPoster())
                        .into(viewHolder.imageView);*/

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public CardView getCardPosition(View itemView){
        return (CardView) itemView.findViewById(R.id.card_view);
    }


}
