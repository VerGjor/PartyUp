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

class RecyclerAdapterEventReservations extends RecyclerView.Adapter<RecyclerAdapterEventReservations.ViewHolder> {

    private static List<EventReservation> listItems;
    private static Context context;

    RecyclerAdapterEventReservations(List<EventReservation> listItems, Context context){
        RecyclerAdapterEventReservations.listItems = listItems;
        RecyclerAdapterEventReservations.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardInfo;

        TextView itemTitle;
        public ImageView imageView;


        ViewHolder(final View itemView) {
            super(itemView);

            cardInfo = itemView.findViewById(R.id.card__reser_view_fr);
            itemTitle = itemView.findViewById(R.id.event_title_fr);


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
        final EventReservation event = listItems.get(i);

       viewHolder.itemTitle.setText(event.clientName);


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
