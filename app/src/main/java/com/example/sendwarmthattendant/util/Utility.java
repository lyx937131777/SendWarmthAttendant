package com.example.sendwarmthattendant.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.sendwarmthattendant.db.Customer;
import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.db.ServiceSubject;
import com.example.sendwarmthattendant.db.Worker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Utility
{
    public static final String ERROR_CODE = "-1";
    public static final String TRUE_CODE = "000";
    public static final String CODE_500 = "500";

    //检查responseData的code是否为000，若不是则返回问题所在
    public static String checkResponse(String response){
        final String code = checkString(response,"code");
        if (code == null){
            return "后台code为null";
        }
        if(code.equals(TRUE_CODE)){
            return TRUE_CODE;
        }
        if(code.equals(ERROR_CODE)){
            return "数据返回格式有误";
        }
        if(code.equals(CODE_500)){
            String msg = checkString(response,"msg");
            if(msg == null){
                return "code:500,msg为null";
            }
            if(msg.equals(ERROR_CODE)){
                return "code:500,msg解析错误";
            }
            return "code:500, msg:" + msg;
        }
        return "code:" + code;
    }

    //返回responseData的code是否为000，若不是则Toast接口名称和问题所在
    public static boolean checkResponse(String response, final Context context, final String address){
        final String result = checkResponse(response);
        final String interfaceName = address.split(HttpUtil.LocalAddress)[1];
        if(result.equals(TRUE_CODE)){
            return true;
        }
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, interfaceName +":\n" + result, Toast.LENGTH_LONG).show();
            }
        });
        LogUtil.e("Utility","interfaceName: " + interfaceName);
        LogUtil.e("Utility","result: " + result);
        LogUtil.e("Utility","response:" + response);
        return false;
    }

    //返回Json数据的特定string值
    public static String checkString(String response, String string)
    {
        try
        {
            JSONObject dataObject = new JSONObject(response);
            return dataObject.getString(string);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    //返回data数据中的特定String值
    public static String checkDataString(String response, String string){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray dataArray = jsonObject.getJSONArray("datas");
            JSONObject dataObject = dataArray.getJSONObject(0);
            return dataObject.getString(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }


    //登录界面 获取角色
    public static String getRole(String response){
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray dataArray = jsonObject.getJSONArray("datas");
            JSONObject dataObject = dataArray.getJSONObject(0);
            return dataObject.getString("roleType");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    //登录界面 老人
    public static Customer handleCustomer(String response){
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.getJSONArray("datas");
                JSONObject dataObject = dataArray.getJSONObject(0);
                JSONObject customerObject = dataObject.getJSONObject("customerInfo");
                String jsonString = customerObject.toString();
                return  new Gson().fromJson(jsonString, Customer.class);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    //登录界面 助老员
    public static Helper handleHelper(String response){
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.getJSONArray("datas");
                JSONObject dataObject = dataArray.getJSONObject(0);
                JSONObject customerObject = dataObject.getJSONObject("helperInfo");
                String jsonString = customerObject.toString();
                return  new Gson().fromJson(jsonString, Helper.class);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    //登录界面 护理员 or 店长
    public static Worker handleWorker(String response){
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.getJSONArray("datas");
                JSONObject dataObject = dataArray.getJSONObject(0);
                JSONObject customerObject = dataObject.getJSONObject("workerInfo");
                String jsonString = customerObject.toString();
                return  new Gson().fromJson(jsonString, Worker.class);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    //注册界面 服务子类
    public static List<ServiceSubject> handleServiceSubjectList(String response){
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.getJSONArray("datas");
                JSONObject dataObject = dataArray.getJSONObject(0);
                JSONArray jsonArray = dataObject.getJSONArray("content");
                String serviceSubjectJson = jsonArray.toString();
                return new Gson().fromJson(serviceSubjectJson, new TypeToken<List<ServiceSubject>>() {}.getType());
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    //主界面  订单列表
    public static List<Order> handleOrderList(String response){
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.getJSONArray("datas");
                JSONObject dataObject = dataArray.getJSONObject(0);
                JSONArray jsonArray = dataObject.getJSONArray("content");
                String orderJson = jsonArray.toString();
                return new Gson().fromJson(orderJson, new TypeToken<List<Order>>() {}.getType());
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

}
