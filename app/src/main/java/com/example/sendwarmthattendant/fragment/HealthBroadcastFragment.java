package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.HealthBroadcastAdapter;
import com.example.sendwarmthattendant.db.HealthBroadcast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HealthBroadcastFragment extends Fragment
{
    private RecyclerView recyclerView;
    private HealthBroadcast[] healthBroadcasts = {new HealthBroadcast("养生播报1",R.drawable.temp,"养生需要注意这几点！第一…………","2020-03-03 11:05"),
            new HealthBroadcast("养生播报2",R.drawable.temp,"养生需要注意这几点！第一…………","2020-03-04 11:55"),
            new HealthBroadcast("养生播报3",R.drawable.temp,"养生需要注意这几点！第一…………","2020-03-15 09:05")};
    private List<HealthBroadcast> healthBroadcastList = new ArrayList<>();
    private HealthBroadcastAdapter healthBroadcastAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_health_broadcast, container, false);
        initHealthBroadcast();
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        healthBroadcastAdapter = new HealthBroadcastAdapter(healthBroadcastList);
        recyclerView.setAdapter(healthBroadcastAdapter);
        return root;
    }

    private void initHealthBroadcast()
    {
        healthBroadcastList.clear();
        for(int i = 0; i < healthBroadcasts.length; i++)
            healthBroadcastList.add(healthBroadcasts[i]);
    }
}
