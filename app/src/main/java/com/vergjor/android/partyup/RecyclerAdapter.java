package com.vergjor.android.partyup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    private Dialog myDialog;

    public RecyclerAdapter(List<ListEvents> listItems, Context context){
        this.listItems = listItems;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardInfo;

        public TextView itemTitle;
        public TextView itemDetail;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardInfo = (CardView) itemView.findViewById(R.id.card_view);
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
        final ViewHolder viewHolder = new ViewHolder(v);

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.detailed_card);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.cardInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final ListEvents listItem = listItems.get(viewHolder.getAdapterPosition());
                TextView dialog_title_tv = (TextView) myDialog.findViewById(R.id.event_name);
                TextView dialog_date_tv = (TextView) myDialog.findViewById(R.id.event_date);
                TextView dialog_time_tv = (TextView) myDialog.findViewById(R.id.event_time);
                ImageView dialog_image_img= (ImageView) myDialog.findViewById(R.id.card_image);
                dialog_title_tv.setText(listItem.getEventName());
                dialog_date_tv.setText(listItem.getDateOfEvent());
                dialog_time_tv.setText(listItem.getTimeOfEvent());
                dialog_image_img.setImageResource(R.drawable.screenshot_2);
                myDialog.show();
            }
        });
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
