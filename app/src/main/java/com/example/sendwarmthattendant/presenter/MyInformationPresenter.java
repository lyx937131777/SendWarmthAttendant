package com.example.sendwarmthattendant.presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sendwarmthattendant.MainActivity;
import com.example.sendwarmthattendant.MyInformationActivity;
import com.example.sendwarmthattendant.db.Account;
import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Worker;
import com.example.sendwarmthattendant.util.FileUtil;
import com.example.sendwarmthattendant.util.HttpUtil;
import com.example.sendwarmthattendant.util.LogUtil;
import com.example.sendwarmthattendant.util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyInformationPresenter {
    private Context context;
    private SharedPreferences pref;
    private ProgressDialog progressDialog;

    public MyInformationPresenter(Context context, SharedPreferences pref) {
        this.context = context;
        this.pref = pref;
    }

    public void updateAccount(){
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
                LogUtil.e("MyInformationPresenter",responseData);
                if(Utility.checkResponse(responseData,context,address)){
                    Account account = Utility.handleAccount(responseData);
                    String role = Utility.getRole(responseData);
                    LogUtil.e("MainPresenter",role);
                    if(role.equals("helper") || role.equals("nurse") || role.equals("shopManager")){
                        if(role.equals("helper")){
                            Helper helper = Utility.handleHelper(responseData);
                            LitePal.deleteAll(Helper.class,"userId = ?",tel);
                            helper.setUserId(tel);
                            helper.setCredential(credential);
                            helper.save();
                        }else {
                            Worker worker = Utility.handleWorker(responseData);
                            LitePal.deleteAll(Worker.class,"userId = ?",tel);
                            worker.setUserId(tel);
                            worker.setCredential(credential);
                            worker.save();
                        }
                    }
                    ((MyInformationActivity)context).setAccount(account);
                }
            }
        });
    }

    public void resetProfile(final String imagePath){
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("确定更换头像么？")
                .setPositiveButton("确定", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog = ProgressDialog.show(context, "", "上传中...");
                                String address = HttpUtil.LocalAddress + "/api/users/resetProfile";
                                final String credential = pref.getString("credential", "");
                                String accountId = "";
                                String role = pref.getString("role","");
                                if(role.equals("helper")){
                                    Helper helper = LitePal.where("credential = ?",credential).findFirst(Helper.class);
                                    accountId = helper.getAccountId();
                                } else {
                                    Worker worker = LitePal.where("credential = ?",credential).findFirst(Worker.class);
                                    accountId = worker.getAccountId();
                                }
                                String fileName = FileUtil.compressImagePathToImagePath(imagePath);
                                HttpUtil.resetProfileRequest(address, accountId, new File(fileName), new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(context, "网络连接错误", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        final String responseData = response.body().string();
                                        if(Utility.checkResponse(responseData,context,address)){
                                            updateAccount();
                                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(context, "头像更换成功！", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }).setNegativeButton("取消",null).show();
    }
}
