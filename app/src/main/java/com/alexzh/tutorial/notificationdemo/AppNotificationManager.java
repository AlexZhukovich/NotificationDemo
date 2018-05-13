package com.alexzh.tutorial.notificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.alexzh.tutorial.notificationdemo.data.model.City;

import java.util.ArrayList;
import java.util.List;

public class AppNotificationManager {
    private final static String APP_PACKAGE = "com.alexzh.tutorial.notificationdemo";
    private final static String NOTES_CHANEL_ID = APP_PACKAGE + ".CITIES_CHANNEL";
    private final static String APP_CHANEL_ID = APP_PACKAGE + ".APP_CHANNEL";
    private final static String GROUP_KEY_NOTES = APP_CHANEL_ID + ".CITIES_GROUP";
    private final static long BASE_NOTIFICATION_ID = 100L;

    @NonNull
    private Context mContext;

    AppNotificationManager(final @NonNull Context context) {
        mContext = context;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final List<NotificationChannel> channels = new ArrayList<>();
            channels.add(createAppNotificationChanel(
                    NOTES_CHANEL_ID,
                    mContext.getString(R.string.notes_channel_name),
                    "Cities description",
                    NotificationManagerCompat.IMPORTANCE_HIGH));

            channels.add(createAppNotificationChanel(
                    APP_CHANEL_ID,
                    mContext.getString(R.string.app_channel_name),
                    "Application description",
                    NotificationManagerCompat.IMPORTANCE_DEFAULT));

            final NotificationManager notificationManager = (NotificationManager)
                    mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.createNotificationChannels(channels);
            }
        }
    }

    private void showNotification(final @NonNull Notification notification, final int notificationId) {
        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        notificationManager.notify(notificationId, notification);
    }

    private Notification createCustomNotification(final NotificationCompat.Action action,
                                                  final String message,
                                                  final PendingIntent contentIntent) {
        return new NotificationCompat.Builder(mContext, NOTES_CHANEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(mContext.getString(R.string.action_notification))
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .addAction(action)
                .setGroup(GROUP_KEY_NOTES)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createAppNotificationChanel(final String chanelId,
                                                            final String chanelName,
                                                            final String chanelDescription,
                                                            final int chanelImportance) {
        NotificationChannel channel = new NotificationChannel(chanelId, chanelName, chanelImportance);
        channel.setDescription(chanelDescription);
        return channel;
    }

    public void showDetailsNotificationWithAllNotesAction(final @NonNull City city) {
        final Intent allNotesIntent = new Intent(mContext, MainActivity.class);
        final int notificationId = (int) (BASE_NOTIFICATION_ID + city.getId());

        allNotesIntent.putExtra(MainActivity.NOTIFICATION_ID_STR, MainActivity.NOTIFICATION_ID);

        final PendingIntent allNotesPendingIntent = PendingIntent.getActivity(
                mContext,
                notificationId,
                allNotesIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final Intent detailNoteIntent = new Intent(mContext, DetailActivity.class);
        detailNoteIntent.putExtra(DetailActivity.CITY_ID, city.getId());

        PendingIntent detailPendingIntent = PendingIntent.getActivity(
                mContext,
                notificationId,
                detailNoteIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Action allNotesAction = new NotificationCompat.Action(
                R.drawable.ic_notification,
                mContext.getString(R.string.action_all_notes),
                allNotesPendingIntent);

        final Notification notification = createCustomNotification(
                allNotesAction,
                city.getDescription(),
                detailPendingIntent);

        showNotification(notification, notificationId);
    }

    public void showBundleNotification(final int notificationCount) {
        final Notification summaryNotification = new NotificationCompat.Builder(mContext, NOTES_CHANEL_ID)
                .setContentText(notificationCount + " cities")
                .setSmallIcon(R.drawable.ic_notification)
                .setStyle(new NotificationCompat.InboxStyle())
                .setGroup(GROUP_KEY_NOTES)
                .setGroupSummary(true)
                .build();
        showNotification(summaryNotification, (int) BASE_NOTIFICATION_ID);
    }
}
