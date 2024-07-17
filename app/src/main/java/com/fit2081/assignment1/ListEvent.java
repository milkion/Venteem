package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.fit2081.assignment1.provider.EventViewModel;

import java.util.ArrayList;


public class ListEvent extends AppCompatActivity {

    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_bar_layout);

        EventRecyclerAdapter adapter = new EventRecyclerAdapter();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(myToolbar);

        ActionBar backButton = getSupportActionBar();
        backButton.setDisplayHomeAsUpEnabled(true);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(this, newData -> {
            adapter.setEvent(new ArrayList<Event>(newData));
        });

        loadEventFragment(null);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, NewEvent.class);
        startActivity(intent);
        return true;
    }

    public void loadEventFragment(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.event_container, new FragmentListEvent()).commit();
    }
}