package com.alexzh.tutorial.notificationdemo.data.model;

public class Note {

    private long mId;
    private String mText;

    public Note(final long id, final String text) {
        mId = id;
        mText = text;
    }

    public long getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (mId != note.mId) return false;
        return mText != null ? mText.equals(note.mText) : note.mText == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (mId ^ (mId >>> 32));
        result = 31 * result + (mText != null ? mText.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + mId +
                ", text='" + mText + '\'' +
                '}';
    }
}
