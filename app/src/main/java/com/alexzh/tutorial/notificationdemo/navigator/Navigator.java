package com.alexzh.tutorial.notificationdemo.navigator;

import android.app.Activity;
import android.content.Intent;

import com.alexzh.tutorial.notificationdemo.DetailActivity;

public final class Navigator {

    private Navigator() {}

    public static void navigateToDetails(final Activity activity, final long noteId) {
        final Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailActivity.NOTE_ID, noteId);
        activity.startActivity(intent);
    }
}