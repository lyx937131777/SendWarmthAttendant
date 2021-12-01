package com.example.sendwarmthattendant.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sendwarmthattendant.LoginActivity;
import com.example.sendwarmthattendant.MainActivity;
import com.example.sendwarmthattendant.db.Account;
import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Worker;
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

public class MainPresenter {

    private Context context;
    private SharedPreferences pref;

    public MainPresenter(Context context, SharedPreferences pref) {
        this.context = context;
        this.pref = pref;
    }

    public void getMe(){
        final String tel = pref.getString("userId","");
        final String address = HttpUtil.LocalAddress + "/api/users/me";
        final String credential = pref.getString("credential","");
        HttpUtil.getHttp(address, credential, new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                ((AppCompatActivity)context).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(context, "网络连接错误", Toast.LENGTH_LONG).show();

                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                final String responseData = response.body().string();
                LogUtil.e("MainPresenter",responseData);
                if(Utility.checkResponse(responseData,context,address)){
                    Account account = Utility.handleAccount(responseData);
                    ((MainActivity) context).setAccount(account);
                    String role = Utility.getRole(responseData);
                    LogUtil.e("MainPresenter",role);
                    if(role.equals("helper") || role.equals("nurse") || role.equals("shopManager")){
                        if(role.equals("helper")){
                            Helper helper = Utility.handleHelper(responseData);
//                            LitePal.deleteAll(Helper.class,"userId = ?",tel);
                            List<Helper> helperList = LitePal.where("userId = ?", tel).find(Helper.class);
                            helper.setUserId(tel);
                            helper.setCredential(credential);
                            helper.saveAll();
                            for(Helper helperToDelete : helperList){
                                helperToDelete.delete();
                            }
                            ((MainActivity) context).setHelper(helper);
                        }else {
                            Worker worker = Utility.handleWorker(responseData);
//                            LitePal.deleteAll(Worker.class,"userId = ?",tel);
                            List<Worker> workerList = LitePal.where("userId = ?", tel).find(Worker.class);
                            worker.setUserId(tel);
                            worker.setCredential(credential);
                            worker.save();
                            for(Worker workerToDelete : workerList){
                                workerToDelete.delete();
                            }
                            ((MainActivity) context).setWorker(worker);
                        }
                    }else{
                        ((AppCompatActivity) context).runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                new AlertDialog.Builder(context)
                                        .setTitle("提示")
                                        .setMessage("该账号无权登录此APP！")
                                        .setPositiveButton("确定", null)
                                        .show();
                            }
                        });
                    }
                }
            }
        });
    }
}
