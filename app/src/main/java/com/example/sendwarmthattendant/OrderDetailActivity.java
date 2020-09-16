package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.util.MapUtil;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener
{
    private Order order;

    private CardView customerNameCard;
    private TextView customerName;
    private CardView customerTelCard;
    private TextView customerTel;
    private TextView state;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        order = (Order) getIntent().getSerializableExtra("order");

        customerNameCard = findViewById(R.id.customer_name_card);
        customerName = findViewById(R.id.customer_name);
        customerTelCard = findViewById(R.id.customer_tel_card);
        customerTel = findViewById(R.id.customer_tel);

        state = findViewById(R.id.state);
        button = findViewById(R.id.button);

        state.setText(MapUtil.getOrderState(order.getState()));
        if(order.getState().equals("arrived")){
            button.setText("完成订单");
        }else if(order.getState().equals("unstart")){
            button.setText("开始订单");
        }else if(order.getState().equals("waiting")) {
            button.setText("立即抢单");
        }else {
            button.setVisibility(View.GONE);
        }

        customerNameCard.setOnClickListener(this);
        customerName.setOnClickListener(this);
        customerTelCard.setOnClickListener(this);
        customerTel.setOnClickListener(this);
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

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.customer_name_card:
            case R.id.customer_name:{
                Intent intent = new Intent(OrderDetailActivity.this, CustomerInformationActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.customer_tel_card:
            case R.id.customer_tel:{
                final String tel = customerTel.getText().toString();
                new AlertDialog.Builder(OrderDetailActivity.this).setTitle("提示")
                        .setMessage("是否拨打电话"+tel+"？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+tel));
                                startActivity(intent);
                            }
                        }).setNegativeButton("取消",null).show();
                break;
            }
        }
    }
}