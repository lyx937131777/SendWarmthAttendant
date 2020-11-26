package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.OrderAdapter;
import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.presenter.OrderPresenter;

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
    private List<Order> orderList = new ArrayList<>();
    private OrderAdapter orderAdapter;

    private OrderPresenter orderPresenter;

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
        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(getContext())).build();
        orderPresenter = myComponent.orderPresenter();

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
    }

    @Override
    public void onStart()
    {
        super.onStart();
        orderPresenter.updateOrderList(orderAdapter,getType());
    }

    private String getType(){
        switch (index){
            case 0:
                return "all";
            case 1:
                return "on_going";
            case 2:
                return "not_start";
            case 3:
                return "un_evaluated";
            case 4:
                return "completed";
            default:
                return "unknown";
        }
    }

    private String[] getTypes(){
        switch (index){
            case 0:
                return new String[]{"on_going","not_start","canceled","completed"};
            case 1:
                return new String[]{"on_going"};
            case 2:
                return new String[]{"not_start"};
            case 3:
                return new String[]{"canceled"};
            case 4:
                return new String[]{"completed"};
            default:
                return new String[]{};
        }
    }

}
