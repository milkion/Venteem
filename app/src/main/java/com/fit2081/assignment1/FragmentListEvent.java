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

import com.fit2081.assignment1.provider.EventViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class FragmentListEvent extends Fragment {

    private ArrayList<Event> listEvent = new ArrayList<>();

    private RecyclerView recyclerView;

    private EventViewModel eventViewModel;
    private EventRecyclerAdapter eventRecyclerAdapter;

    private EventRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_event, container, false);

        recyclerView = view.findViewById(R.id.eventRecycler);
        adapter = new EventRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), newData -> {
            adapter.setEvent(new ArrayList<Event>(newData));
            adapter.notifyDataSetChanged();
        });

        return view;
    }

}