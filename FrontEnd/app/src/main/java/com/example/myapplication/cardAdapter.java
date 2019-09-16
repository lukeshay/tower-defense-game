package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

<<<<<<< HEAD
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class cardAdapter extends RecyclerView.Adapter<cardAdapter.ViewHolder>{
    private ArrayList<String> mDataSet;
    private Context mContext;
    private deckAdapter deck;

    public cardAdapter(Context context,ArrayList<String> DataSet, deckAdapter _deck){
        mDataSet = DataSet;
        mContext = context;
        deck = _deck;
=======
import androidx.recyclerview.widget.RecyclerView;


public class cardAdapter extends RecyclerView.Adapter<cardAdapter.ViewHolder>{
    private String[] mDataSet;
    private Context mContext;

    public cardAdapter(Context context,String[] DataSet){
        mDataSet = DataSet;
        mContext = context;
>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public LinearLayout mLinearLayout;
        public ViewHolder(View v){
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv);
            mLinearLayout = (LinearLayout) v.findViewById(R.id.ll);
        }
    }

<<<<<<< HEAD
=======

>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
    @Override
    public cardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_view,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
<<<<<<< HEAD
        holder.mTextView.setText(mDataSet.get(position));
=======
        holder.mTextView.setText(mDataSet[position]);
>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.WHITE);
        gd.setCornerRadius(5);
        gd.setStroke(5, Color.BLACK);
        holder.mTextView.setBackground(gd);
<<<<<<< HEAD
        final int viewPosition = position;
=======
>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: this is broken look into why we cannot grab the index as the view should not be null
                //RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
                //int itemPosition = recyclerView.indexOfChild(v);
<<<<<<< HEAD
                Log.e("clickEvent", "user clicked on a card " + Integer.toString(viewPosition) + " " + getItem(viewPosition));
                deck.addItem();
=======
                //Log.e("clickEvent", "user clicked on a card " + String.valueOf(itemPosition));
>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
            }
        });
    }

    @Override
    public int getItemCount(){
<<<<<<< HEAD
        return mDataSet.size();
    }

    public String getItem(int position){
        return mDataSet.get(position);
    }

    public void addItem(){
        mDataSet.add("test");
        this.notifyItemInserted(mDataSet.size());
    }

    public void removeItem(int position){
       mDataSet.remove(position);
       this.notifyItemRemoved(position);
       this.notifyItemRangeChanged(position, mDataSet.size());
    }
=======
        return mDataSet.length;
    }



>>>>>>> c3b2f7a2716cc13f509bad5486a3d2a03a5628ef
}