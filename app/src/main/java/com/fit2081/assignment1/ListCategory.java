package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fit2081.assignment1.provider.EventCategoryViewModel;

import java.util.ArrayList;

public class ListCategory extends AppCompatActivity {

    private EventCategoryViewModel eventCategoryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_bar_layout);

        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);

        ActionBar backButton = getSupportActionBar();
        backButton.setDisplayHomeAsUpEnabled(true);

        eventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);
        eventCategoryViewModel.getAllEventCategory().observe(this, newData -> {
            adapter.setCategory(new ArrayList<EventCategory>(newData));
        });

        loadCategoryFragment(null);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, NewEvent.class);
        startActivity(intent);
        return true;
    }

    public void loadCategoryFragment(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.category_container, new FragmentListCategory()).commit();
    }
}