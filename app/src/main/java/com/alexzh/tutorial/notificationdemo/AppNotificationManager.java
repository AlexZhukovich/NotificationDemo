package com.alexzh.tutorial.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

public class AppNotificationManager {

    @NonNull
    private Context mContext;

    AppNotificationManager(final @NonNull Context context) {
        mContext = context;
    }

    public void showNotification(final @NonNull Notification notification) {
        final NotificationManager notificationManager = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(MainActivity.NOTIFICATION_ID, notification);
        }
    }

    public Notification createCustomNotification(final NotificationCompat.Action action,
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
}
