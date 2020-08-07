package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.Task;
import com.example.sendwarmthattendant.util.MapUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>
{
    private Context mContext;
    private List<Task> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        ImageView state;
        TextView stateText;
        TextView number;
        TextView customer;
        TextView time;
        TextView serviceContent;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            state = view.findViewById(R.id.state);
            stateText = view.findViewById(R.id.state_text);
            number = view.findViewById(R.id.number);
            customer = view.findViewById(R.id.customer);
            time = view.findViewById(R.id.time);
            serviceContent = view.findViewById(R.id.service_content);
        }
    }

    public TaskAdapter(List<Task> taskList)
    {
        mList = taskList;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task,parent,false);
        final TaskAdapter.ViewHolder holder = new TaskAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = holder.getAdapterPosition();

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position)
    {
        Task task = mList.get(position);
        holder.number.setText(task.getNumber());
        holder.customer.setText("客户："+task.getCustomer());
        long time = System.currentTimeMillis();
        Glide.with(mContext).load(MapUtil.getState(task.getType())).into(holder.state);
        holder.stateText.setText(MapUtil.getTaskTypeState(task.getType()));
        holder.time.setText("上门时间："+task.getTime());
        holder.serviceContent.setText("服务内容："+task.getServiceContent());
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
