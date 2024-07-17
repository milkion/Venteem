package com.fit2081.assignment1;


import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawerlayout;
    protected Context context;

    MyNavigationListener(DrawerLayout drawerlayout, Context context) {

        this.drawerlayout = drawerlayout;
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // get the id of the selected item
        int id = item.getItemId();

        if (id == R.id.optionViewCategories)  {

            Intent intentCategoryList = new Intent(context, ListCategory.class);
            context.startActivity(intentCategoryList);

        } else if (id == R.id.optionAddCategory) {

            Intent intentAddNewCategory = new Intent(context, NewEventCategory.class);
            context.startActivity(intentAddNewCategory);

        } else if (id == R.id.optionViewEvents) {

            Intent intentEventList = new Intent(context, ListEvent.class);
            context.startActivity(intentEventList);

        } else if (id == R.id.optionLogout) {
            Intent intentLogout = new Intent(context, LoginPage.class);
            intentLogout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intentLogout);
        }
        // close the drawer
        drawerlayout.closeDrawers();
        // tell the OS
        return true;
    }


}