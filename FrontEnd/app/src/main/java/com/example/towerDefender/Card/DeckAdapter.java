package com.example.towerDefender.Card;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder>{
    private ArrayList<Card> mDataSet = new ArrayList<Card>();
    private Context mContext;

    public DeckAdapter(Context context, ArrayList<Card> DataSet){
       // mDataSet = DataSet;
        if(DataSet != null) {
            for (Card x : DataSet) {
                mDataSet.add(x);
            }
        }
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
    public DeckAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(com.example.towerDefender.R.layout.deck_custom_view,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.mTextView.setText(mDataSet.get(position).cardName);
        holder.mCardView.setImageBitmap(CardUtilities.getGameObjectSprite(mContext, mDataSet.get(position), 0, 0, true).image);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        gd.setCornerRadius(5);
        gd.setStroke(5, Color.BLACK);
        holder.mTextView.setBackground(gd);
        final int viewPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
        Log.e("adding item", "added item");
        this.notifyItemInserted(mDataSet.size());
        for(Card x: mDataSet){
            Log.e("cards", x.cardName);
        }
    }

    public void addCard(Card card){
        mDataSet.add(card);
        addItem();
    }

    public void removeItem(int position){
        mDataSet.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, mDataSet.size());
    }

    public ArrayList<Card> getDeck(){
        return mDataSet;
    }



}