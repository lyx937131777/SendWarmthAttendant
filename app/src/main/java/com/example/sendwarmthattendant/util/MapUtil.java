package com.example.sendwarmthattendant.util;

import com.example.sendwarmthattendant.R;

import java.util.HashMap;
import java.util.Map;

public class MapUtil
{
    private static Map<String,Integer> stateMap =new HashMap<>();
    private static Map<String,String> taskTypeStateMap = new HashMap<>();
    private static Map<String,String> taskTypeMap = new HashMap<>();

    static {
        stateMap.put("running", R.drawable.state_yellow);
        stateMap.put("unstart", R.drawable.state_blue);
        stateMap.put("completed",R.drawable.state_green);

        taskTypeStateMap.put("running","进行中");
        taskTypeStateMap.put("unstart","未开始");
        taskTypeStateMap.put("completed","已完成");

        taskTypeMap.put("running","当前任务");
        taskTypeMap.put("unstart","待解决任务");
        taskTypeMap.put("completed","已完成任务");
    }

    public static int getState(String s){
        return stateMap.get(s);
    }

    public static String getTaskTypeState(String s){
        return taskTypeStateMap.get(s);
    }

    public static String getTaskType(String s){
        return taskTypeMap.get(s);
    }
}
