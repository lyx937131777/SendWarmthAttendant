package com.example.sendwarmthattendant.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.sendwarmthattendant.util.HttpUtil;
import com.example.sendwarmthattendant.util.LogUtil;
import com.example.sendwarmthattendant.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SettingPresenter
{
    //TODO 上一个在服务器上的版本
    public static final String LAST_VERSION = "0.4.1";
    private Context context;
    private SharedPreferences pref;

    public SettingPresenter(Context context, SharedPreferences pref){
        this.context = context;
        this.pref = pref;
    }

    public void getLatestVersion(final String version){
        final String address = HttpUtil.LocalAddress + "/api/version/newest?isTendant=true";
        String credential = pref.getString("credential",null);
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
                LogUtil.e("SettingPresenter",responseData);
                if(Utility.checkResponse(responseData,context,address)){
                    String latestVersion = Utility.checkDataString(responseData,"versionNo");
                    if(latestVersion == null || latestVersion.equals(Utility.ERROR_CODE)){
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "版本号解析错误", Toast.LENGTH_LONG).show();
                            }
                        });
                        return;
                    }
                    if(latestVersion.equals(LAST_VERSION)){
                        return;
                    }
                    if(!latestVersion.equals(version)){
                        LogUtil.e("SettingPresenter","有新版本需要更新");
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("latestVersion",latestVersion);
                        editor.putString("latestVersionDownloadUrl",Utility.checkDataString(responseData,"fileAddress"));
                        editor.apply();
                        final String des = Utility.checkDataString(responseData,"des");
                        if(des != null && !des.equals("null")){
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(context)
                                            .setTitle("提示")
                                            .setMessage("检测到有新版本，请及时更新！\n更新内容：\n" + des)
                                            .setPositiveButton("确定", null)
                                            .show();
                                }
                            });
                        }else {
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(context)
                                            .setTitle("提示")
                                            .setMessage("检测到有新版本，请及时更新！")
                                            .setPositiveButton("确定", null)
                                            .show();
                                }
                            });
                        }
                    }
                }
            }
        });
    }
}
