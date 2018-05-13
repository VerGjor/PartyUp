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

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<ListEvents> listItems;
    private Context context;

    public RecyclerAdapter(List<ListEvents> listItems, Context context){
        this.listItems = listItems;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView itemTitle;
        public TextView itemDetail;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.event_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.event_place_title);
            /*imageView =
                    (ImageView) itemView.findViewById(R.id.card_background_img);*/
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ListEvents listItem = listItems.get(i);

        viewHolder.itemTitle.setText(listItem.getEventName());
        viewHolder.itemDetail.setText(listItem.getDateOfEvent());

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
