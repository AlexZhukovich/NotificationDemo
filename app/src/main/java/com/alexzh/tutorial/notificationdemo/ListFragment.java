package com.alexzh.tutorial.notificationdemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexzh.tutorial.notificationdemo.adapter.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private List<String> mNotes;

    @Override
    public View onCreateView(final @NonNull LayoutInflater inflater,
                             final @Nullable ViewGroup container,
                             final @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mNotes = getDummyData();

        StaggeredGridLayoutManager mGridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        NotesAdapter mAdapter = new NotesAdapter(mNotes, new OnItemClickListener() {
            @Override
            public void onNotificationClick(int position) {
                // TODO fix it
                Intent allNotesIntent = new Intent(getActivity(), MainActivity.class);
                Intent detailNoteIntent = new Intent(getActivity(), DetailActivity.class);
                allNotesIntent.putExtra(MainActivity.NOTIFICATION_ID_STR, MainActivity.NOTIFICATION_ID);
                detailNoteIntent.putExtra(DetailActivity.TEXT_MESSAGE, mNotes.get(position));
                PendingIntent detailPendingIntent = PendingIntent.getActivity(getActivity(), 0, detailNoteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                PendingIntent allNotesPendingIntent = PendingIntent.getActivity(getActivity(), 0, allNotesIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle(getString(R.string.action_notification));
                builder.setContentText(mNotes.get(position));
                builder.addAction(
                        R.drawable.ic_notification,
                        getString(R.string.action_all_notes),
                        allNotesPendingIntent);
                builder.setAutoCancel(true);
                builder.setContentIntent(detailPendingIntent);

                final NotificationManager notificationManager = (NotificationManager)
                        getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

                if (notificationManager != null) {
                    notificationManager.notify(MainActivity.NOTIFICATION_ID, builder.build());
                }
            }

            @Override
            public void onContentClick(int position) {
                // TODO fix it
                Intent detailNoteIntent = new Intent(getActivity(), DetailActivity.class);
                detailNoteIntent.putExtra(DetailActivity.TEXT_MESSAGE, mNotes.get(position));
                startActivity(detailNoteIntent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    public List<String> getDummyData() {
        final List<String> notes = new ArrayList<>();
        notes.add("First item");
        notes.add("Second item");
        notes.add("Third item");
        notes.add("Fourth item");
        return notes;
    }
}