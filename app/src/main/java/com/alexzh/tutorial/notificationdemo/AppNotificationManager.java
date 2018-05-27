package com.alexzh.tutorial.notificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.alexzh.tutorial.notificationdemo.data.model.City;

import java.util.ArrayList;
import java.util.List;

public class AppNotificationManager {
    private final static String EXTRA_NOTIFICATION_ID = "notification_id";
    private final static String APP_PACKAGE = "com.alexzh.tutorial.notificationdemo";
    private final static String CITIES_CHANEL_ID = APP_PACKAGE + ".CITIES_CHANNEL";
    private final static String APP_CHANEL_ID = APP_PACKAGE + ".APP_CHANNEL";
    private final static String GROUP_KEY_CITIES = APP_CHANEL_ID + ".CITIES_GROUP";
    private final static long BASE_NOTIFICATION_ID = 100L;
    private final static int INVALID_NOTIFICATION_ID = -1;

    @NonNull
    private Context mContext;

    AppNotificationManager(final @NonNull Context context) {
        mContext = context;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final List<NotificationChannel> channels = new ArrayList<>();
            channels.add(createAppNotificationChanel(
                    CITIES_CHANEL_ID,
                    mContext.getString(R.string.notification_channel_cities_name),
                    mContext.getString(R.string.notification_channel_cities_description),
                    NotificationManagerCompat.IMPORTANCE_HIGH));

            channels.add(createAppNotificationChanel(
                    APP_CHANEL_ID,
                    mContext.getString(R.string.notification_channel_app_name),
                    mContext.getString(R.string.notification_channel_app_description),
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
        return new NotificationCompat.Builder(mContext, CITIES_CHANEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(mContext.getString(R.string.notification_title))
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .addAction(action)
                .setGroup(GROUP_KEY_CITIES)
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

    public void showDetailsNotificationWithAllCitiesAction(final @NonNull City city) {
        final Intent allCitiesIntent = new Intent(mContext, MainActivity.class);
        final int notificationId = (int) (BASE_NOTIFICATION_ID + city.getId());

        allCitiesIntent.putExtra(EXTRA_NOTIFICATION_ID, notificationId);

        final PendingIntent allCitiesPendingIntent = PendingIntent.getActivity(
                mContext,
                notificationId,
                allCitiesIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final Intent detailCityIntent = new Intent(mContext, DetailActivity.class);
        detailCityIntent.putExtra(DetailActivity.CITY_ID, city.getId());

        PendingIntent detailPendingIntent = PendingIntent.getActivity(
                mContext,
                notificationId,
                detailCityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Action allCitiesAction = new NotificationCompat.Action(
                R.drawable.ic_notification,
                mContext.getString(R.string.notification_action_all_cities),
                allCitiesPendingIntent);

        final Notification notification = createCustomNotification(
                allCitiesAction,
                city.getDescription(),
                detailPendingIntent);

        showNotification(notification, notificationId);
    }

    public void showBundleNotification(final int notificationCount) {
        final Notification summaryNotification = new NotificationCompat.Builder(mContext, CITIES_CHANEL_ID)
                .setContentText(notificationCount + " cities")
                .setSmallIcon(R.drawable.ic_notification)
                .setStyle(new NotificationCompat.InboxStyle())
                .setGroup(GROUP_KEY_CITIES)
                .setGroupSummary(true)
                .build();
        showNotification(summaryNotification, (int) BASE_NOTIFICATION_ID);
    }

    public void hideNotification(final @Nullable Intent intent) {
        final NotificationManager notificationManager = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null && intent != null) {
            final int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, INVALID_NOTIFICATION_ID);
            notificationManager.cancel(notificationId);
        }
    }
}
