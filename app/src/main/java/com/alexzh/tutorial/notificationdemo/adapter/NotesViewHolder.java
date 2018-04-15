package com.alexzh.tutorial.notificationdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alexzh.tutorial.notificationdemo.OnItemClickListener;
import com.alexzh.tutorial.notificationdemo.R;

public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mNoteText;
    private ImageView mNotificationImage;
    private OnItemClickListener mItemClickListener;

    NotesViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        mItemClickListener = onItemClickListener;
        mNoteText = itemView.findViewById(R.id.text);
        mNotificationImage = itemView.findViewById(R.id.image);
        mNotificationImage.setOnClickListener(this);
        itemView.setOnClickListener(this);
        itemView.setTag(itemView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.image) {
            mItemClickListener.onNotificationClick(getAdapterPosition());
        } else {
            mItemClickListener.onContentClick(getAdapterPosition());
        }
    }
}