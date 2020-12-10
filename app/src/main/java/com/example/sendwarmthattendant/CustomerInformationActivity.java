package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sendwarmthattendant.db.Customer;

public class CustomerInformationActivity extends AppCompatActivity
{
    private Customer customer;
    private TextView nickNameText,realNameText,addressText,telText,desText;
    private Button historyOrderButton;

    private SharedPreferences pref;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initCustomer();
    }

    private void initCustomer()
    {
        customer = (Customer)getIntent().getSerializableExtra("customer");
        nickNameText = findViewById(R.id.nickname);
        realNameText = findViewById(R.id.real_name);
        addressText = findViewById(R.id.address);
        telText = findViewById(R.id.tel);
        desText = findViewById(R.id.personal_signature);
        historyOrderButton = findViewById(R.id.history_order);

        nickNameText.setText(customer.getUserName());
        realNameText.setText(customer.getName());
        addressText.setText(customer.getAddress());
        telText.setText(customer.getTel());
        desText.setText(customer.getPersonalDescription());

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        role = pref.getString("role","");
        if(role.equals("helper")){
            historyOrderButton.setVisibility(View.GONE);
        }

        historyOrderButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CustomerInformationActivity.this,CustomerHistoryOrderActivity.class);
                intent.putExtra("customerId",customer.getInternetId());
                startActivity(intent);
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
}