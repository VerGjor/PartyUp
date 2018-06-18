package com.vergjor.android.partyup;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
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

public class RecyclerAdapterSavedEvents extends RecyclerView.Adapter<RecyclerAdapterSavedEvents.ViewHolder> {

    private static List<Events> listItems;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private Dialog myDialog;
    static private UserReservations userReservations;
    static int i;
    static boolean wasSuccessful;

    static Semaphore lock1 = new Semaphore(0);
    static Semaphore lock2 = new Semaphore(0);

    RecyclerAdapterSavedEvents(List<Events> listItems, Context context){
        RecyclerAdapterSavedEvents.context = context;
        RecyclerAdapterSavedEvents.listItems = listItems;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardInfo;

        TextView itemTitle;
        public ImageView imageView;
        ImageButton btnReserve;
        ImageButton btnRemove;


        ViewHolder(final View itemView) {
            super(itemView);
            cardInfo = itemView.findViewById(R.id.card_view_saved_events);
            itemTitle = itemView.findViewById(R.id.event_title_saved);
            btnReserve = itemView.findViewById(R.id.makeReservation);
            btnRemove = itemView.findViewById(R.id.removeCurrentEvent);


        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_saved_event_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.detailed_saved_card);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        viewHolder.cardInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Events listItem = listItems.get(viewHolder.getAdapterPosition());
                final TextView dialog_title_tv = myDialog.findViewById(R.id.event_name);
                final TextView dialog_date_tv = myDialog.findViewById(R.id.event_date);
                final TextView dialog_time_tv = myDialog.findViewById(R.id.event_time);
                ImageView dialog_image_img= myDialog.findViewById(R.id.card_image);
                dialog_title_tv.setText(listItem.eventTitle);
                dialog_date_tv.setText(listItem.eventDate);
                dialog_time_tv.setText(listItem.eventTime);
                dialog_image_img.setImageResource(R.drawable.screenshot_2);
                myDialog.show();
            }
        });

        viewHolder.btnReserve.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                final Events listItem = listItems.get(viewHolder.getAdapterPosition());
                i = viewHolder.getAdapterPosition();

                userReservations = new UserReservations(
                        listItem.eventTitle,
                        listItem.eventDate,
                        listItem.eventTime,
                        listItem.taxNumber);
                reserveEventATask task = new reserveEventATask();

                lock1.release();
                task.execute();
                try {
                    lock2.acquire();

                    Toast.makeText(context, "You have a reservation", Toast.LENGTH_SHORT).show();

                    UserDatabase db = Room.databaseBuilder(context,
                            UserDatabase.class, "user-database").allowMainThreadQueries().build();

                    if(db.userInfoDao().numberOfSavedEvents() == 0)
                        SavedEventsActivity.textView.setText("You have no saved events");

                    db.close();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v){

                final Events listItem = listItems.get(viewHolder.getAdapterPosition());
                i = viewHolder.getAdapterPosition();
                UserDatabase db = Room.databaseBuilder(context,
                        UserDatabase.class, "user-database").allowMainThreadQueries().build();
                db.userInfoDao().deleteSavedEvent(listItem.eventTitle);
                listItems.remove(viewHolder.getAdapterPosition());
                SavedEventsActivity.adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Toast.makeText(context, "Event removed", Toast.LENGTH_SHORT).show();

                if(db.userInfoDao().numberOfSavedEvents() == 0)
                    SavedEventsActivity.textView.setText("You have no saved events");
                db.close();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemTitle.setText(listItems.get(position).eventTitle);
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    private static class reserveEventATask extends AsyncTask<Void, Void, Void> {


        UserDatabase db = Room.databaseBuilder(context,
                UserDatabase.class, "user-database").allowMainThreadQueries().build();

        final String uname = db.userInfoDao().getUserName();
        final String e_name =  userReservations.eventTitle;
        final String b_tax= userReservations.taxNumber;

        @Override
        protected Void doInBackground(Void... voids){
            try{

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success= jsonResponse.getBoolean("success");
                            if (!success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
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

                ReserveRequest registerRequest = new ReserveRequest(uname, e_name, b_tax, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                queue.add(registerRequest);

                db.userInfoDao().insertNewReservation(userReservations);
                db.userInfoDao().deleteSavedEvent(userReservations.eventTitle);
                listItems.remove(i);
                SavedEventsActivity.adapter.notifyItemRemoved(i);

                lock1.acquire();
                lock2.release();

                db.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
