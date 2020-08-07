package com.example.sendwarmthattendant.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.sendwarmthattendant.LoginActivity;
import com.example.sendwarmthattendant.MainActivity;
import com.example.sendwarmthattendant.util.CheckUtil;

public class LoginPresenter
{
    private Context context;
    private SharedPreferences pref;

    private String username_text;
    private String password_text;
    private CheckUtil checkUtil;

    public LoginPresenter(Context context, SharedPreferences pref, CheckUtil checkUtil)
    {
        this.context = context;
        this.pref = pref;
        this.checkUtil = checkUtil;
    }

    public void login(String username, String password)
    {
        username_text = username;
        password_text = password;

        if (!checkUtil.checkLogin(username, password))
            return;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userID", username_text);
        editor.putString("password", password_text);
        editor.putString("latest", String.valueOf(System.currentTimeMillis()));
        editor.apply();
        Intent intent_login = new Intent(context, MainActivity.class);
        context.startActivity(intent_login);
        ((LoginActivity) context).finish();
    }
}
