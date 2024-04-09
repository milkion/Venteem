package com.fit2081.assignment1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Inherited;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;



public class FragmentListCategory extends Fragment {

    private ArrayList<EventCategory> listCategory = new ArrayList<>();

    private RecyclerView recyclerView;
    private CategoryRecyclerAdapter categoryRecyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        recyclerView = view.findViewById(R.id.eventCategoryRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        listCategory = getEventCategoryFromSharedPreference();

        categoryRecyclerAdapter = new CategoryRecyclerAdapter();
        categoryRecyclerAdapter.setCategory(listCategory);
        recyclerView.setAdapter(categoryRecyclerAdapter);

        return view;
    }

    public ArrayList<EventCategory> getEventCategoryFromSharedPreference(){

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("UNIQUE_FILE_NAME", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("KEY_CATEGORY", "[]");
        Type type = new TypeToken<ArrayList<EventCategory>>() {}.getType();
        ArrayList<EventCategory> eventCategoryList = new Gson().fromJson(json, type);


        return eventCategoryList;
    }
}