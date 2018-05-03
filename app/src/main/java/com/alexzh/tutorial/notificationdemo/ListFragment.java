package com.alexzh.tutorial.notificationdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexzh.tutorial.notificationdemo.adapter.NotesAdapter;
import com.alexzh.tutorial.notificationdemo.data.DummyData;
import com.alexzh.tutorial.notificationdemo.data.model.Note;
import com.alexzh.tutorial.notificationdemo.navigator.Navigator;

import java.util.List;

public class ListFragment extends Fragment implements View.OnClickListener {
    private List<Note> mNotes;

    private AppNotificationManager mNotificationManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            mNotificationManager = new AppNotificationManager(getActivity());
        }
    }

    @Override
    public View onCreateView(final @NonNull LayoutInflater inflater,
                             final @Nullable ViewGroup container,
                             final @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mNotes = DummyData.getDummyData();

        rootView.findViewById(R.id.send_all_notifications).setOnClickListener(this);

        StaggeredGridLayoutManager mGridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        NotesAdapter mAdapter = new NotesAdapter(mNotes, new OnItemClickListener() {
            @Override
            public void onNotificationClick(int position) {
                mNotificationManager.showDetailsNotificationWithAllNotesAction(mNotes.get(position));
            }

            @Override
            public void onContentClick(int position) {
                Navigator.navigateToDetails(getActivity(), mNotes.get(position).getId());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_all_notifications:
                for (Note noteTitle: mNotes) {
                    mNotificationManager.showDetailsNotificationWithAllNotesAction(noteTitle);
                }
                mNotificationManager.showBundleNotification(mNotes.size());
                break;
        }
    }
}