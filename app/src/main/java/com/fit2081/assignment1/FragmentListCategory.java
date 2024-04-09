package com.fit2081.assignment1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Inherited;
import java.lang.reflect.Array;
import java.util.ArrayList;



public class FragmentListCategory extends Fragment {

    private ArrayList<EventCategory> listCategory = new ArrayList<>();

    private RecyclerView recyclerView;
    private CategoryRecyclerAdapter categoryRecyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        return view;
    }
}