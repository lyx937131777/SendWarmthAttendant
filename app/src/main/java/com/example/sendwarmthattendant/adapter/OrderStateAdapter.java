package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderStateAdapter extends RecyclerView.Adapter<OrderStateAdapter.ViewHolder>
{
    private Context mContext;
    private List<String> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        TextView orderStateTitle;
        RecyclerView recyclerView;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            orderStateTitle = view.findViewById(R.id.order_state_title);
            recyclerView = view.findViewById(R.id.recycler_goods);
        }
    }

    public OrderStateAdapter(List<String> menuList){
        mList = menuList;
    }

    @Override
    public OrderStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_state, parent,false);
        final OrderStateAdapter.ViewHolder holder = new OrderStateAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrderStateAdapter.ViewHolder holder, int position)
    {
        String orderState = mList.get(position);
        holder.orderStateTitle.setText(MapUtil.getOrderStateTitle(orderState));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        Order[] orders = {new Order("001","张三","","2020-08-07 10:00~14:00",orderState,"","理发",50),
                new Order("002","李四","","2020-08-07 13:00~15:00",orderState,"","修锁",50),
                new Order("003","周五","","2020-08-07 12:00~14:00",orderState,"","打扫卫生",50)};
        List<Order> orderList = new ArrayList<>();
        if(orderState.equals("mine")){
            Order[] orders2 = {new Order("001","张三","","2020-08-07 10:00~14:00","arrived","","理发",50),
                    new Order("002","小李","","2020-08-07 10:00~14:00","moving","","理发",50),
                    new Order("003","小王","","2020-08-07 13:00~15:00","unstart","","修锁",50),
                    new Order("004","小白","","2020-08-07 12:00~14:00","unstart","","打扫卫生",50)};
            for(int i = 0; i <orders2.length; i++)
                orderList.add(orders2[i]);
        }else if(orderState.equals("running")){
            Order[] orders3 = {new Order("001","张三","","2020-08-07 10:00~14:00","arrived","","理发",50),
                    new Order("002","小李","","2020-08-07 10:00~14:00","moving","","理发",50),
                    new Order("003","小王","","2020-08-07 13:00~15:00","moving","","修锁",50)};
            for(int i = 0; i <orders3.length; i++)
                orderList.add(orders3[i]);
        }else {
            for(int i = 0; i < orders.length; i++)
                orderList.add(orders[i]);
        }
        OrderAdapter orderAdapter = new OrderAdapter(orderList);
        holder.recyclerView.setAdapter(orderAdapter);
    }
    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
