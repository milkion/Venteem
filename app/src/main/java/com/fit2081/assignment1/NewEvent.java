package com.fit2081.assignment1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.fit2081.assignment1.provider.EventCategoryViewModel;
import com.fit2081.assignment1.provider.EventViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class NewEvent extends AppCompatActivity {

    private TextView eventId;
    private TextView categoryId;
    private TextView eventName;
    private TextView ticketsAvailable;
    private Switch isActiveEvent;

    DrawerLayout drawerlayout;

    ArrayList<Event> listEvent;


    private EventCategoryViewModel eventCategoryViewModel;

    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_layout);

        listEvent = new ArrayList<>();

        eventId = findViewById(R.id.textViewEventID);
        categoryId = findViewById(R.id.editTextEventCatId);
        eventName = findViewById(R.id.editTextEventName);
        ticketsAvailable = findViewById(R.id.editTextTicketNumber);
        isActiveEvent = findViewById(R.id.switchEventActive);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        View touchpad = findViewById(R.id.view);
        TextView tvGestures = findViewById(R.id.tvGestures);

        CustomGestureDetector customGestureDetector = new CustomGestureDetector(tvGestures, this);
        GestureDetectorCompat mDetector = new GestureDetectorCompat(this, customGestureDetector);
        mDetector.setOnDoubleTapListener(customGestureDetector);
        touchpad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });

        drawerlayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener(drawerlayout, this));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();


        CategoryRecyclerAdapter categoryAdapter = new CategoryRecyclerAdapter();
        EventRecyclerAdapter eventAdapter = new EventRecyclerAdapter();

        eventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);
        eventCategoryViewModel.getAllEventCategory().observe(this, newData -> {
            categoryAdapter.setCategory(new ArrayList<EventCategory>(newData));
        });

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(this, newData -> {
            eventAdapter.setEvent(new ArrayList<Event>(newData));
        });

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onSaveEventButtonClick(view);
            }
        });


        loadCategoryFragment(null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    /**
     * A convenience class to extend when you only want to listen for a subset of all the gestures.
     */
    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener{

        private GestureDetectorCompat mDetector;
        private ScaleGestureDetector mScaleDetector;


        TextView tvGestures;
        private NewEvent newEvent;

        public CustomGestureDetector(TextView tvGestures, NewEvent newEvent){

            this.tvGestures = tvGestures;
            this.newEvent = newEvent;
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            tvGestures.setText("onLongPress");
            newEvent.clearEventForm();
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            tvGestures.setText("onDoubleTap");
            newEvent.onSaveEventButtonClick(null);
            return super.onDoubleTap(e);
        }

    }


    public void onSaveEventButtonClick(View view){

        View rootView = findViewById(android.R.id.content);

        AtomicInteger ticketsAvailableInt = new AtomicInteger();

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

        if (!eventNameStr.matches(".*[a-zA-Z].*")){
            String msg = "Invalid Event Name!";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<List<EventCategory>> categoryFind = eventCategoryViewModel.getEventCategoryById(catIdStr);

        categoryFind.observe(this, newData -> {
            if (newData.isEmpty()){
                String msg = "Category ID not found!";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            } else {
                categoryFind.removeObservers(this);

                eventId.setText(genEventId());
                String eventIdStr = eventId.getText().toString();

                if (ticketsAvailableStr.isEmpty()){
                    ticketsAvailableInt.set(0);
                } else {
                    ticketsAvailableInt.set(Integer.parseInt(ticketsAvailableStr));
                }

                boolean isActiveBool = isActiveEvent.isChecked();

                Event event = new Event(eventIdStr, catIdStr, eventNameStr, ticketsAvailableInt.get(), isActiveBool);
                eventViewModel.insert(event);

                EventCategory category = newData.get(0);
                int eventCount = category.getEventCount();
                category.setEventCount(eventCount + 1);
                eventCategoryViewModel.update(category);

                Snackbar undoBar = Snackbar.make(rootView, "Event saved successfully", Snackbar.LENGTH_LONG);
                undoBar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eventViewModel.deleteEvent(eventIdStr);
                        Snackbar.make(rootView, "Event removed", Snackbar.LENGTH_SHORT).show();
                    }
                });
                undoBar.show();
            }
        });
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

        if (item.getItemId() == R.id.optionDeleteAllCategories){
            onDeleteCategory();

        } else if (item.getItemId() == R.id.optionDeleteAllEvents){
            onDeleteEvent();
        } else if (item.getItemId() == R.id.optionsClearEventForm){
            clearEventForm();
        }

        return true;
    }

    public void onDeleteCategory(){
        eventCategoryViewModel.deleteAll();
    }

    public void onDeleteEvent(){ eventViewModel.deleteAll(); }


    public void clearEventForm(){

        eventId.setText("");
        categoryId.setText("");
        eventName.setText("");
        ticketsAvailable.setText("");
        isActiveEvent.setChecked(false);
    }


    public void loadCategoryFragment(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.category_container, new FragmentListCategory()).commit();
    }


}