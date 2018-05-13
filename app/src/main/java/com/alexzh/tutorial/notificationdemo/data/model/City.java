package com.alexzh.tutorial.notificationdemo.data.model;

import android.support.annotation.NonNull;

public class City {

    private final long mId;
    private final String mName;
    private final String mDescription;
    private final String mImageURL;

    public City(final long id,
                @NonNull final String name,
                @NonNull final String description,
                @NonNull final String imageURL) {
        mId = id;
        mName = name;
        mDescription = description;
        mImageURL = imageURL;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return mId == city.mId &&
                mName.equals(city.mName) &&
                mDescription.equals(city.mDescription) &&
                mImageURL.equals(city.mImageURL);
    }

    @Override
    public int hashCode() {
        int result = (int) (mId ^ (mId >>> 32));
        result = 31 * result + mName.hashCode();
        result = 31 * result + mDescription.hashCode();
        result = 31 * result + mImageURL.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + mId +
                ", name='" + mName + '\'' +
                ", description='" + mDescription + '\'' +
                ", imageURL='" + mImageURL + '\'' +
                '}';
    }
}
