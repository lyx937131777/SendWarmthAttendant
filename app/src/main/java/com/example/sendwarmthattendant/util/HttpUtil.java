package com.example.sendwarmthattendant.util;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil
{
    //正式
    public static final String LocalAddress = "http://47.101.68.214:8999";

    public static String getResourceURL(String url){
        return LocalAddress + "/resources/" + url;
    }

    //get 登录界面 获取列表
    public static void getHttp(String address,String credential, Callback callback)
    {
//        OkHttpClient client = buildBasicAuthClient(userID,"123456");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).addHeader("Authorization",credential).build();
        LogUtil.e("Login","111111111   " + request.header("Authorization"));
        client.newCall(request).enqueue(callback);
    }

    //get 注册界面
    public static void getHttpWithoutCredential(String address, Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    //登录
    public static void loginRequest(String address, String tel, String password, Callback callback){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        HashMap<String, String> map = new HashMap<>();
        map.put("username",tel);
        map.put("password",password);
        String jsonStr = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(jsonStr, JSON);
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    //注册
    public static void registerRequest(String address, String tel, String password, String name, int workType1, int workType2, String id, String idCardFront, String idCardBack, Callback callback){
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        HashMap<String, String> helperMap = new HashMap<>();
        helperMap.put("helperTel",tel);
        helperMap.put("workClass1",""+workType1);
        helperMap.put("workClass2",""+workType2);
        helperMap.put("helperIdCard",id);
        helperMap.put("helperName",name);
        helperMap.put("idCardFront",idCardFront);
        helperMap.put("idCardBack",idCardBack);
        String helperInfo = new Gson().toJson(helperMap);
        HashMap<String, String> map = new HashMap<>();
        map.put("loginName",tel);
        map.put("password",password);
        map.put("roleType","helper");
        String jsonStr = new Gson().toJson(map);
        LogUtil.e("HttpUtil","address: "+ address);
        LogUtil.e("HttpUtil","helperInfo: "+ helperInfo);
        jsonStr = jsonStr.split("\\}")[0] + ",\"helperInfo\":"+helperInfo+"}";
        LogUtil.e("HttpUtil","registerRequest: "+jsonStr);
        RequestBody requestBody = RequestBody.create(jsonStr, JSON);
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    //上传照片
    public static void fileRequest(String address, File file, okhttp3.Callback callback)
    {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        MediaType fileType = MediaType.parse("image/jpeg");//数据类型为File格式，
        RequestBody fileBody = RequestBody.create(file, fileType);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), fileBody)
                .build();
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    //接单
    public static void acceptOrderRequest(String address, String credential, String orderId, String role, String manId,Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("orderId", orderId)
                .add("roleType",role)
                .add("manId",manId)
                .build();
        Request request = new Request.Builder().url(address).put(requestBody).addHeader("Authorization",credential).build();
        client.newCall(request).enqueue(callback);
    }

    //开始订单
    public static void startOrderRequest(String address, String credential, String orderId,Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("orderId", orderId)
                .build();
        Request request = new Request.Builder().url(address).put(requestBody).addHeader("Authorization",credential).build();
        client.newCall(request).enqueue(callback);
    }

    //结束订单
    public static void endOrderRequest(String address, String credential, String orderId,Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("orderId", orderId)
                .build();
        Request request = new Request.Builder().url(address).put(requestBody).addHeader("Authorization",credential).build();
        client.newCall(request).enqueue(callback);
    }

    //评价订单
    public static void commentOrderRequest(String address, String credential, String orderId, String comment,Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("orderId", orderId)
                .add("workerDes",comment)
                .build();
        Request request = new Request.Builder().url(address).put(requestBody).addHeader("Authorization",credential).build();
        client.newCall(request).enqueue(callback);
    }
}
