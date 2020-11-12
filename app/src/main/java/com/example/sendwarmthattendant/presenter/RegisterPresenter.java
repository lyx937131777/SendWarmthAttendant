package com.example.sendwarmthattendant.presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.sendwarmthattendant.RegisterActivity;
import com.example.sendwarmthattendant.db.ServiceSubject;
import com.example.sendwarmthattendant.util.CheckUtil;
import com.example.sendwarmthattendant.util.FileUtil;
import com.example.sendwarmthattendant.util.HttpUtil;
import com.example.sendwarmthattendant.util.LogUtil;
import com.example.sendwarmthattendant.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterPresenter
{
    private Context context;
    private CheckUtil checkUtil;
    private ProgressDialog progressDialog;
    private SharedPreferences pref;

    public RegisterPresenter(Context context, CheckUtil checkUtil, SharedPreferences pref) {
        this.context = context;
        this.checkUtil = checkUtil;
        this.pref = pref;
    }

    public void register(final String tel, final String password, String confirmPassword, final String name, final int workType1, final int workType2, final String id, String idCardFront, final String idCardBack)
    {
        if (!checkUtil.checkRegister(tel,password,confirmPassword,name,workType1,workType2,id,idCardFront,idCardBack))
            return;
        progressDialog = ProgressDialog.show(context,"","上传中...");
        String address = HttpUtil.LocalAddress + "/api/file";
        String idCardFront1 = FileUtil.compressImagePathToImagePath(idCardFront);
        HttpUtil.fileRequest(address, new File(idCardFront1), new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                ((RegisterActivity)context).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(context, "网络连接错误", Toast.LENGTH_LONG).show();
                    }
                });
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                final String responsData = response.body().string();
                LogUtil.e("RegisterPresenter",responsData);
                final String photoPathFront = Utility.checkString(responsData,"msg");
                String address = HttpUtil.LocalAddress + "/api/file";
                String idCardBack2 = FileUtil.compressImagePathToImagePath(idCardBack);
                HttpUtil.fileRequest(address, new File(idCardBack2), new Callback()
                {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e)
                    {
                        ((RegisterActivity)context).runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                Toast.makeText(context, "网络连接错误", Toast.LENGTH_LONG).show();
                            }
                        });
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                    {
                        final String responsData = response.body().string();
                        LogUtil.e("RegisterPresenter",responsData);
                        final String photoPathBack = Utility.checkString(responsData,"msg");
                        String address = HttpUtil.LocalAddress + "/api/users/helper";
                        HttpUtil.registerRequest(address, tel, password, name, workType1, workType2, id, photoPathFront, photoPathBack, new Callback()
                        {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e)
                            {
                                ((RegisterActivity)context).runOnUiThread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        Toast.makeText(context, "网络连接错误", Toast.LENGTH_LONG).show();
                                    }
                                });
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
                            {
                                final String responseData = response.body().string();
                                LogUtil.e("RegisterPresenter", responseData);
                                if (Utility.checkString(responseData,"code").equals("000"))
                                {
                                    ((RegisterActivity) context).runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            new AlertDialog.Builder(context)
                                                    .setTitle("提示")
                                                    .setMessage("注册成功！")
                                                    .setPositiveButton("确定", new
                                                            DialogInterface.OnClickListener()
                                                            {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int
                                                                        which)
                                                                {
                                                                    ((RegisterActivity) context).finish();
                                                                }
                                                            }).show();
                                        }
                                    });
                                } else if (Utility.checkString(responseData,"code").equals("500"))
                                {
                                    if(Utility.checkString(responseData,"msg").equals("用户名不能重复。")){
                                        ((RegisterActivity) context).runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                new AlertDialog.Builder(context)
                                                        .setTitle("提示")
                                                        .setMessage("该账户已被注册！")
                                                        .setPositiveButton("确定", null)
                                                        .show();
                                            }
                                        });
                                    }else{
                                        ((RegisterActivity) context).runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                new AlertDialog.Builder(context)
                                                        .setTitle("提示")
                                                        .setMessage(Utility.checkString(responseData,"msg"))
                                                        .setPositiveButton("确定", null)
                                                        .show();
                                            }
                                        });
                                    }

                                } else {
                                    ((RegisterActivity) context).runOnUiThread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            new AlertDialog.Builder(context)
                                                    .setTitle("提示")
                                                    .setMessage("由于未知原因注册失败，请重试！")
                                                    .setPositiveButton("确定", null)
                                                    .show();
                                        }
                                    });
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                });
            }
        });

    }

    public void updateServiceSubject(final ArrayAdapter<ServiceSubject> arrayAdapter1, final List<ServiceSubject> subjectList1,
                                     final ArrayAdapter<ServiceSubject> arrayAdapter2, final List<ServiceSubject> subjectList2){
        String address = HttpUtil.LocalAddress + "/api/servicesubject/list";
        HttpUtil.getHttpWithoutCredential(address, new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                ((RegisterActivity)context).runOnUiThread(new Runnable()
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
                final String responsData = response.body().string();
                LogUtil.e("RegisterPresenter",responsData);
                subjectList1.clear();
                subjectList2.clear();
                subjectList1.addAll(Utility.handleServiceSubjectList(responsData));
                subjectList2.addAll(Utility.handleServiceSubjectList(responsData));
                ((RegisterActivity)context).runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        arrayAdapter1.notifyDataSetChanged();
                        arrayAdapter2.notifyDataSetChanged();
                    }
                });

            }
        });
    }
}
