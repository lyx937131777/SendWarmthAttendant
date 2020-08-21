package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.Task;
import com.example.sendwarmthattendant.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskTypeAdapter extends RecyclerView.Adapter<TaskTypeAdapter.ViewHolder>
{
    private Context mContext;
    private List<String> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        TextView taskType;
        RecyclerView recyclerView;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            taskType = view.findViewById(R.id.task_type);
            recyclerView = view.findViewById(R.id.recycler_goods);
        }
    }

    public TaskTypeAdapter(List<String> menuList){
        mList = menuList;
    }

    @Override
    public TaskTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task_type, parent,false);
        final TaskTypeAdapter.ViewHolder holder = new TaskTypeAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TaskTypeAdapter.ViewHolder holder, int position)
    {
        String taskType = mList.get(position);
        holder.taskType.setText(MapUtil.getTaskType(taskType));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        Task[] tasks = {new Task("001","张三","2020-08-07 10:00~14:00",taskType,"理发"),
                new Task("002","李四","2020-08-07 13:00~15:00",taskType,"修锁"),
                new Task("003","周五","2020-08-07 12:00~14:00",taskType,"打扫卫生")};
        List<Task> taskList = new ArrayList<>();
        if(taskType.equals("mine")){
            Task[] tasks2 = {new Task("001","张三","2020-08-07 10:00~14:00","running","理发"),
                    new Task("002","李四","2020-08-07 13:00~15:00","unstart","修锁"),
                    new Task("003","周五","2020-08-07 12:00~14:00","unstart","打扫卫生")};
            for(int i = 0; i <tasks2.length; i++)
                taskList.add(tasks2[i]);
        }else{
            for(int i = 0; i <tasks.length; i++)
                taskList.add(tasks[i]);
        }
        TaskAdapter taskAdapter = new TaskAdapter(taskList);
        holder.recyclerView.setAdapter(taskAdapter);
    }
    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
