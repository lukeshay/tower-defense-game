package com.example.towerDefender.Card;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.towerDefender.R;
import com.example.towerDefender.Util.CardUtilities;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    private ArrayList<Card> mDataSet;
    private Context mContext;
    private DeckAdapter deck;
    private PopupWindow mPopupWindow;
    private ImageButton closeButton;
    private TextView textview;
    private ImageView card_desc_imv;

    public CardAdapter(Context context, ArrayList<Card> dataSet, DeckAdapter deck){
        mDataSet = dataSet;
        mContext = context;
        this.deck = deck;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        LinearLayout mLinearLayout;
        ImageView mCardView;
        ViewHolder(View v){
            super(v);
            mTextView = (TextView) v.findViewById(com.example.towerDefender.R.id.tv);
            mLinearLayout = (LinearLayout) v.findViewById(com.example.towerDefender.R.id.ll);
            mCardView = (ImageView) v.findViewById(com.example.towerDefender.R.id.invImageView);
        }
    }

    @Override
    @NonNull
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(mContext).inflate(com.example.towerDefender.R.layout.custom_view,parent,false);

        return new ViewHolder(v);
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
                //TODO: this is broken look into why we cannot grab the index as the view should not be null
                //RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
                //int itemPosition = recyclerView.indexOfChild(v);
                   Log.e("clickEvent", "user clicked on a card " + Integer.toString(viewPosition) + " " + getItem(viewPosition).cardName);
                    deck.addCard(getItem(viewPosition));
                   //deck.addItem();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View view){
                Log.e("returned", getItem(viewPosition).cardName);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.card_desc_view,null);
                mPopupWindow = new PopupWindow(
                        customView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                closeButton = customView.findViewById(R.id.ib_close);
                textview = customView.findViewById(R.id.card_desc_tv);
                card_desc_imv = customView.findViewById(R.id.card_desc_img);
                textview.setText(
                       "Name:" + getItem(viewPosition).cardName + "\n" +
                       "Description:" + getItem(viewPosition).cardDescription + "\n" +
                       "Cast Cost:" + getItem(viewPosition).castingCost + "\n" +
                               "Speed:" + getItem(viewPosition).speed + "\n" +
                                "HP:" + getItem(viewPosition).hitPoints + "\n" +
                               "Range:" + getItem(viewPosition).range + "\n"
                );
                card_desc_imv.setImageBitmap(CardUtilities.getGameObjectSprite(mContext,getItem(viewPosition),0,0, false).image);
                closeButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        mPopupWindow.dismiss();
                    }
                });
                mPopupWindow.showAtLocation(view.getRootView(), Gravity.CENTER,0,0);
                return true;
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