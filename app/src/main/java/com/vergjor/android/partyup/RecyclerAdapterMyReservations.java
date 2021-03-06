package com.vergjor.android.partyup;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class RecyclerAdapterMyReservations extends RecyclerView.Adapter<RecyclerAdapterMyReservations.ViewHolder> {

    private static List<Events> listItems;
    private static Context context;
    private Dialog myDialog;

    static Semaphore lock1 = new Semaphore(0);
    static Semaphore lock2 = new Semaphore(0);

    public RecyclerAdapterMyReservations(List<Events> listItems, Context context){
        this.context = context;
        this.listItems = listItems;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardInfo;

        public TextView itemTitle;
        public ImageView imageView;
        public ImageButton btnRemove;


        public ViewHolder(View itemView) {
            super(itemView);
            cardInfo = itemView.findViewById(R.id.card_view_my_reser);
            itemTitle = itemView.findViewById(R.id.event_title_my_reser);
            btnRemove = itemView.findViewById(R.id.removeReser);

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){

                    int i = getAdapterPosition();
                    UserDatabase db = Room.databaseBuilder(context,
                            UserDatabase.class, "user-database").allowMainThreadQueries().build();

                    try {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (!success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RecyclerAdapterMyReservations.context);
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

                        CancelReservationRequest cancelRequest = new CancelReservationRequest(db.userInfoDao().getUserName(), listItems.get(i).eventTitle, listItems.get(i).taxNumber, responseListener);
                        HurlStack hurlStack = new HurlStack();
                        RequestQueue queue = Volley.newRequestQueue(RecyclerAdapterMyReservations.context, hurlStack);
                        queue.add(cancelRequest);

                        db.userInfoDao().deleteReservation(listItems.get(i).eventTitle);
                        listItems.remove(i);
                        UserReservationsActivity.adapter.notifyItemRemoved(i);
                        Toast.makeText(context, "Reservation canceled", Toast.LENGTH_SHORT).show();

                        if (db.userInfoDao().numberOfUserReservations() == 0)
                            UserReservationsActivity.textView.setText("You have no reservations");
                    }
                    catch (Exception e){
                        e.getMessage();
                    }
                    finally {
                        db.close();
                    }
                }
            });
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_my_reser_layout, parent, false);
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


}


