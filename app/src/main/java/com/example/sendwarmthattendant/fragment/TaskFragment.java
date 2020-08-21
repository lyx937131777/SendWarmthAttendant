package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.TaskAdapter;
import com.example.sendwarmthattendant.db.Task;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int index;

    private RecyclerView recyclerView;
    private Task[]  tasks = {new Task("001","张三","2020-08-07 10:00~14:00","running","理发"),
            new Task("002","周五","2020-08-07 12:00~14:00","running","护理"),
            new Task("003","李四","2020-08-07 13:00~15:00","unstart","修锁"),
            new Task("004","小红","2020-08-07 12:00~14:00","unstart","打扫卫生"),
            new Task("005","小明","2020-08-07 11:00~12:00","unstart","按摩"),
            new Task("006","小王","2020-08-06 15:00~16:00","canceled","陪聊"),
            new Task("007","小白","2020-08-06 13:00~15:00","canceled","修空调"),
            new Task("008","小李","2020-08-07 12:00~14:00","canceled","修锁"),
            new Task("009","小许","2020-08-06 15:00~16:00","completed","裁缝"),
            new Task("010","小徐","2020-08-06 14:00~15:00","completed","打扫卫生"),
            new Task("011","小黑","2020-08-06 11:00~13:00","completed","理发")};
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter taskAdapter;


    public static TaskFragment newInstance(int index)
    {
        TaskFragment taskFragment = new TaskFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER,index);
        taskFragment.setArguments(bundle);
        return taskFragment;
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
        View root = inflater.inflate(R.layout.fragment_task, container, false);

        initTasks();
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);
        return root;
    }

    private void initTasks()
    {
        String type = getType();
        if(type == "all"){
            for(int i = 0; i < tasks.length; i++){
                taskList.add(tasks[i]);
            }
        }else{
            for(int i = 0; i < tasks.length; i++){
                if(type.equals(tasks[i].getType())){
                    taskList.add(tasks[i]);
                }
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
        }
        return "unknown";
    }

}
