package com.vergjor.android.partyup;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Semaphore;


/**
 * Created by Veronika Gjoreva on 01/03/2018.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<ListEvents> listItems;
    private static Context context;
    private Dialog myDialog;
    private static UserSavedEvents userSavedEvents;
    private static UserReservations userReservations;
    static boolean already_saved = false;
    static boolean hasReservation = false;
    static Semaphore lock = new Semaphore(0);
    static Semaphore lock1 = new Semaphore(0);

    public RecyclerAdapter(List<ListEvents> listItems, Context context){
        this.listItems = listItems;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        protected CardView cardInfo;

        public TextView itemTitle;
        public TextView itemDetail;
        public ImageView imageView;
        public ImageButton btnReserve;
        public ImageButton btnLocation;
        public ImageButton btnLike;

        public ViewHolder(View itemView) {
            super(itemView);
            cardInfo = itemView.findViewById(R.id.card_view);
            itemTitle = itemView.findViewById(R.id.event_title);
            /*imageView =
                    (ImageView) itemView.findViewById(R.id.card_background_img);*/
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(v);

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.detailed_card);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        viewHolder.cardInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final ListEvents listItem = listItems.get(viewHolder.getAdapterPosition());
                final TextView dialog_title_tv = myDialog.findViewById(R.id.event_name);
                final TextView dialog_date_tv = myDialog.findViewById(R.id.event_date);
                final TextView dialog_time_tv = myDialog.findViewById(R.id.event_time);
                ImageView dialog_image_img= myDialog.findViewById(R.id.card_image);
                dialog_title_tv.setText(listItem.getEventName());
                dialog_date_tv.setText(listItem.getDateOfEvent());
                dialog_time_tv.setText(listItem.getTimeOfEvent());
                dialog_image_img.setImageResource(R.drawable.screenshot_2);
                ImageButton dialog_image_reserve_btn = myDialog.findViewById(R.id.reserve_btn);
                dialog_image_reserve_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userReservations = new UserReservations(
                                listItem.getEventName(),
                                listItem.getDateOfEvent(),
                                listItem.getTimeOfEvent());
                        reserveEventATask task = new reserveEventATask();

                        lock.release();
                        task.execute();
                        try {
                            lock1.acquire();
                            if(already_saved){
                                Toast.makeText(context, "You already have a reservation", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(context, "You made a reservation", Toast.LENGTH_SHORT).show();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                ImageButton dialog_image_like_btn = myDialog.findViewById(R.id.like_btn);
                dialog_image_like_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userSavedEvents = new UserSavedEvents(
                                listItem.getEventName(),
                                listItem.getDateOfEvent(),
                                listItem.getTimeOfEvent());
                        addNewSavedEventATask task = new addNewSavedEventATask();
                        lock.release();
                        task.execute();
                        try {
                            lock1.acquire();
                            if(hasReservation){
                                Toast.makeText(context, "You already have a reservation for this event", Toast.LENGTH_SHORT).show();
                            }
                            else
                                if(already_saved){
                                    Toast.makeText(context, "Event is already saved", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(context, "Event saved", Toast.LENGTH_SHORT).show();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });


                final UserDatabase db = Room.databaseBuilder(RecyclerAdapter.context,
                        UserDatabase.class, "user-database").allowMainThreadQueries().build();

                ImageButton btn_res=myDialog.findViewById(R.id.reserve_btn);
                btn_res.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String uname = db.userInfoDao().getUserName();
                        final String e_name = listItem.getEventName();
                        final String b_tax=listItem.getBtax();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success= jsonResponse.getBoolean("success");
                                    if (success){

                                        Intent intent = new Intent(RecyclerAdapter.context, ReserveRequest.class);
                                        RecyclerAdapter.context.startActivity(intent);
                                    }
                                    else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RecyclerAdapter.context);
                                        builder.setMessage("Failed")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        ReserveRequest registerRequest = new ReserveRequest(uname, e_name,b_tax,  responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RecyclerAdapter.context);
                        queue.add(registerRequest);
                    }
                });

                        myDialog.show();


            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ListEvents listItem = listItems.get(i);

        viewHolder.itemTitle.setText(listItem.getEventName());

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

    private static class addNewSavedEventATask extends AsyncTask<Void, Void, Void> {

        UserDatabase db = Room.databaseBuilder(context,
                UserDatabase.class, "user-database").build();


        @Override
        protected Void doInBackground(Void... voids) {
            try{
                boolean exists = false;
                boolean existsInSaved = false;

                for(Events e : db.userInfoDao().userReservations()){
                    if(userSavedEvents.eventTitle.equals(e.eventTitle)){
                        exists = true;
                        break;
                    }
                }

                for(Events e : db.userInfoDao().userSavedEvents()){
                    if(userSavedEvents.eventTitle.equals(e.eventTitle)){
                        existsInSaved = true;
                        break;
                    }
                }

                if(existsInSaved)
                    already_saved = true;
                else
                    already_saved = false;


                if(!exists) {
                    db.userInfoDao().saveEvent(userSavedEvents);
                    hasReservation = false;
                }
                else
                    hasReservation = true;

                lock.acquire();
                lock1.release();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                db.close();
            }

            return null;
        }
    }

    private static class reserveEventATask extends AsyncTask<Void, Void, Void> {

        UserDatabase db = Room.databaseBuilder(context,
                UserDatabase.class, "user-database").build();

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                boolean existsInReser = false;

                for(Events e : db.userInfoDao().userReservations()){
                    if(userReservations.eventTitle.equals(e.eventTitle)){
                        existsInReser = true;
                        break;
                    }
                }

                if(existsInReser)
                    already_saved = true;
                else {
                    already_saved = false;
                    db.userInfoDao().insertNewReservation(userReservations);
                    for(Events e : db.userInfoDao().userSavedEvents()){
                        if(userReservations.eventTitle.equals(e.eventTitle)){
                            db.userInfoDao().deleteSavedEvent(userReservations.eventTitle);
                            break;
                        }
                    }
                }

                lock.acquire();
                lock1.release();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                db.close();
            }
            return null;
        }
    }

}
