package com.example.sendwarmthattendant.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.sendwarmthattendant.MainActivity;
import com.example.sendwarmthattendant.adapter.OrderStateAdapter;
import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.db.Worker;
import com.example.sendwarmthattendant.util.CheckUtil;
import com.example.sendwarmthattendant.util.HttpUtil;
import com.example.sendwarmthattendant.util.LogUtil;
import com.example.sendwarmthattendant.util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomePresenter
{
    private Context context;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;

    public HomePresenter(Context context, SharedPreferences pref) {
        this.context = context;
        this.pref = pref;
    }

    public void updateOrderList(final OrderStateAdapter orderStateAdapter){
        String address = HttpUtil.LocalAddress + "/api/order";
        final String role = pref.getString("role","");
        final String credential = pref.getString("credential","");
        if(role.equals("helper")){
            Helper helper = LitePal.where("credential = ?",credential).findFirst(Helper.class);
            address = address + "/helper/list?helpId="+helper.getInternetId();
        }else {
            Worker worker = LitePal.where("credential = ?",credential).findFirst(Worker.class);
            address = address + "/worker/list?workerId="+worker.getInternetId();
        }
        HttpUtil.getHttp(address, credential, new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "网络连接错误", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String responsData = response.body().string();
                LogUtil.e("HomePresenter",responsData);
                if(Utility.checkString(responsData,"code") != null && Utility.checkString(responsData,"code").equals("000")){
                    List<Order> orderList = Utility.handleOrderList(responsData);
                    orderStateAdapter.setOrderList(orderList);
                    String address = HttpUtil.LocalAddress + "/api/order/unAccept?roleType="+role;
                    if(role.equals("helper")){
                        Helper helper = LitePal.where("credential = ?",credential).findFirst(Helper.class);
                        address = address + "&id=" + helper.getInternetId();
                    }else {
                        Worker worker = LitePal.where("credential = ?",credential).findFirst(Worker.class);
                        address = address + "&id=" + worker.getInternetId();
                    }
                    HttpUtil.getHttp(address, credential, new Callback()
                    {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e)
                        {
                            ((MainActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "网络连接错误", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                        {
                            String responsData = response.body().string();
                            LogUtil.e("HomePresenter",responsData);
                            if(Utility.checkString(responsData,"code") != null && Utility.checkString(responsData,"code").equals("000")){
                                List<Order> unAcceptedOrderList = Utility.handleOrderList(responsData);
                                List<Order> orderList = orderStateAdapter.getOrderList();
                                if(unAcceptedOrderList != null){
                                    orderList.addAll(unAcceptedOrderList);
                                }
                                ((MainActivity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        orderStateAdapter.notifyDataSetChanged();
                                    }
                                });
                            }else {
                                ((MainActivity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "数据传输错误", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });

                }else {
                    ((MainActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "数据传输错误", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
