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

class RecyclerAdapterOwnerCreatedEvents extends RecyclerView.Adapter<RecyclerAdapterOwnerCreatedEvents.ViewHolder> {

    private List<CreatedEvents> listItems;
    private static Context context;
    private Dialog myDialog;

    public RecyclerAdapterOwnerCreatedEvents(List<CreatedEvents> listItems, Context context){
        this.listItems = listItems;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        protected CardView cardInfo;

        public TextView itemTitle;
        public ImageView imageView;

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
        myDialog.setContentView(R.layout.detailed_created_card);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.cardInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final CreatedEvents listItem = listItems.get(viewHolder.getAdapterPosition());
                final TextView dialog_title_tv = myDialog.findViewById(R.id.event_name);

                ImageView dialog_image_img= myDialog.findViewById(R.id.card_image);
                dialog_title_tv.setText(listItem.eventTitle);

                dialog_image_img.setImageResource(R.drawable.screenshot_2);
                myDialog.show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final CreatedEvents listItem = listItems.get(i);

        viewHolder.itemTitle.setText(listItem.eventTitle);

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
