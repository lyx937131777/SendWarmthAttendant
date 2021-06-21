package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.db.ServiceSubject;
import com.example.sendwarmthattendant.db.Worker;
import com.example.sendwarmthattendant.presenter.OrderDetailPresenter;
import com.example.sendwarmthattendant.util.HttpUtil;
import com.example.sendwarmthattendant.util.LogUtil;
import com.example.sendwarmthattendant.util.MapUtil;
import com.example.sendwarmthattendant.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener
{
    private Order order;
    private String state;

    private ServiceSubject serviceSubject;

    private SharedPreferences pref;
    private String role;

    private TextView numberText,startTimeTypeText, endTimeTypeText,startTimeText,endTimeText,priceText,addressText,houseNumText,messageText;
    private TextView tipText,orderTypeText,appointedPersonText,customerCommentText, attendantCommentText;
    private CardView customerNameCard,customerTelCard;
    private TextView customerName,customerTel;
    private TextView stateText;
    private Button button;
    private CardView commentCard,customerCommentCard, attendantCommentCard,appointedPersonCard;
    private EditText commentText;

    private Spinner workerSpiner;
    private List<Worker> workerList = new ArrayList<>();
    private ArrayAdapter<Worker> workerArraryAdapter;
    private String workerId = "0";

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
        state = order.getState();

        initServiceWork();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        role = pref.getString("role","");

        initWorkerList();
        workerArraryAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,workerList);
        workerArraryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workerSpiner.setAdapter(workerArraryAdapter);


        numberText = findViewById(R.id.number);
        appointedPersonText = findViewById(R.id.appointed_person);
        appointedPersonCard = findViewById(R.id.appointed_person_card);
        startTimeTypeText = findViewById(R.id.start_time_type);
        endTimeTypeText = findViewById(R.id.end_time_type);
        startTimeText = findViewById(R.id.start_time);
        endTimeText = findViewById(R.id.end_time);
        priceText = findViewById(R.id.service_price);
        addressText = findViewById(R.id.address);
        houseNumText = findViewById(R.id.house_num);
        messageText = findViewById(R.id.message);
        orderTypeText =  findViewById(R.id.order_type);
        tipText = findViewById(R.id.tip);

        customerCommentCard = findViewById(R.id.customer_comment_card);
        customerCommentText = findViewById(R.id.customer_comment);
        attendantCommentCard = findViewById(R.id.attendant_comment_card);
        attendantCommentText = findViewById(R.id.attendant_comment);
        commentCard = findViewById(R.id.comment_card);
        commentText = findViewById(R.id.comment);

        customerNameCard = findViewById(R.id.customer_name_card);
        customerName = findViewById(R.id.customer_name);
        customerTelCard = findViewById(R.id.customer_tel_card);
        customerTel = findViewById(R.id.customer_tel);

        stateText = findViewById(R.id.state);
        button = findViewById(R.id.button);

        numberText.setText(order.getOrderNo());
        appointedPersonText.setText(order.getAppointedPerson());
        if(!order.getServiceClassInfo().getOrderWorkType().equals("worker")){
            appointedPersonCard.setVisibility(View.GONE);
        }
        if(state.equals("not_start") || state.equals("not_accepted") || state.equals("canceled")){
            startTimeTypeText.setText("预计上门");
            endTimeTypeText.setText("预计结束");
            startTimeText.setText(TimeUtil.timeStampToString(order.getExpectStartTime(),"yyyy-MM-dd HH:mm"));
            endTimeText.setText(TimeUtil.timeStampToString(order.getExpectEndTime(),"yyyy-MM-dd HH:mm"));
        }else if(state.equals("on_going")){
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

        addressText.setText(order.getDeliveryDetail());
        houseNumText.setText(order.getHouseNum());
        messageText.setText(order.getMessage());
        orderTypeText.setText(MapUtil.getOrderType(order.getOrderType()));
        if(order.getOrderType().equals("expedited_order")){
            orderTypeText.setTextColor(getResources().getColor(R.color.red));
        }
        tipText.setText("" + order.getTip());

        customerName.setText(order.getCustomerInfo().getName());
        customerTel.setText(order.getDeliveryPhone());

        stateText.setText(MapUtil.getOrderState(state));
        changeButton();

        if(state.equals("unpaid") || state.equals("not_accepted") || state.equals("not_start") || state.equals("on_going") || state.equals("canceled")){
            LogUtil.e("OrderDetailActivity","时薪： " + order.getSalaryHourly());
            priceText.setText(order.getSalaryHourly() + "元/时");
        }else{
            LogUtil.e("OrderDetailActivity","总价： " + order.getSalarySum());
            priceText.setText(order.getSalarySum() + "元");
        }

        customerNameCard.setOnClickListener(this);
        customerName.setOnClickListener(this);
        customerTelCard.setOnClickListener(this);
        customerTel.setOnClickListener(this);
        button.setOnClickListener(this);

        orderDetailPresenter.updateWorker(order.getLongitude(),order.getLatitude(),workerArraryAdapter,workerList);
    }

    private void initServiceWork()
    {
        serviceSubject = order.getServiceSubjectInfo();
        View serviceWorkView = findViewById(R.id.service_work);
        ImageView picture = serviceWorkView.findViewById(R.id.picture);
        TextView title = serviceWorkView.findViewById(R.id.title);
        TextView description = serviceWorkView.findViewById(R.id.description);
        TextView pricePerUnit = serviceWorkView.findViewById(R.id.price);

        Glide.with(this).load(HttpUtil.getResourceURL(serviceSubject.getImage())).into(picture);
        title.setText(serviceSubject.getSubjectName());
        description.setText(serviceSubject.getSubjectDes());
        pricePerUnit.setText(serviceSubject.getSalaryPerHour() +"（加急："+serviceSubject.getHurrySalaryPerHour()+"）元/单价");

    }

    private void initWorkerList(){
        workerSpiner = findViewById(R.id.assign);
        if(!role.equals("shopManager") || !state.equals("not_accepted")){
            workerSpiner.setVisibility(View.GONE);
        }
        Worker noWorker = new Worker();
        noWorker.setWorkerName("不指定");
        noWorker.setInternetId("0");
        workerList.add(noWorker);
        workerSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                workerId = workerList.get(i).getInternetId();
                changeButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }

    private void changeButton(){
        if(state.equals("on_going")){
            button.setText("结束订单");
        }else if(state.equals("not_start")){
            button.setText("开始订单");
        }else if(state.equals("not_accepted")) {
            if(role.equals("shopManager") && !workerId.equals("0")){
                button.setText("指派订单");
            }else {
                button.setText("立即抢单");
            }
        }else if(state.equals("un_evaluated")){
            customerCommentCard.setVisibility(View.VISIBLE);
            if(order.getCustomerDes() != null){
                customerCommentText.setText(order.getCustomerDes());
            }
            commentCard.setVisibility(View.VISIBLE);
            button.setText("评价订单");
        } else {
            customerCommentCard.setVisibility(View.VISIBLE);
            if(order.getCustomerDes() != null){
                customerCommentText.setText(order.getCustomerDes());
            }
            attendantCommentCard.setVisibility(View.VISIBLE);
            if(order.getWorkerDes() != null){
                attendantCommentText.setText(order.getWorkerDes());
            }
            button.setVisibility(View.GONE);
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

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.customer_name_card:
            case R.id.customer_name:{
                Intent intent = new Intent(OrderDetailActivity.this, CustomerInformationActivity.class);
                intent.putExtra("customer",order.getCustomerInfo());
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
                if(state.equals("on_going")){
                    orderDetailPresenter.endOrder(order.getInternetId());
                }else if(state.equals("not_start")){
                    orderDetailPresenter.startOrder(order.getInternetId());
                }else if(state.equals("not_accepted")) {
                    orderDetailPresenter.acceptOrder(order.getInternetId(),workerId);
                } else if(state.equals("un_evaluated")){
                    orderDetailPresenter.commentOrder(order.getInternetId(),commentText.getText().toString());
                }
            }
        }
    }
}