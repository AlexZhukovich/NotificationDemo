package com.alexzh.tutorial.notificationdemo.data;

import com.alexzh.tutorial.notificationdemo.data.model.Note;

import java.util.ArrayList;
import java.util.List;

public final class DummyData {

    private DummyData() {}

    public static List<Note> getDummyData() {
        final List<Note> notes = new ArrayList<>();
        notes.add(new Note(1L,"First item"));
        notes.add(new Note(2L,"Second item"));
        notes.add(new Note(3L,"Third item"));
        notes.add(new Note(4L,"Fourth item"));
        return notes;
    }

    public static Note getNoteById(final long id) {
        return getDummyData().get((int) id - 1);
    }
}