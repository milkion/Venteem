package com.fit2081.assignment1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.assignment1.provider.EventCategoryViewModel;
import com.google.gson.Gson;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Inherited;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;



public class FragmentListCategory extends Fragment {

    private RecyclerView recyclerView;

    private EventCategoryViewModel eventCategoryViewModel;

    private CategoryRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        recyclerView = view.findViewById(R.id.eventCategoryRecycler);
        adapter = new CategoryRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        eventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);
        eventCategoryViewModel.getAllEventCategory().observe(getViewLifecycleOwner(), newData -> {
            adapter.setCategory(new ArrayList<EventCategory>(newData));
            adapter.notifyDataSetChanged();
        });

        return view;
    }

}