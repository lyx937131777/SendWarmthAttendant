package com.example.sendwarmthattendant.presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Worker;
import com.example.sendwarmthattendant.util.CheckUtil;
import com.example.sendwarmthattendant.util.HttpUtil;
import com.example.sendwarmthattendant.util.LogUtil;
import com.example.sendwarmthattendant.util.Utility;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.Response;

public class ChangePasswordPresenter
{
    private Context context;
    private SharedPreferences pref;
    private CheckUtil checkUtil;
    private ProgressDialog progressDialog;

    public ChangePasswordPresenter(Context context, SharedPreferences pref, CheckUtil checkUtil) {
        this.context = context;
        this.pref = pref;
        this.checkUtil = checkUtil;
    }

    public void changePassword(final String oldPassword, final String newPassword, String confirmNewPassword){
        if(!checkUtil.checkChangePassword(oldPassword,newPassword,confirmNewPassword)){
            return;
        }
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("确认修改密码么？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        changePassword(oldPassword,newPassword);
                    }
                }).setNegativeButton("取消",null).show();

    }

    private void changePassword(String oldPassword, final String newPassword){
        progressDialog = ProgressDialog.show(context,"","修改密码中...");
        final String address = HttpUtil.LocalAddress + "/api/users/password";
        final String credential = pref.getString("credential",null);
        HttpUtil.changePasswordRequest(address, credential, oldPassword, newPassword, new Callback()
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
                LogUtil.e("ChangePasswordPresenter",responseData);
                progressDialog.dismiss();
                if(Utility.checkResponse(responseData,context,address)){
                    String tel = pref.getString("userId","");
                    String role = pref.getString("role","");
                    String newCredential = Credentials.basic(tel, newPassword);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("password", newPassword);
                    editor.putString("credential",newCredential);
                    editor.putString("latest", String.valueOf(System.currentTimeMillis()));
                    editor.apply();
                    if(role.equals("helper")){
                        Helper helper = LitePal.where("credential = ?", credential).findFirst(Helper.class);
                        helper.setCredential(newCredential);
                        helper.save();
                    }else {
                        Worker worker = LitePal.where("credential = ?", credential).findFirst(Worker.class);
                        worker.setCredential(newCredential);
                        worker.save();
                    }
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "密码修改成功", Toast.LENGTH_LONG).show();
                        }
                    });
                    ((AppCompatActivity)context).finish();
                }
            }
        });
    }
}
