package com.example.sendwarmthattendant.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.OrderStateAdapter;
import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.db.Worker;
import com.example.sendwarmthattendant.util.HttpUtil;
import com.example.sendwarmthattendant.util.LogUtil;
import com.example.sendwarmthattendant.util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MapPresenter
{
    private Context context;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;

    public MapPresenter(Context context, SharedPreferences pref) {
        this.context = context;
        this.pref = pref;
    }

    public void updateOrderList(final List<Order> orderList, final List<Marker> markerList,  final BaiduMap baiduMap, final String type){
        LogUtil.e("MapPresenter","startUpdateOrderList");
        String address = HttpUtil.LocalAddress + "/api/order";
        final String role = pref.getString("role","");
        final String credential = pref.getString("credential","");
        if(role.equals("helper")){
            Helper helper = LitePal.where("credential = ?",credential).findFirst(Helper.class);
            address = address + "/helper/list?helperId="+helper.getInternetId();
        }else {
            Worker worker = LitePal.where("credential = ?",credential).findFirst(Worker.class);
            address = address + "/worker/list?workerId="+worker.getInternetId();
        }
        final String finalAddress = address;
        HttpUtil.getHttp(address, credential, new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "网络连接错误", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String responseData = response.body().string();
                LogUtil.e("MapPresenter",responseData);
                if(Utility.checkResponse(responseData,context, finalAddress)){
                    List<Order> myOrderList = Utility.handleOrderList(responseData);
                    if(myOrderList != null){
                        orderList.addAll(myOrderList);
                    }
                    String address = HttpUtil.LocalAddress + "/api/order/unAccept?roleType="+role;
                    if(role.equals("helper")){
                        Helper helper = LitePal.where("credential = ?",credential).findFirst(Helper.class);
                        address = address + "&id=" + helper.getInternetId();
                    }else {
                        Worker worker = LitePal.where("credential = ?",credential).findFirst(Worker.class);
                        address = address + "&id=" + worker.getInternetId();
                    }
                    final String finalAddress1 = address;
                    HttpUtil.getHttp(address, credential, new Callback()
                    {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e)
                        {
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "网络连接错误", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                        {
                            String responseData = response.body().string();
                            LogUtil.e("MapPresenter",responseData);
                            if(Utility.checkResponse(responseData,context, finalAddress1)){
                                List<Order> unAcceptedOrderList = Utility.handleOrderList(responseData);
                                if(unAcceptedOrderList != null){
                                    orderList.addAll(unAcceptedOrderList);
                                }
                                LogUtil.e("MapPresenter","获得数据");
                                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for(Order order: orderList){
                                            LatLng ll = new LatLng(order.getLatitude(), order.getLongitude());
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("order",order);
                                            if(order.getState().equals("not_accepted") && type.equals("all")){
                                                MarkerOptions options = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_red)).extraInfo(bundle);
                                                Marker marker = (Marker)baiduMap.addOverlay(options);//TODO 注释123-124，128-129，133-134
                                                markerList.add(marker);
//                                                order.setMarker(marker);不要取消注释
                                            }else if(order.getState().equals("not_start")){
                                                MarkerOptions options = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_primary)).extraInfo(bundle);
                                                Marker marker = (Marker)baiduMap.addOverlay(options);
                                                markerList.add(marker);
//                                                order.setMarker(marker);不要取消注释
                                            }else if(order.getState().equals("on_going")){
                                                MarkerOptions options = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromResource(R.drawable.location_blue)).extraInfo(bundle);
                                                Marker marker = (Marker)baiduMap.addOverlay(options);
                                                markerList.add(marker);
//                                                order.setMarker(marker);不要取消注释
                                            }
                                        }
                                        LogUtil.e("MapPresenter","endUpdateOrderList");
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}
