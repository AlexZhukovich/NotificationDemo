package com.alexzh.tutorial.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

public class AppNotificationManager {

    @NonNull
    private Context mContext;

    AppNotificationManager(final @NonNull Context context) {
        mContext = context;
    }

    private void showNotification(final @NonNull Notification notification) {
        final NotificationManager notificationManager = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(MainActivity.NOTIFICATION_ID, notification);
        }
    }

    private Notification createCustomNotification(final NotificationCompat.Action action,
                                                  final String message,
                                                  final PendingIntent contentIntent) {
        return new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(mContext.getString(R.string.action_notification))
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .addAction(action)
                .build();
    }

    public void showDetailsNotificationWithAllNotesAction(final @NonNull String note) {
        final Intent allNotesIntent = new Intent(mContext, MainActivity.class);
        allNotesIntent.putExtra(MainActivity.NOTIFICATION_ID_STR, MainActivity.NOTIFICATION_ID);

        final PendingIntent allNotesPendingIntent = PendingIntent.getActivity(
                mContext,
                0,
                allNotesIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        final Intent detailNoteIntent = new Intent(mContext, DetailActivity.class);
        detailNoteIntent.putExtra(DetailActivity.TEXT_MESSAGE,note);

        PendingIntent detailPendingIntent = PendingIntent.getActivity(
                mContext,
                0,
                detailNoteIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        final NotificationCompat.Action allNotesAction = new NotificationCompat.Action(
                R.drawable.ic_notification,
                mContext.getString(R.string.action_all_notes),
                allNotesPendingIntent);

        final Notification notification = createCustomNotification(
                allNotesAction,
                note,
                detailPendingIntent);

        showNotification(notification);
    }
}
