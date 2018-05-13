package com.alexzh.tutorial.notificationdemo.data.model;

import android.support.annotation.NonNull;

public class City {

    private final long mId;
    private final String mName;
    private final String mDescription;
    private final String mImageURL;
    private final String mSource;

    public City(final long id,
                @NonNull final String name,
                @NonNull final String description,
                @NonNull final String imageURL,
                @NonNull final String source) {
        mId = id;
        mName = name;
        mDescription = description;
        mImageURL = imageURL;
        mSource = source;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public String getSource() {
        return mSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return mId == city.mId &&
                mName.equals(city.mName) &&
                mDescription.equals(city.mDescription) &&
                mImageURL.equals(city.mImageURL) &&
                mSource.equals(city.mSource);
    }

    @Override
    public int hashCode() {
        int result = (int) (mId ^ (mId >>> 32));
        result = 31 * result + mName.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + mImageURL.hashCode();
        result = 31 * result + mSource.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + mId +
                ", name='" + mName + '\'' +
                ", description='" + mDescription + '\'' +
                ", imageURL='" + mImageURL + '\'' +
                ", source='" + mSource + '\'' +
                '}';
    }
}
