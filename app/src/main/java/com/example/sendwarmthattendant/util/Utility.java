package com.example.sendwarmthattendant.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utility
{
    public static final String ERROR_CODE = "-1";

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
