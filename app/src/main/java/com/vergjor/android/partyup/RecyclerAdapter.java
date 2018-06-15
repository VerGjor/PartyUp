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


/**
 * Created by Veronika Gjoreva on 01/03/2018.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<ListEvents> listItems;
    private static Context context;
    private Dialog myDialog;
    private static UserSavedEvents userSavedEvents;
    private static UserReservations userReservations;

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
                        task.execute();
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
                        task.execute();
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
                for(Events e : db.userInfoDao().userReservations()){
                    if(userSavedEvents.eventTitle.equals(e.eventTitle)){
                        exists = true;
                        break;
                    }
                }

                if(!exists){
                    db.userInfoDao().saveEvent(userSavedEvents);
                }
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
                db.userInfoDao().insertNewReservation(userReservations);
                for(Events e : db.userInfoDao().userSavedEvents()){
                    if(userReservations.eventTitle.equals(e.eventTitle)){
                        db.userInfoDao().deleteSavedEvent(userReservations.eventTitle);
                        break;
                    }
                }
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
