package com.example.towerDefender.Card;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class deckAdapter extends RecyclerView.Adapter<deckAdapter.ViewHolder>{
    private ArrayList<Card> mDataSet;
    private Context mContext;

    public deckAdapter(Context context, ArrayList<Card> DataSet){
        mDataSet = DataSet;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public LinearLayout mLinearLayout;
        public ImageView mCardView;
        public ViewHolder(View v){
            super(v);
            mTextView = (TextView) v.findViewById(com.example.towerDefender.R.id.deck_tv);
            mLinearLayout = (LinearLayout) v.findViewById(com.example.towerDefender.R.id.deck_ll);
            mCardView = (ImageView) v.findViewById(com.example.towerDefender.R.id.cardImage);
        }
    }

    @Override
    public deckAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(com.example.towerDefender.R.layout.deck_custom_view,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.mTextView.setText(mDataSet.get(position).cardName);
        holder.mCardView.setImageBitmap(CardUtilities.getGameObjectSpriteLeftFacing(mContext, mDataSet.get(position), 0, 0).image);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        gd.setCornerRadius(5);
        gd.setStroke(5, Color.BLACK);
        holder.mTextView.setBackground(gd);
        final int viewPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: this is broken look into why we cannot grab the index as the view should not be null
                //RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
                //int itemPosition = recyclerView.indexOfChild(v);
                //Log.e("clickEvent", "user clicked on a card " + String.valueOf(itemPosition));
                removeItem(viewPosition);
            }
        });
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }

    public Card getItem(int position){
        return mDataSet.get(position);
    }

    public void addItem(){

        this.notifyItemInserted(mDataSet.size());

    }

    public void removeItem(int position){
        mDataSet.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, mDataSet.size());
    }



}