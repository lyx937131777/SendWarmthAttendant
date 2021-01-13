package com.example.sendwarmthattendant.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
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
import java.util.List;

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

    public void acceptOrder(String orderId, String workerId){
        progressDialog = ProgressDialog.show(context,"","操作中...");
        final String address = HttpUtil.LocalAddress + "/api/order/accept";
        String role = pref.getString("role","");
        String credential = pref.getString("credential","");
        String manId = workerId;
        if(manId != null && manId.equals("0")){
            if(role.equals("helper")){
                Helper helper = LitePal.where("credential = ?",credential).findFirst(Helper.class);
                manId = helper.getInternetId();
            }else {
                Worker worker = LitePal.where("credential = ?",credential).findFirst(Worker.class);
                manId = worker.getInternetId();
            }
        }else {
            role = "nurse";
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
                String responseData = response.body().string();
                LogUtil.e("HomePresenter",responseData);
                progressDialog.dismiss();
                if(Utility.checkResponse(responseData,context,address)){
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
        final String address = HttpUtil.LocalAddress + "/api/order/start";
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
                String responseData = response.body().string();
                LogUtil.e("OrderDetailPresenter",responseData);
                progressDialog.dismiss();
                if(Utility.checkResponse(responseData,context,address)){
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
        final String address = HttpUtil.LocalAddress + "/api/order/end";
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
                String responseData = response.body().string();
                LogUtil.e("OrderDetailPresenter",responseData);
                progressDialog.dismiss();
                if(Utility.checkResponse(responseData,context,address)){
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

    public void commentOrder(String orderId, String comment){
        progressDialog = ProgressDialog.show(context,"","操作中...");
        final String address = HttpUtil.LocalAddress + "/api/order/workerComment";
        String credential = pref.getString("credential","");
        HttpUtil.commentOrderRequest(address, credential, orderId, comment, new Callback()
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
                String responseData = response.body().string();
                LogUtil.e("OrderDetailPresenter",responseData);
                progressDialog.dismiss();
                if(Utility.checkResponse(responseData,context,address)){
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

    public void updateWorker(double longitude, double latitude, final ArrayAdapter<Worker> workerArrayAdapter, final List<Worker> workerList){
        final String address = HttpUtil.LocalAddress + "/api/order/storeWorker";
        final String credential = pref.getString("credential","");
        LogUtil.e("OrderingPresenter","longitude: " + longitude + "   latitude: " + latitude);
        HttpUtil.getStoreWorker(address, credential, longitude, latitude, new Callback()
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
                LogUtil.e("OrderingPresenter",responseData);
                if(Utility.checkResponse(responseData,context,address)){
                    workerList.clear();
                    Worker noWorker = new Worker();
                    noWorker.setWorkerName("不指定");
                    noWorker.setInternetId("0");
                    workerList.add(noWorker);
                    List<Worker> tempWorkerList = Utility.handleWorkerList(responseData);
                    if (tempWorkerList != null){
                        workerList.addAll(tempWorkerList);
                        LogUtil.e("OrderDetailPresenter",""+tempWorkerList.size());
                    }
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            workerArrayAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
