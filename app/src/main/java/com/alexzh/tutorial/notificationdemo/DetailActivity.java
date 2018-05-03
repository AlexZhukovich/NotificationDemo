package com.alexzh.tutorial.notificationdemo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexzh.tutorial.notificationdemo.data.DummyData;
import com.alexzh.tutorial.notificationdemo.data.model.Note;

public class DetailActivity extends AppCompatActivity {

    public static final String NOTE_ID = "note_id";

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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            TextView textView = rootView.findViewById(R.id.textView);
            if (getActivity().getIntent().getLongExtra(NOTE_ID, 0) != 0) {
                final long id = getActivity().getIntent().getLongExtra(NOTE_ID, 0);
                final Note note = DummyData.getNoteById(id);
                if (note != null) {
                    textView.setText(note.getText());
                }
            }
            return rootView;
        }
    }
}
