package com.alexzh.tutorial.notificationdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alexzh.tutorial.notificationdemo.OnItemClickListener;
import com.alexzh.tutorial.notificationdemo.R;

public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mDescription;
    public ImageView mCityImage;
    private OnItemClickListener mItemClickListener;

    CityViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        mItemClickListener = onItemClickListener;
        mDescription = itemView.findViewById(R.id.description);
        mCityImage = itemView.findViewById(R.id.city_image);
        Button mSendNotificationButton = itemView.findViewById(R.id.send_button);

        mSendNotificationButton.setOnClickListener(this);
        itemView.setOnClickListener(this);
        itemView.setTag(itemView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_button) {
            mItemClickListener.onNotificationClick(getAdapterPosition());
        } else {
            mItemClickListener.onContentClick(getAdapterPosition());
        }
    }
}