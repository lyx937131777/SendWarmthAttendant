package com.example.sendwarmthattendant.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
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

import androidx.appcompat.app.AppCompatActivity;

public class Utility
{
    public static final String ERROR_CODE = "-1";

    //检查responseData的code是否为000，若不是则Toast问题所在
    public static boolean checkResponse(String response, final Context context){
        final String code = checkString(response,"code");
        if (code == null){
            ((AppCompatActivity)(context)).runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(context, "后台code为null", Toast.LENGTH_LONG).show();
                }
            });
            return false;
        }
        if(code.equals("000")){
            return true;
        }
        if(code.equals(ERROR_CODE)){
            ((AppCompatActivity)(context)).runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(context, "数据返回格式有误", Toast.LENGTH_LONG).show();
                }
            });
            return false;
        }
        if(code.equals("500")){
            final String msg = checkString(response,"msg");
            if(msg == null){
                ((AppCompatActivity)(context)).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(context, "code:500,msg为null", Toast.LENGTH_LONG).show();
                    }
                });
            } else if(msg.equals(ERROR_CODE)){
                ((AppCompatActivity)(context)).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(context, "code:500,msg解析错误", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                ((AppCompatActivity)(context)).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(context, "code:500, msg:" + msg, Toast.LENGTH_LONG).show();
                    }
                });
            }
            return false;
        }
        ((AppCompatActivity)(context)).runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(context, "code:" + code, Toast.LENGTH_LONG).show();
            }
        });
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
