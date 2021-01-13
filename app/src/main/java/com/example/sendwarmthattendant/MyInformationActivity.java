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
import androidx.cardview.widget.CardView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyInformationActivity extends AppCompatActivity
{
    private Account account;

    private CircleImageView profile;
    private TextView nameText, addressText, telText, idText, workerClassText1, workerClassText2, levelText;
    private CardView addressCard, workerClassCard1, workerClassCard2;

    private String role;
    private Helper helper;
    private Worker worker;

    private Button logoutButton,changePasswordButton;

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

        changePasswordButton = findViewById(R.id.change_password);
        changePasswordButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MyInformationActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

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
        nameText = findViewById(R.id.name);
        addressText = findViewById(R.id.address);
        addressCard = findViewById(R.id.address_card);
        telText = findViewById(R.id.tel);
        idText = findViewById(R.id.id);
        workerClassText1 = findViewById(R.id.worker_class_1);
        workerClassText2 = findViewById(R.id.worker_class_2);
        workerClassCard1 = findViewById(R.id.worker_class_card_1);
        workerClassCard2 = findViewById(R.id.worker_class_card_2);
        levelText = findViewById(R.id.title);

        Glide.with(this).load(R.drawable.profile_uri).into(profile);
        if(role.equals("helper")){
            nameText.setText(helper.getHelperName());
            addressCard.setVisibility(View.GONE);
            telText.setText(helper.getHelperTel());
            idText.setText(helper.getHelperIdCard());
            workerClassText1.setText(helper.getWorkerClass1().toString());
            workerClassText2.setText(helper.getWorkerClass2().toString());
            levelText.setText(helper.getLevel()+"级助老员");
        }else{
            nameText.setText(worker.getWorkerName());
            addressText.setText(worker.getStoreName());
            telText.setText(worker.getWorkerTel());
            idText.setText(worker.getEmployeeId());
            levelText.setText(worker.getLevel()+"级护理员");
            workerClassCard1.setVisibility(View.GONE);
            workerClassCard2.setVisibility(View.GONE);
        }

    }
}