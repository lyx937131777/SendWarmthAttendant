package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.util.LogUtil;
import com.example.sendwarmthattendant.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderStateAdapter extends RecyclerView.Adapter<OrderStateAdapter.ViewHolder>
{
    private Context mContext;
    private List<String> mList;
    private List<Order> orderList = new ArrayList<>();

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
        LogUtil.e("OrderStateAdapter","orderState: "+orderState);
        holder.orderStateTitle.setText(MapUtil.getOrderStateTitle(orderState));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        List<Order> mOrderList = new ArrayList<>();
        if(orderState.equals("mine")){
            for(Order order : orderList){
                if(order.getState().equals("on_going") || order.getState().equals("not_start")){
                    mOrderList.add(order);
                }
            }
        }else {
            for(Order order : orderList){
                if(order.getState().equals(orderState)){
                    mOrderList.add(order);
                }
            }
        }
        LogUtil.e("OrderStateAdapter","size: "+mOrderList.size());
        OrderAdapter orderAdapter = new OrderAdapter(mOrderList);
        holder.recyclerView.setAdapter(orderAdapter);
    }
    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    public List<String> getmList()
    {
        return mList;
    }

    public void setmList(List<String> mList)
    {
        this.mList = mList;
    }

    public List<Order> getOrderList()
    {
        return orderList;
    }

    public void setOrderList(List<Order> orderList)
    {
        this.orderList = orderList;
    }
}
