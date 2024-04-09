package com.fit2081.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.metrics.Event;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringTokenizer;

public class NewEventCategory extends AppCompatActivity {

    private TextView categoryId;
    private TextView categoryName;
    private TextView eventCount;
    private Switch isActiveCategory;

    ArrayList<EventCategory> categoryList;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);


        categoryId = findViewById(R.id.textViewCatID);
        categoryName = findViewById(R.id.editTextCatName);
        eventCount = findViewById(R.id.editTextEventCount);
        isActiveCategory = findViewById(R.id.eventIsActive);

        categoryList = new ArrayList<>();

    }

    public void onSaveCategoryButtonClick(View view) {

        int eventCountInt;

        categoryId = findViewById(R.id.textViewCatID);
        categoryName = findViewById(R.id.editTextCatName);
        eventCount = findViewById(R.id.editTextEventCount);
        isActiveCategory = findViewById(R.id.eventIsActive);

        String catNameStr = categoryName.getText().toString();
        String eventCountStr = eventCount.getText().toString();

        if (catNameStr.isEmpty()) {
            String msg = "Category Name is required!";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        categoryId.setText(genCategoryId());
        String catIdStr = categoryId.getText().toString();

        if (eventCountStr.isEmpty()){
            eventCountInt = 0;
        } else {
            eventCountInt = Integer.parseInt(eventCountStr);
        }

        boolean isActiveBool = isActiveCategory.isChecked();


        EventCategory eventCategory = new EventCategory(catIdStr, catNameStr, eventCountInt, isActiveBool);
        ArrayList<EventCategory> categoryListOld = getEventCategoryFromSharedPreference();
        categoryListOld.add(eventCategory);

        categoryList = categoryListOld;

        saveArrayListAsText();

        String msg = "Category saved successfully: " + catIdStr;

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public ArrayList<EventCategory> getEventCategoryFromSharedPreference(){

        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        String json = sharedPreferences.getString("KEY_CATEGORY", "[]");
        Type type = new TypeToken<ArrayList<EventCategory>>() {}.getType();
        ArrayList<EventCategory> eventCategoryList = new Gson().fromJson(json, type);

        Log.d("NewEvent", "Retrieved from shared preferences: " + json);

        return eventCategoryList;
    }

    private void saveArrayListAsText(){

        String arrayListStr = gson.toJson(categoryList);
        Log.d("NewEventCategory", "Saving to shared preferences: " +arrayListStr);
        SharedPreferences.Editor editor = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE).edit();
        editor.putString("KEY_CATEGORY", arrayListStr);
        editor.apply();
    }

    public String genCategoryId(){

        String categoryId = "C" + (char)genRandom(65, 90) + (char)genRandom(65, 90) + "-";
        categoryId += genRandom(1000, 9999);

        return categoryId;
    }


    static int genRandom(int minNo, int maxNo){
        return (int)((Math.random() * (maxNo-minNo) + minNo));
    }



}