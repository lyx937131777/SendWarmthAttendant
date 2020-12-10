package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.presenter.SetNewPasswordPresenter;

public class SetNewPasswordActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText telText,newPasswordText,confirmNewPasswordText, verificationCodeText;
    private Button telClearButton, newPasswordClearButton, confirmNewPasswordClearButton, verificationCodeClearButton;
    private String tel,newPassword,confirmNewPassword, verificationCode;
    private TextView sendVerificationCode;

    private SetNewPasswordPresenter setNewPasswordPresenter;

    private TimeCounter timeCounter;
    private class TimeCounter extends CountDownTimer{
        public TimeCounter(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l)
        {
            sendVerificationCode.setClickable(false);
            sendVerificationCode.setText(l/1000 +"S");
        }

        @Override
        public void onFinish()
        {
            sendVerificationCode.setClickable(true);
            sendVerificationCode.setText("发送验证码");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(this)).build();
        setNewPasswordPresenter = myComponent.setNewPasswordPresenter();

        initView();
        timeCounter = new TimeCounter(60*1000,1000);
    }

    private void initView()
    {
        telText = findViewById(R.id.tel);
        telText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                tel = telText.getText().toString();
                if (tel.equals("")) {
                    telClearButton.setVisibility(View.INVISIBLE);
                } else {
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

        newPasswordText = findViewById(R.id.password);
        newPasswordText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                newPassword = newPasswordText.getText().toString();
                if (newPassword.equals("")) {
                    newPasswordClearButton.setVisibility(View.INVISIBLE);
                } else {
                    newPasswordClearButton.setVisibility(View.VISIBLE);
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

        confirmNewPasswordText = findViewById(R.id.confirm_password);
        confirmNewPasswordText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                confirmNewPassword = confirmNewPasswordText.getText().toString();
                if (confirmNewPassword.equals("")) {
                    confirmNewPasswordClearButton.setVisibility(View.INVISIBLE);
                } else {
                    confirmNewPasswordClearButton.setVisibility(View.VISIBLE);
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

        verificationCodeText = findViewById(R.id.verification_code);
        verificationCodeText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                verificationCode = verificationCodeText.getText().toString();
                if (verificationCode.equals("")) {
                    verificationCodeClearButton.setVisibility(View.INVISIBLE);
                } else {
                    verificationCodeClearButton.setVisibility(View.VISIBLE);
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

        telClearButton = findViewById(R.id.tel_clear);
        telClearButton.setOnClickListener(this);
        newPasswordClearButton = findViewById(R.id.password_clear);
        newPasswordClearButton.setOnClickListener(this);
        confirmNewPasswordClearButton = findViewById(R.id.confirm_password_clear);
        confirmNewPasswordClearButton.setOnClickListener(this);
        verificationCodeClearButton = findViewById(R.id.verification_code_clear);
        verificationCodeClearButton.setOnClickListener(this);
        sendVerificationCode = findViewById(R.id.send_verification_code);
        sendVerificationCode.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.commit:
                setNewPasswordPresenter.setNewPassword(telText.getText().toString(),newPasswordText.getText().toString(),
                        confirmNewPasswordText.getText().toString(), verificationCodeText.getText().toString());
                break;
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.commit_menu, menu);
        return true;
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tel_clear:
                telText.setText("");
                break;
            case R.id.password_clear:
                newPasswordText.setText("");
                break;
            case R.id.confirm_password_clear:
                confirmNewPasswordText.setText("");
                break;
            case R.id.verification_code_clear:
                verificationCodeText.setText("");
                break;
            case R.id.send_verification_code:
                timeCounter.start();
                setNewPasswordPresenter.sendVerificationCode(telText.getText().toString());
                break;
        }
    }
}