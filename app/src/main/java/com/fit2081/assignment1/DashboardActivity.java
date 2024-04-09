package com.fit2081.assignment1;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;



public class DashboardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        TextView displayName = findViewById(R.id.textViewdisplayName);

        String usernameRes = sharedPreferences.getString("KEY_USERNAME", "DEFAULT_VALUE");
        displayName.setText(usernameRes);

    }

    public void onAddEventButtonClick(View view) {
        Intent intentAddEvent = new Intent(this, NewEvent.class);
        startActivity(intentAddEvent);
    }

    public void onAddNewCategoryButtonClick(View view) {
        Intent intentAddNewCategory = new Intent(this, NewEventCategory.class);
        startActivity(intentAddNewCategory);
    }
}

