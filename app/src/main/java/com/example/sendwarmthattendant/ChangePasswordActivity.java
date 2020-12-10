package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.presenter.ChangePasswordPresenter;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText telText,oldPasswordText,newPasswordText,confirmNewPasswordText;
    private Button oldPasswordClearButton, newPasswordClearButton, confirmNewPasswordClearButton;
    private String tel,oldPassword,newPassword,confirmNewPassword;
    SharedPreferences pref;

    private ChangePasswordPresenter changePasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(this)).build();
        changePasswordPresenter = myComponent.changePasswordPresenter();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        tel = pref.getString("userId","");

        initView();
    }

    private void initView()
    {
        telText = findViewById(R.id.tel);
        telText.setText(tel);

        oldPasswordText = findViewById(R.id.old_password);
        oldPasswordText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                oldPassword = oldPasswordText.getText().toString();
                if (oldPassword.equals("")) {
                    oldPasswordClearButton.setVisibility(View.INVISIBLE);
                } else {
                    oldPasswordClearButton.setVisibility(View.VISIBLE);
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

        oldPasswordClearButton = findViewById(R.id.old_password_clear);
        oldPasswordClearButton.setOnClickListener(this);
        newPasswordClearButton = findViewById(R.id.password_clear);
        newPasswordClearButton.setOnClickListener(this);
        confirmNewPasswordClearButton = findViewById(R.id.confirm_password_clear);
        confirmNewPasswordClearButton.setOnClickListener(this);
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
                changePasswordPresenter.changePassword(oldPasswordText.getText().toString(),
                        newPasswordText.getText().toString(),confirmNewPasswordText.getText().toString());
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
            case R.id.old_password_clear:
                oldPasswordText.setText("");
                break;
            case R.id.password_clear:
                newPasswordText.setText("");
                break;
            case R.id.confirm_password_clear:
                confirmNewPasswordText.setText("");
                break;
        }
    }
}