package com.alexzh.tutorial.notificationdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexzh.tutorial.notificationdemo.OnItemClickListener;
import com.alexzh.tutorial.notificationdemo.R;
import com.alexzh.tutorial.notificationdemo.data.model.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    private List<Note> mNotes;
    private OnItemClickListener mOnItemClickListener;

    public NotesAdapter(final @NonNull List<Note> notes,
                        final @NonNull OnItemClickListener onItemClickListener) {
        mNotes = notes;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(final @NonNull ViewGroup parent,
                                              final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_text,
                parent,
                false);
        return new NotesViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(final @NonNull NotesViewHolder viewHolder,
                                 final int position) {
        viewHolder.mNoteText.setText(mNotes.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mNotes != null ? mNotes.size() : 0;
    }
}
