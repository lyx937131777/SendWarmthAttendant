package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.OrderStateAdapter;

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
    private String[] orderStates = {"running","unstart","canceled","completed"};
    private List<String> orderStateList = new ArrayList<>();
    private OrderStateAdapter orderStateAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_historical_orders, container, false);
        initOrderState();
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderStateAdapter = new OrderStateAdapter(orderStateList);
        recyclerView.setAdapter(orderStateAdapter);
        return root;
    }

    private void initOrderState()
    {
        orderStateList.clear();
        for(int i = 0; i < orderStates.length; i++){
            orderStateList.add(orderStates[i]);
        }
    }
}
