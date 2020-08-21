package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.TaskTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoricalOrdersFragment extends Fragment
{
    private RecyclerView recyclerView;
    private String[] taskTypes = {"running","unstart","canceled","completed"};
    private List<String> taskTypeList = new ArrayList<>();
    private TaskTypeAdapter taskTypeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_historical_orders, container, false);
        initTaskTypes();
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskTypeAdapter = new TaskTypeAdapter(taskTypeList);
        recyclerView.setAdapter(taskTypeAdapter);
        return root;
    }

    private void initTaskTypes()
    {
        taskTypeList.clear();
        for(int i = 0; i < taskTypes.length; i++){
            taskTypeList.add(taskTypes[i]);
        }
    }
}
