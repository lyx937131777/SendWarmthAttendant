package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.OrderStateAdapter;
import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment
{
    private RecyclerView recyclerView;
    private String[] orderStates = {"mine","not_accepted"};
    private List<String> orderStateList = new ArrayList<>();
    private OrderStateAdapter orderStateAdapter;

    private HomePresenter homePresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(getContext())).build();
        homePresenter = myComponent.homePresenter();

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

    @Override
    public void onStart()
    {
        super.onStart();
        homePresenter.updateOrderList(orderStateAdapter);
    }
}