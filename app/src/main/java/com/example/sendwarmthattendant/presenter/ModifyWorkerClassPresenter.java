package com.example.sendwarmthattendant.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sendwarmthattendant.ModifyWorkerClassActivity;
import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.ServiceSubject;
import com.example.sendwarmthattendant.util.CheckUtil;
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

public class ModifyWorkerClassPresenter {
    private Context context;
    private CheckUtil checkUtil;
    private ProgressDialog progressDialog;
    private SharedPreferences pref;

    public ModifyWorkerClassPresenter(Context context, CheckUtil checkUtil, SharedPreferences pref) {
        this.context = context;
        this.checkUtil = checkUtil;
        this.pref = pref;
    }

    public void updateServiceSubject(final ArrayAdapter<ServiceSubject> arrayAdapter1, final List<ServiceSubject> subjectList1, final Spinner spinner1,
                                     final ArrayAdapter<ServiceSubject> arrayAdapter2, final List<ServiceSubject> subjectList2, final Spinner spinner2){
        final String address = HttpUtil.LocalAddress + "/api/servicesubject/list?isHelper=true";
        HttpUtil.getHttpWithoutCredential(address, new Callback()
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
                LogUtil.e("ModifyWorkerClassPresenter",responseData);
                if(Utility.checkResponse(responseData,context,address)){
                    subjectList1.clear();
                    subjectList2.clear();
                    subjectList1.addAll(Utility.handleServiceSubjectList(responseData));
                    subjectList2.addAll(Utility.handleServiceSubjectList(responseData));
                    final String credential = pref.getString("credential","");
                    Helper helper = LitePal.where("credential = ?", credential).findFirst(Helper.class);
                    ((AppCompatActivity)context).runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            arrayAdapter1.notifyDataSetChanged();
                            arrayAdapter2.notifyDataSetChanged();
                            for(int i = 0; i < subjectList1.size(); i++){
                                if(subjectList1.get(i).getInternetId().equals(String.valueOf(helper.getWorkClass1()))){
                                    spinner1.setSelection(i);
                                }
                            }
                            for(int i = 0; i < subjectList1.size(); i++){
                                if(subjectList2.get(i).getInternetId().equals(String.valueOf(helper.getWorkClass2()))){
                                    spinner2.setSelection(i);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void modifyHelper(int workType1, int workType2){
        final String address = HttpUtil.LocalAddress + "/api/helper";
        final String credential = pref.getString("credential","");
        Helper helper = LitePal.where("credential = ?", credential).findFirst(Helper.class);
        if(!checkUtil.checkWorkerClass(helper.getWorkClass1(),helper.getWorkClass2(), workType1,workType2))
            return;
        progressDialog = ProgressDialog.show(context, "", "保存中...");
        helper.setWorkClass1(workType1);
        helper.setWorkClass2(workType2);
        HttpUtil.modifyHelperRequest(address, credential, helper, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
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
                LogUtil.e("ModifyWorkerClassPresenter",responseData);
                if(Utility.checkResponse(responseData,context,address)){
                    helper.saveAll();
                    ((ModifyWorkerClassActivity)context).finish();
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                progressDialog.dismiss();
            }
        });
    }
}
