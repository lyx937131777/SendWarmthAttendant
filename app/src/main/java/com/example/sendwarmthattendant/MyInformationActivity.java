package com.example.sendwarmthattendant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Account;
import com.example.sendwarmthattendant.db.Worker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyInformationActivity extends AppCompatActivity
{
    private Account account;

    private CircleImageView profile;
    private TextView name;
    private TextView address;
    private TextView tel;
    private TextView id;

    private String role;
    private Helper helper;
    private Worker worker;

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initUser();

        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MyInformationActivity.this).edit();
                editor.remove("userId");
                editor.remove("password");
                editor.apply();
                Intent intent_logout = new Intent(MyInformationActivity.this, LoginActivity.class);
                startActivity(intent_logout);
                MainActivity.instance.finish();
                finish();
            }
        });
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

    private void initUser()
    {
        role = getIntent().getStringExtra("role");
        if(role.equals("helper")){
            helper = (Helper) getIntent().getSerializableExtra("helper");
        }else{
            worker = (Worker) getIntent().getSerializableExtra("worker");
        }

        profile = findViewById(R.id.profile);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        tel = findViewById(R.id.tel);
        id = findViewById(R.id.id);

        Glide.with(this).load(R.drawable.profile_uri).into(profile);
        if(role.equals("helper")){
            name.setText(helper.getName());
            tel.setText(helper.getTel());
            id.setText(helper.getIdCardNumber());
        }else{
            name.setText(worker.getWorkerName());
            tel.setText(worker.getWorkerTel());
            id.setText(worker.getEmployeeId());
        }

    }
}