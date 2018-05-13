package com.alexzh.tutorial.notificationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.alexzh.tutorial.notificationdemo.data.DummyData;
import com.alexzh.tutorial.notificationdemo.data.model.City;

public class DetailActivity extends AppCompatActivity {

    public static final String CITY_ID = "city_id";
    public static final int INVALID_VALUE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView textView = findViewById(R.id.textView);

        long cityId = getIntent().getLongExtra(CITY_ID, INVALID_VALUE);
        if (cityId != INVALID_VALUE) {
            final City city = DummyData.getCityById(cityId);
            if (city != null) {
                textView.setText(city.getDescription());
            }
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
}
