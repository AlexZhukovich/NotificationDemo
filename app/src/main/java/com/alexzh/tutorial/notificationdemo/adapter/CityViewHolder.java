package com.alexzh.tutorial.notificationdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alexzh.tutorial.notificationdemo.OnItemClickListener;
import com.alexzh.tutorial.notificationdemo.R;

public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mNoteText;
    public ImageView mNotificationImage;
    private OnItemClickListener mItemClickListener;

    CityViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        mItemClickListener = onItemClickListener;
        mNoteText = itemView.findViewById(R.id.text);
        mNotificationImage = itemView.findViewById(R.id.image);
        Button mSendNotificationButton = itemView.findViewById(R.id.button);

        mSendNotificationButton.setOnClickListener(this);
        itemView.setOnClickListener(this);
        itemView.setTag(itemView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            mItemClickListener.onNotificationClick(getAdapterPosition());
        } else {
            mItemClickListener.onContentClick(getAdapterPosition());
        }
    }
}