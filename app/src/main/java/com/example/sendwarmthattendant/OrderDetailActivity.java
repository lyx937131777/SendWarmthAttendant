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

import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.presenter.OrderDetailPresenter;
import com.example.sendwarmthattendant.util.MapUtil;
import com.example.sendwarmthattendant.util.TimeUtil;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener
{
    private Order order;

    private TextView numberText,startTimeTypeText, endTimeTypeText,startTimeText,endTimeText,serviceClassText,serviceContentText,priceText,addressText,houseNumText,messageText;
    private TextView tipText,orderTypeText,appointedPersonText;
    private CardView customerNameCard;
    private TextView customerName;
    private CardView customerTelCard;
    private TextView customerTel;
    private TextView state;
    private Button button;

    private OrderDetailPresenter orderDetailPresenter;

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

        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(this)).build();
        orderDetailPresenter = myComponent.orderDetailPresenter();

        order = (Order) getIntent().getSerializableExtra("order");

        numberText = findViewById(R.id.number);
        appointedPersonText = findViewById(R.id.appointed_person);
        startTimeTypeText = findViewById(R.id.start_time_type);
        endTimeTypeText = findViewById(R.id.end_time_type);
        startTimeText = findViewById(R.id.start_time);
        endTimeText = findViewById(R.id.end_time);
        serviceClassText = findViewById(R.id.service_type);
        serviceContentText = findViewById(R.id.service_content);
        priceText = findViewById(R.id.price);
        addressText = findViewById(R.id.address);
        houseNumText = findViewById(R.id.house_num);
        messageText = findViewById(R.id.message);
        orderTypeText =  findViewById(R.id.order_type);
        tipText = findViewById(R.id.tip);

        customerNameCard = findViewById(R.id.customer_name_card);
        customerName = findViewById(R.id.customer_name);
        customerTelCard = findViewById(R.id.customer_tel_card);
        customerTel = findViewById(R.id.customer_tel);

        state = findViewById(R.id.state);
        button = findViewById(R.id.button);

        numberText.setText(order.getOrderNo());
        appointedPersonText.setText(order.getAppointedPerson());
        if(order.getState().equals("not_start") || order.getState().equals("not_accepted") || order.getState().equals("canceled")){
            startTimeTypeText.setText("预计上门");
            endTimeTypeText.setText("预计结束");
            startTimeText.setText(TimeUtil.timeStampToString(order.getExpectStartTime(),"yyyy-MM-dd HH:mm"));
            endTimeText.setText(TimeUtil.timeStampToString(order.getExpectEndTime(),"yyyy-MM-dd HH:mm"));
        }else if(order.getState().equals("on_going")){
            startTimeTypeText.setText("上门时间");
            endTimeTypeText.setText("预计结束");
            startTimeText.setText(TimeUtil.timeStampToString(order.getStartTime(),"yyyy-MM-dd HH:mm"));
            endTimeText.setText(TimeUtil.timeStampToString(order.getExpectEndTime(),"yyyy-MM-dd HH:mm"));
        }else {
            startTimeTypeText.setText("上门时间");
            endTimeTypeText.setText("结束时间");
            startTimeText.setText(TimeUtil.timeStampToString(order.getStartTime(),"yyyy-MM-dd HH:mm"));
            endTimeText.setText(TimeUtil.timeStampToString(order.getEndTime(),"yyyy-MM-dd HH:mm"));
        }
        serviceClassText.setText(order.getServiceClassInfo().getName());
        serviceContentText.setText(order.getServiceSubjectInfo().getName());
        addressText.setText(order.getDeliveryDetail());
        houseNumText.setText(order.getHouseNum());
        messageText.setText(order.getMessage());
        orderTypeText.setText(MapUtil.getOrderType(order.getOrderType()));
        tipText.setText("" + order.getTip());

        customerName.setText(order.getCustomerInfo().getName());
        customerTel.setText(order.getDeliveryPhone());

        state.setText(MapUtil.getOrderState(order.getState()));
        if(order.getState().equals("on_going")){
            button.setText("结束订单");
        }else if(order.getState().equals("not_start")){
            button.setText("开始订单");
        }else if(order.getState().equals("not_accepted")) {
            button.setText("立即抢单");
        }else {
            button.setVisibility(View.GONE);
        }

        customerNameCard.setOnClickListener(this);
        customerName.setOnClickListener(this);
        customerTelCard.setOnClickListener(this);
        customerTel.setOnClickListener(this);
        button.setOnClickListener(this);
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
            case R.id.button:{
                if(order.getState().equals("on_going")){
                    orderDetailPresenter.endOrder(order.getInternetId());
                }else if(order.getState().equals("not_start")){
                    orderDetailPresenter.startOrder(order.getInternetId());
                }else if(order.getState().equals("not_accepted")) {
                    orderDetailPresenter.acceptOrder(order.getInternetId());
                }
            }
        }
    }
}