package com.example.sendwarmthattendant.util;

import com.example.sendwarmthattendant.R;

import java.util.HashMap;
import java.util.Map;

public class MapUtil
{
    private static Map<String,Integer> stateMap =new HashMap<>();
    private static Map<String,String> orderStateMap = new HashMap<>();
    private static Map<String,String> orderStateTitleMap = new HashMap<>();
    private static Map<String,String> orderType = new HashMap<>();

    static {
        //
        stateMap.put("running", R.drawable.state_yellow);
        stateMap.put("unstart", R.drawable.state_blue);
        stateMap.put("canceled",R.drawable.state_red);
        stateMap.put("completed",R.drawable.state_green);
        stateMap.put("moving",R.drawable.state_yellow);
        stateMap.put("arrived",R.drawable.state_yellow);

        orderStateMap.put("running","进行中");
        orderStateMap.put("unstart","未开始");
        orderStateMap.put("canceled","已取消");
        orderStateMap.put("completed","已完成");
        orderStateMap.put("waiting","等待中");
        orderStateMap.put("moving","未到达");
        orderStateMap.put("arrived","已到达");
        //

        stateMap.put("on_going", R.drawable.state_yellow);
        stateMap.put("not_start", R.drawable.state_blue);
        stateMap.put("canceled",R.drawable.state_red);
        stateMap.put("completed",R.drawable.state_green);
//        stateMap.put("not_comment",R.drawable.state_yellow);
        stateMap.put("not_accepted",R.drawable.state_green);

        orderStateMap.put("on_going","进行中");
        orderStateMap.put("not_start","未开始");
        orderStateMap.put("canceled","已取消");
        orderStateMap.put("completed","已完成");
//        orderStateMap.put("moving","未到达");
        orderStateMap.put("not_accepted","未接单");


        orderStateTitleMap.put("on_going","当前任务");
        orderStateTitleMap.put("not_start","待解决任务");
        orderStateTitleMap.put("canceled","已取消任务");
        orderStateTitleMap.put("completed","已完成任务");
        orderStateTitleMap.put("mine","我的订单");
        orderStateTitleMap.put("not_accepted","可接的订单");

        orderType.put("预约订单","book_order");
        orderType.put("加急订单","expedited_order");
        orderType.put("book_order","预约订单");
        orderType.put("expedited_order","加急订单");
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

    public static String getOrderType(String s){
        return orderType.get(s);
    }
}
