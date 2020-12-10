package com.example.sendwarmthattendant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.presenter.LoginPresenter;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements OnClickListener
{
    private EditText telText, passwordText;
    private Button telClearButton;
    private Button passwordClearButton;
    private Button passwordEyeButton;
    private Button loginButton;
    private Button goRegisterButton,setNewPasswordButton;
    private boolean isOpen = false;
    private String tel, passowrd;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        PermissionsUtil.verifyStoragePermissions(this);
        initView();
//        androidx.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null)
//        {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(this)).build();
        loginPresenter = myComponent.loginPresenter();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        tel = pref.getString("userId",null);
        passowrd = pref.getString("password",null);
        if(tel != null & passowrd != null)
        {
            Intent intent_login = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent_login);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;

        }
        return true;
    }

    private void initView()
    {
        telText = findViewById(R.id.tel);
        // 监听文本框内容变化
        telText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // 获得文本框中的用户
                tel = telText.getText().toString().trim();
                if (tel.equals(""))
                {
                    // 用户名为空,设置按钮不可见
                    telClearButton.setVisibility(View.INVISIBLE);
                } else
                {
                    // 用户名不为空，设置按钮可见
                    telClearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        passwordText = findViewById(R.id.password);
        passwordText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                // 获得文本框中的用户
                passowrd = passwordText.getText().toString().trim();
                if (passowrd.equals(""))
                {
                    // 用户名为空,设置按钮不可见
                    passwordClearButton.setVisibility(View.INVISIBLE);
                } else
                {
                    // 用户名不为空，设置按钮可见
                    passwordClearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        //初始化button
        telClearButton = findViewById(R.id.tel_clear);
        telClearButton.setOnClickListener(this);
        passwordClearButton = findViewById(R.id.password_clear);
        passwordClearButton.setOnClickListener(this);
        passwordEyeButton = findViewById(R.id.password_eye);
        passwordEyeButton.setOnClickListener(this);
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(this);
        goRegisterButton = findViewById(R.id.go_register);
        goRegisterButton.setOnClickListener(this);
        setNewPasswordButton = findViewById(R.id.set_new_password);
        setNewPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tel_clear:
                telText.setText("");
                break;
            case R.id.password_clear:
                passwordText.setText("");
                break;

            // 密码可见与不可见的切换
            case R.id.password_eye:
                if (isOpen)
                {
                    isOpen = false;
                } else
                {
                    isOpen = true;
                }
                // 默认isOpen是false,密码不可见
                changePwdOpenOrClose(isOpen);
                break;

            case R.id.login:
                tel = telText.getText().toString();
                passowrd = passwordText.getText().toString();
                loginPresenter.login(tel, passowrd);
                break;
            // 注册按钮
            case R.id.go_register:{
                Intent reg = new Intent();
                reg.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(reg);
                break;
            }
            // 找回密码
            case R.id.set_new_password:{
                Intent intent = new Intent(this,SetNewPasswordActivity.class);
                startActivity(intent);
            }
            default:
                break;
        }
    }

    /**
     * 密码可见与不可见的切换
     *
     * @param flag
     */
    private void changePwdOpenOrClose(boolean flag)
    {
        // 第一次过来是false，密码不可见
        if (flag)
        {
            // 密码可见
            passwordEyeButton.setBackgroundResource(R.drawable.pwd_open);
            // 设置EditText的密码可见
            passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else
        {
            // 密码不可见
            passwordEyeButton.setBackgroundResource(R.drawable.pwd_closed);
            // 设置EditText的密码隐藏
            passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}