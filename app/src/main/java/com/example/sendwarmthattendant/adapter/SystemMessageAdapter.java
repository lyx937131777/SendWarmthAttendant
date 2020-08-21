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
import com.example.sendwarmthattendant.TaskDetailActivity;
import com.example.sendwarmthattendant.db.SystemMessage;
import com.example.sendwarmthattendant.db.Task;
import com.example.sendwarmthattendant.util.MapUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SystemMessageAdapter extends RecyclerView.Adapter<SystemMessageAdapter.ViewHolder>
{
    private Context mContext;
    private List<SystemMessage> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        TextView time;
        TextView date;
        TextView content;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            time = view.findViewById(R.id.time);
            date = view.findViewById(R.id.date);
            content = view.findViewById(R.id.content);
        }
    }

    public SystemMessageAdapter(List<SystemMessage> systemMessageList)
    {
        mList = systemMessageList;
    }

    @NonNull
    @Override
    public SystemMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_system_message,parent,false);
        final SystemMessageAdapter.ViewHolder holder = new SystemMessageAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SystemMessageAdapter.ViewHolder holder, int position)
    {
        SystemMessage systemMessage = mList.get(position);
        holder.time.setText(systemMessage.getTime());
        holder.date.setText(systemMessage.getDate());
        holder.content.setText(systemMessage.getContent());
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
