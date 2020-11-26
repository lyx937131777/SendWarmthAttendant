package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.OrderDetailActivity;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.util.MapUtil;
import com.example.sendwarmthattendant.util.TimeUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>
{
    private Context mContext;
    private List<Order> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        ImageView state;
        TextView stateText;
        TextView number;
        TextView customer;
        TextView startTime;
        TextView endTime;
        TextView serviceContent;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            state = view.findViewById(R.id.state);
            stateText = view.findViewById(R.id.state_text);
            number = view.findViewById(R.id.number);
            customer = view.findViewById(R.id.customer);
            startTime = view.findViewById(R.id.start_time);
            endTime = view.findViewById(R.id.end_time);
            serviceContent = view.findViewById(R.id.service_content);
        }
    }

    public OrderAdapter(List<Order> orderList)
    {
        mList = orderList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order,parent,false);
        final OrderAdapter.ViewHolder holder = new OrderAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = holder.getAdapterPosition();
                Order order = mList.get(position);
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("order", order);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position)
    {
        Order order = mList.get(position);
        holder.number.setText(order.getOrderNo());
        holder.customer.setText(order.getCustomerInfo().getName());
        Glide.with(mContext).load(MapUtil.getState(order.getState())).into(holder.state);
        holder.stateText.setText(MapUtil.getOrderState(order.getState()));
        if(order.getState().equals("not_start") || order.getState().equals("not_accepted")){
            holder.startTime.setText("预计上门："+TimeUtil.timeStampToString(order.getExpectStartTime(),"yyyy-MM-dd HH:mm"));
            holder.endTime.setText("预计结束：" + TimeUtil.timeStampToString(order.getExpectEndTime(),"yyyy-MM-dd HH:mm"));
        }else if(order.getState().equals("on_going")){
            holder.startTime.setText("上门时间："+TimeUtil.timeStampToString(order.getStartTime(),"yyyy-MM-dd HH:mm"));
            holder.endTime.setText("预计结束：" + TimeUtil.timeStampToString(order.getExpectEndTime(),"yyyy-MM-dd HH:mm"));
        }else if(order.getState().equals("canceled")){
            holder.startTime.setText("");
            holder.endTime.setText("");
        }else {
            holder.startTime.setText("上门时间："+TimeUtil.timeStampToString(order.getStartTime(),"yyyy-MM-dd HH:mm"));
            holder.endTime.setText("结束时间：" + TimeUtil.timeStampToString(order.getEndTime(),"yyyy-MM-dd HH:mm") );
        }
        holder.serviceContent.setText(order.getServiceSubjectInfo().getName());
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    public List<Order> getmList()
    {
        return mList;
    }

    public void setmList(List<Order> mList)
    {
        this.mList = mList;
    }
}
