package com.example.sendwarmthattendant.presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

public class SetNewPasswordPresenter
{
    private Context context;
    private SharedPreferences pref;
    private CheckUtil checkUtil;
    private ProgressDialog progressDialog;

    public SetNewPasswordPresenter(Context context, SharedPreferences pref, CheckUtil checkUtil) {
        this.context = context;
        this.pref = pref;
        this.checkUtil = checkUtil;
    }

    public void sendVerificationCode(String tel){
        if(!checkUtil.checkSendVerificationCode(tel)){
            return;
        }
        final String address = HttpUtil.LocalAddress + "/api/users/unlogin/sendTelCode";
        HttpUtil.sendVerificationCodeRequest(address, tel, new Callback()
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
                LogUtil.e("SetNewPasswordPresenter",responseData);
                if(Utility.checkResponse(responseData,context,address)){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "验证码发送成功", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    public void setNewPassword(final String tel, final String newPassword, String confirmNewPassword, final String verificationCode){
        if(!checkUtil.checkSetNewPassword(tel,newPassword,confirmNewPassword,verificationCode)){
            return;
        }
        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("确认设置新密码么？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        setNewPassword(tel,newPassword,verificationCode);
                    }
                }).setNegativeButton("取消",null).show();
    }

    public void setNewPassword(String tel, String newPassword, String verificationCode){
        progressDialog = ProgressDialog.show(context,"","设置新密码中...");
        final String address = HttpUtil.LocalAddress + "/api/users/forgetPassword/checkTelCode";
        HttpUtil.setNewPasswordRequest(address, tel, verificationCode, newPassword, new Callback()
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
                LogUtil.e("SetNewPasswordPresenter",responseData);
                progressDialog.dismiss();
                if(Utility.checkResponse(responseData,context,address)){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "新密码设置成功", Toast.LENGTH_LONG).show();
                        }
                    });
                    ((AppCompatActivity)context).finish();
                }
            }
        });
    }
}
