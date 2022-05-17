package com.example.main_fitness_app;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Breakfast extends Fragment {
    public static final String One ="Breakfast";
    private RecyclerView recyclerView ;
    HashMap<String, Integer> calories = new HashMap<>();
    List<String> foodname = new ArrayList<>(),
            serving= new ArrayList<>();
    AssetManager fileeassetManager ;
    private TextView total;
    public static Breakfast getInstance(){
        return new Breakfast();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breakfast, container, false);
        recyclerView = view.findViewById(R.id.breakfastrv);
        fileAdapter adapter = new fileAdapter(getContext(),foodname,calories,serving,1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileeassetManager  = getContext().getAssets();
        foodmain.showdata(1,fileeassetManager,foodname,calories,serving );

    }


    public TextView getTotal() {
        return total;
    }

    public void setTotal(TextView total) {
        this.total = total;
    }
}
