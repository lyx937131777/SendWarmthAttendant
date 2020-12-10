package com.example.sendwarmthattendant.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.sendwarmthattendant.adapter.OrderAdapter;
import com.example.sendwarmthattendant.db.Order;
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

public class CustomerHistoryOrderPresenter
{
    private Context context;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;

    public CustomerHistoryOrderPresenter(Context context, SharedPreferences pref) {
        this.context = context;
        this.pref = pref;
    }

    public void updateOrderList(final OrderAdapter orderAdapter, String customerId){
        String credential = pref.getString("credential","");
        final String address = HttpUtil.LocalAddress + "/api/order/customer/list?customerId=" + customerId;
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
                final String responsData = response.body().string();
                LogUtil.e("OrderPresenter",responsData);
                if(Utility.checkResponse(responsData,context, address)){
                    List<Order> orderList = Utility.handleOrderList(responsData);
                    List<Order> typeOrderList = new ArrayList<>();
                    for(Order order : orderList){
                        if(order.getState().equals("completed")){
                            typeOrderList.add(order);
                        }
                    }
                    orderAdapter.setmList(typeOrderList);
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
