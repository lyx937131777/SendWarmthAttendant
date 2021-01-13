package com.example.sendwarmthattendant.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.sendwarmthattendant.adapter.OrderAdapter;
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
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderPresenter
{
    private Context context;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;

    public OrderPresenter(Context context, SharedPreferences pref) {
        this.context = context;
        this.pref = pref;
    }

    public void updateOrderList(final OrderAdapter orderAdapter, final String type){
        String address = HttpUtil.LocalAddress + "/api/order";
        String role = pref.getString("role","");
        String credential = pref.getString("credential","");
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
                final String responseData = response.body().string();
                LogUtil.e("OrderPresenter",responseData);
                if(Utility.checkResponse(responseData,context, finalAddress)){
                    List<Order> orderList = Utility.handleOrderList(responseData);
                    if(type.equals("all")) {
                        orderAdapter.setmList(orderList);
                    }else {
                        List<Order> typeOrderList = new ArrayList<>();
                        for(Order order : orderList){
                            if(order.getState().equals(type)){
                                typeOrderList.add(order);
                            }
                        }
                        orderAdapter.setmList(typeOrderList);
                    }
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            orderAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
