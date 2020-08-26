package com.example.sendwarmthattendant.util;

import com.example.sendwarmthattendant.R;

import java.util.HashMap;
import java.util.Map;

public class MapUtil
{
    private static Map<String,Integer> stateMap =new HashMap<>();
    private static Map<String,String> orderStateMap = new HashMap<>();
    private static Map<String,String> orderStateTitleMap = new HashMap<>();

    static {
        stateMap.put("running", R.drawable.state_yellow);
        stateMap.put("unstart", R.drawable.state_blue);
        stateMap.put("canceled",R.drawable.state_red);
        stateMap.put("completed",R.drawable.state_green);

        orderStateMap.put("running","进行中");
        orderStateMap.put("unstart","未开始");
        orderStateMap.put("canceled","已取消");
        orderStateMap.put("completed","已完成");
        orderStateMap.put("waiting","等待中");


        orderStateTitleMap.put("running","当前任务");
        orderStateTitleMap.put("unstart","待解决任务");
        orderStateTitleMap.put("canceled","已取消任务");
        orderStateTitleMap.put("completed","已完成任务");
        orderStateTitleMap.put("mine","我的订单");
    }

    public static int getState(String s){
        return stateMap.get(s);
    }

    public static String getOrderState(String s){
        return orderStateMap.get(s);
    }

    public static String getOrderStateTitle(String s){
        return orderStateTitleMap.get(s);
    }
}
