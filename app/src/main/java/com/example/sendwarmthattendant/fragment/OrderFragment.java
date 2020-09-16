package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.OrderAdapter;
import com.example.sendwarmthattendant.db.Order;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int index;

    private RecyclerView recyclerView;
    private Order[] orders = {new Order("001","张三","","2020-08-07 10:00~14:00","moving","","理发",50),
            new Order("002","周五","","2020-08-07 12:00~14:00","arrived","","护理",50),
            new Order("003","李四","","2020-08-07 13:00~15:00","unstart","","修锁",20),
            new Order("004","小红","","2020-08-07 12:00~14:00","unstart","","打扫卫生",50),
            new Order("005","小明","","2020-08-07 11:00~12:00","unstart","","按摩",30),
            new Order("006","小王","","2020-08-06 15:00~16:00","canceled","","陪聊",50),
            new Order("007","小白","","2020-08-06 13:00~15:00","canceled","","修空调",60),
            new Order("008","小李","","2020-08-07 12:00~14:00","canceled","","修锁",20),
            new Order("009","小许","","2020-08-06 15:00~16:00","completed","","裁缝",30),
            new Order("010","小徐","","2020-08-06 14:00~15:00","completed","","打扫卫生",50),
            new Order("011","小黑","","2020-08-06 11:00~13:00","completed","","理发",60)};
    private List<Order> orderList = new ArrayList<>();
    private OrderAdapter orderAdapter;


    public static OrderFragment newInstance(int index)
    {
        OrderFragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER,index);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        index = 0;
        if (getArguments() != null)
        {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        initOrders();
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);
        return root;
    }

    private void initOrders()
    {
        String[] types = getTypes();
        List<String> typeList = new ArrayList<>();
        for(int i = 0; i < types.length; i++){
            typeList.add(types[i]);
        }
        for(int i = 0; i < orders.length; i++){
            if(typeList.contains(orders[i].getState())){
                orderList.add(orders[i]);
            }
        }
    }

    private String getType(){
        switch (index){
            case 0:
                return "all";
            case 1:
                return "running";
            case 2:
                return "unstart";
            case 3:
                return "canceled";
            case 4:
                return "completed";
            default:
                return "unknown";
        }
    }

    private String[] getTypes(){
        switch (index){
            case 0:
                return new String[]{"moving","arrived","unstart","canceled","completed"};
            case 1:
                return new String[]{"moving","arrived"};
            case 2:
                return new String[]{"unstarted"};
            case 3:
                return new String[]{"canceled"};
            case 4:
                return new String[]{"completed"};
            default:
                return new String[]{};
        }
    }

}
