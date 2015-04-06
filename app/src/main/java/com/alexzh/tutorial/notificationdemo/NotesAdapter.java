package com.alexzh.tutorial.notificationdemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private Context mContext;
    private String[] mList;

    public NotesAdapter(Context context, String[] list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_text,
                viewGroup,
                false);
        return new ViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mNoteText.setText(mList[position]);
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context mContext;
        private TextView mNoteText;
        private ImageView mNotificationImage;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mNoteText = (TextView) itemView.findViewById(R.id.text);
            mNotificationImage = (ImageView) itemView.findViewById(R.id.image);
            mNotificationImage.setOnClickListener(this);
            itemView.setOnClickListener(this);
            itemView.setTag(itemView);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.image) {
                Intent allNotesIntent = new Intent(mContext, MainActivity.class);
                Intent detailNoteIntent = new Intent(mContext, DetailActivity.class);
                allNotesIntent.putExtra(MainActivity.NOTIFICATION_ID_STR, MainActivity.NOTIFICATION_ID);
                detailNoteIntent.putExtra(DetailActivity.TEXT_MESSAGE, mNoteText.getText());
                PendingIntent detailPendingIntent = PendingIntent.getActivity(mContext, 0, detailNoteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                PendingIntent allNotesPendingIntent = PendingIntent.getActivity(mContext, 0, allNotesIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setContentTitle(mContext.getString(R.string.action_notification));
                mBuilder.setContentText(mNoteText.getText());
                mBuilder.addAction(
                        R.drawable.ic_notification,
                        mContext.getString(R.string.action_all_notes),
                        allNotesPendingIntent);
                mBuilder.setAutoCancel(true);
                mBuilder.setContentIntent(detailPendingIntent);

                NotificationManager mNotificationManager = (NotificationManager)
                            mContext.getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(MainActivity.NOTIFICATION_ID, mBuilder.build());
            } else {
                Intent detailNoteIntent = new Intent(mContext, DetailActivity.class);
                detailNoteIntent.putExtra(DetailActivity.TEXT_MESSAGE, mNoteText.getText());
                mContext.startActivity(detailNoteIntent);
            }
        }
    }
}
