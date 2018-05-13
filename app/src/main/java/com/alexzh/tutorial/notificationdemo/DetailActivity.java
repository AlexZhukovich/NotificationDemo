package com.alexzh.tutorial.notificationdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexzh.tutorial.notificationdemo.data.DummyData;
import com.alexzh.tutorial.notificationdemo.data.model.City;

public class DetailActivity extends AppCompatActivity {

    public static final String CITY_ID = "city_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(@NonNull final LayoutInflater inflater,
                                 @Nullable final ViewGroup container,
                                 @Nullable final Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            TextView textView = rootView.findViewById(R.id.textView);
            if (getActivity().getIntent().getLongExtra(CITY_ID, 0) != 0) {
                final long id = getActivity().getIntent().getLongExtra(CITY_ID, 0);
                final City city = DummyData.getCityById(id);
                if (city != null) {
                    textView.setText(city.getDescription());
                }
            }
            return rootView;
        }
    }
}
