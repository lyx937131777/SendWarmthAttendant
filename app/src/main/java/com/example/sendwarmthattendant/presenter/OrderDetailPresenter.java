package com.example.sendwarmthattendant.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.sendwarmthattendant.MainActivity;
import com.example.sendwarmthattendant.OrderDetailActivity;
import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Worker;
import com.example.sendwarmthattendant.util.HttpUtil;
import com.example.sendwarmthattendant.util.LogUtil;
import com.example.sendwarmthattendant.util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderDetailPresenter
{
    private Context context;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;

    public OrderDetailPresenter(Context context, SharedPreferences pref) {
        this.context = context;
        this.pref = pref;
    }

    public void acceptOrder(String orderId){
        progressDialog = ProgressDialog.show(context,"","操作中...");
        String address = HttpUtil.LocalAddress + "/api/order/accept";
        String role = pref.getString("role","");
        String credential = pref.getString("credential","");
        String manId;
        if(role.equals("helper")){
            Helper helper = LitePal.where("credential = ?",credential).findFirst(Helper.class);
            manId = helper.getInternetId();
        }else {
            Worker worker = LitePal.where("credential = ?",credential).findFirst(Worker.class);
            manId = worker.getInternetId();
        }
        HttpUtil.acceptOrderRequest(address, credential, orderId, role, manId, new Callback()
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
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String responsData = response.body().string();
                LogUtil.e("HomePresenter",responsData);
                if(Utility.checkResponse(responsData,context)){
                    progressDialog.dismiss();
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "抢单成功！", Toast.LENGTH_LONG).show();
                        }
                    });
                    ((OrderDetailActivity)context).finish();
                }
            }
        });
    }

    public void startOrder(String orderId){
        progressDialog = ProgressDialog.show(context,"","操作中...");
        String address = HttpUtil.LocalAddress + "/api/order/start";
        String credential = pref.getString("credential","");
        HttpUtil.startOrderRequest(address, credential, orderId, new Callback()
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
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String responsData = response.body().string();
                LogUtil.e("OrderDetailPresenter",responsData);
                if(Utility.checkResponse(responsData,context)){
                    progressDialog.dismiss();
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "操作成功！", Toast.LENGTH_LONG).show();
                        }
                    });
                    ((OrderDetailActivity)context).finish();
                }
            }
        });
    }

    public void endOrder(String orderId){
        progressDialog = ProgressDialog.show(context,"","操作中...");
        String address = HttpUtil.LocalAddress + "/api/order/end";
        String credential = pref.getString("credential","");
        HttpUtil.endOrderRequest(address, credential, orderId, new Callback()
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
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String responsData = response.body().string();
                LogUtil.e("OrderDetailPresenter",responsData);
                if(Utility.checkResponse(responsData,context)){
                    progressDialog.dismiss();
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "操作成功！", Toast.LENGTH_LONG).show();
                        }
                    });
                    ((OrderDetailActivity)context).finish();
                }
            }
        });
    }
}
