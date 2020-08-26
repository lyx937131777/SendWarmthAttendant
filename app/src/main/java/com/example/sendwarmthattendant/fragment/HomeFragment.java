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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment
{
    private RecyclerView recyclerView;
    private String[] orderStates = {"mine"};
    private List<String> orderStateList = new ArrayList<>();
    private OrderStateAdapter orderStateAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initOrderStates();
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderStateAdapter = new OrderStateAdapter(orderStateList);
        recyclerView.setAdapter(orderStateAdapter);
        return root;
    }


    private void initOrderStates()
    {
        orderStateList.clear();
        for(int i = 0; i < orderStates.length; i++){
            orderStateList.add(orderStates[i]);
        }
    }

}