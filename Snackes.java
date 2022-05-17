package com.example.main_fitness_app;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Snackes extends Fragment {
    public static final String Two ="Snackes";
    public static final String Four ="Dinner";
    private RecyclerView recyclerView ;
    HashMap<String, Integer> calories = new HashMap<>();
    List<String> foodname = new ArrayList<>(),
            serving= new ArrayList<>();
    AssetManager fileeassetManager ;
    public static Snackes getInstance(){
        return new Snackes();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snackes, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.snackrc);
        fileAdapter adapter = new fileAdapter(getContext(),foodname,calories,serving,0);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileeassetManager  = getContext().getAssets();
        foodmain.showdata(0,fileeassetManager,foodname,calories,serving );

    }

}
