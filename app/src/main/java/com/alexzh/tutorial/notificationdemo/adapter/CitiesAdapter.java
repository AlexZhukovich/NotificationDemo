package com.alexzh.tutorial.notificationdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexzh.tutorial.notificationdemo.OnItemClickListener;
import com.alexzh.tutorial.notificationdemo.R;
import com.alexzh.tutorial.notificationdemo.data.model.City;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CityViewHolder> {
    private List<City> mCities;
    private OnItemClickListener mOnItemClickListener;

    public CitiesAdapter(final @NonNull List<City> cities,
                         final @NonNull OnItemClickListener onItemClickListener) {
        mCities = cities;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(final @NonNull ViewGroup parent,
                                             final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_text,
                parent,
                false);
        return new CityViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(final @NonNull CityViewHolder viewHolder,
                                 final int position) {
        viewHolder.mNoteText.setText(mCities.get(position).getDescription());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CircleCrop());

        Glide.with(viewHolder.itemView.getContext())
                .load(mCities.get(position).getImageURL())
                .apply(requestOptions)
                .into(viewHolder.mNotificationImage);
    }

    @Override
    public int getItemCount() {
        return mCities != null ? mCities.size() : 0;
    }
}
