package com.vergjor.android.partyup;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapterSavedEvents extends RecyclerView.Adapter<RecyclerAdapterSavedEvents.ViewHolder> {

    private static List<Events> listItems;
    private static RecyclerAdapterSavedEvents adapter;
    private static Context context;
    private Dialog myDialog;
    private static String title;
    static private UserSavedEvents userSavedEvents;
    static private UserReservations userReservations;

    public RecyclerAdapterSavedEvents(List<Events> listItems, Context context){
        this.context = context;
        this.listItems = listItems;
        this.adapter = this;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardInfo;

        public TextView itemTitle;
        public ImageView imageView;
        public ImageButton btnReserve;
        public ImageButton btnRemove;


        public ViewHolder(View itemView) {
            super(itemView);
            cardInfo = itemView.findViewById(R.id.card_view_saved_events);
            itemTitle = itemView.findViewById(R.id.event_title_saved);
            btnReserve = itemView.findViewById(R.id.makeReservation);
            btnRemove = itemView.findViewById(R.id.removeCurrentEvent);

            btnReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    userReservations = new UserReservations(
                            listItems.get(i).eventTitle,
                            listItems.get(i).eventDate,
                            listItems.get(i).eventTime);
                    reserveEventATask task = new reserveEventATask();
                    task.execute();
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int i = getAdapterPosition();
                    UserDatabase db = Room.databaseBuilder(context,
                            UserDatabase.class, "user-database").allowMainThreadQueries().build();
                    db.userInfoDao().deleteSavedEvent(listItems.get(i).eventTitle);
                    db.close();
                }
            });
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

    /*private static class removeSavedEventTask extends AsyncTask<Void, Void, Void> {


        UserDatabase db = Room.databaseBuilder(context,
                UserDatabase.class, "user-database").build();

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                db.userInfoDao().deleteReservation(title);
                adapter.notifyDataSetChanged();
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
*/
    private static class reserveEventATask extends AsyncTask<Void, Void, Void> {


        UserDatabase db = Room.databaseBuilder(context,
                UserDatabase.class, "user-database").build();

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                db.userInfoDao().insertNewReservation(userReservations);
                db.userInfoDao().deleteSavedEvent(userReservations.eventTitle);
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