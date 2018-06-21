package com.vergjor.android.partyup;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Veronika Gjoreva on 01/03/2018.
 */

class RecyclerAdapterOwnerCreatedEvents extends RecyclerView.Adapter<RecyclerAdapterOwnerCreatedEvents.ViewHolder> {


    private static List<CreatedEvents> listItems;
    private static Context context;
    private static TextView createdEventTitle;
    private static TextView createdEventDate;
    private static TextView createdEventTime;



    RecyclerAdapterOwnerCreatedEvents(List<CreatedEvents> listItems, TextView createdEventTitle, TextView createdEventDate, TextView createdEventTime, Context context){
        RecyclerAdapterOwnerCreatedEvents.listItems = listItems;
        RecyclerAdapterOwnerCreatedEvents.context = context;
        RecyclerAdapterOwnerCreatedEvents.createdEventTitle = createdEventTitle;
        RecyclerAdapterOwnerCreatedEvents.createdEventDate = createdEventDate;
        RecyclerAdapterOwnerCreatedEvents.createdEventTime = createdEventTime;

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


                }
            });



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
