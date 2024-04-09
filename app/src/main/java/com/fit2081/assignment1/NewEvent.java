package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringTokenizer;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class NewEvent extends AppCompatActivity {

    private TextView eventId;
    private TextView categoryId;
    private TextView eventName;
    private TextView ticketsAvailable;
    private Switch isActiveEvent;

    DrawerLayout drawerlayout;

    ArrayList<EventCategory> listEventCategory = new ArrayList<>();

    CategoryRecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_layout);

        eventId = findViewById(R.id.textViewEventID);
        categoryId = findViewById(R.id.editTextEventCatId);
        eventName = findViewById(R.id.editTextEventName);
        ticketsAvailable = findViewById(R.id.editTextTicketNumber);
        isActiveEvent = findViewById(R.id.switchEventActive);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        drawerlayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener(drawerlayout, this));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onSaveEventButtonClick(view);
            }

//            TODO snackbar for undo action
        });

        loadCategoryFragment(null);

//        recyclerView = findViewById(R.id.eventCategoryRecycler);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        listEventCategory = getEventCategoryFromSharedPreference();
//
//        recyclerAdapter = new CategoryRecyclerAdapter();
//        recyclerAdapter.setCategory(listEventCategory);
//        recyclerView.setAdapter(recyclerAdapter);
    }

    public void onSaveEventButtonClick(View view){

        int ticketsAvailableInt;

        eventId = findViewById(R.id.textViewEventID);
        categoryId = findViewById(R.id.editTextEventCatId);
        eventName = findViewById(R.id.editTextEventName);
        ticketsAvailable = findViewById(R.id.editTextTicketNumber);
        isActiveEvent = findViewById(R.id.switchEventActive);

        String catIdStr = categoryId.getText().toString();
        String eventNameStr = eventName.getText().toString();
        String ticketsAvailableStr = ticketsAvailable.getText().toString();

        if (catIdStr.isEmpty() || eventNameStr.isEmpty()){
            String msg = "Category ID & Event Name is required!";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        eventId.setText(genEventId());
        String eventIdStr = eventId.getText().toString();

        if (ticketsAvailableStr.isEmpty()){
            ticketsAvailableInt = 0;
        } else {
            ticketsAvailableInt = Integer.parseInt(ticketsAvailableStr);
        }

        boolean isActiveBool = isActiveEvent.isChecked();

        saveDataToSharedPreference(eventIdStr, catIdStr, eventNameStr, ticketsAvailableInt, isActiveBool);

        String msg = "Event saved successfully: " + eventIdStr + " to " + catIdStr;

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    public String genEventId(){

        String eventId = "E" + (char)genRandom(65, 90) + (char)genRandom(65, 90) + "-";
        eventId += genRandom(10000, 99999);

        return eventId;
    }


    static int genRandom(int minNo, int maxNo){
        return (int)((Math.random() * (maxNo-minNo) + minNo));
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

//        TODO: IMPLEMENT THE CODE BODY
        if (item.getItemId() == R.id.optionDeleteAllCategories){
            clearAllCategory();

        } else if (item.getItemId() == R.id.optionDeleteAllEvents){
            return true;
        } else if (item.getItemId() == R.id.optionsClearEventForm){
            clearEventForm();

        } else if (item.getItemId() == R.id.optionsRefresh){

//            System.out.println("Refresh clicked");
//            ArrayList<EventCategory> cat = getEventCategoryFromSharedPreference();
//            recyclerAdapter.setCategory(cat);
//            recyclerAdapter.notifyDataSetChanged();

            loadCategoryFragment(null);

        }

        return true;
    }

    public void clearAllCategory(){
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit().remove("KEY_CATEGORY");
        editor.apply();
    }

    public void clearEventForm(){

        eventId.setText("");
        categoryId.setText("");
        eventName.setText("");
        ticketsAvailable.setText("");
        isActiveEvent.setChecked(false);
    }

//    public ArrayList<EventCategory> getEventCategoryFromSharedPreference(){
//
//        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
//        String json = sharedPreferences.getString("KEY_CATEGORY", "[]");
//        Type type = new TypeToken<ArrayList<EventCategory>>() {}.getType();
//        ArrayList<EventCategory> eventCategoryList = new Gson().fromJson(json, type);
//
//        Log.d("NewEvent", "Retrieved from shared preferences: " + json);
//
//        return eventCategoryList;
//    }

    private void saveDataToSharedPreference(String eventId, String eventCatId, String eventName, int ticketCount, boolean eventActive ){

        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("KEY_EVENT_ID", eventId);
        editor.putString("KEY_EVENT_CAT_ID", eventCatId);
        editor.putString("KEY_EVENT_NAME", eventName);
        editor.putInt("KEY_EVENT_TICKETS", ticketCount);
        editor.putBoolean("KEY_EVENT_ACTIVE", eventActive);

        editor.apply();
    }

    public void loadCategoryFragment(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.category_container, new FragmentListCategory()).commit();
    }

}