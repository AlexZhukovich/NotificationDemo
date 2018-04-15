package com.alexzh.tutorial.notificationdemo.data;

import java.util.ArrayList;
import java.util.List;

public final class DummyData {

    private DummyData() {}

    public static List<String> getDummyData() {
        final List<String> notes = new ArrayList<>();
        notes.add("First item");
        notes.add("Second item");
        notes.add("Third item");
        notes.add("Fourth item");
        return notes;
    }
}