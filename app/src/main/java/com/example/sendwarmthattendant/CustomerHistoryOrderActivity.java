package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sendwarmthattendant.adapter.OrderAdapter;
import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.db.Order;
import com.example.sendwarmthattendant.presenter.CustomerHistoryOrderPresenter;

import java.util.ArrayList;
import java.util.List;

public class CustomerHistoryOrderActivity extends AppCompatActivity
{
    private String customerId;

    private RecyclerView recyclerView;
    private List<Order> orderList = new ArrayList<>();
    private OrderAdapter orderAdapter;

    private CustomerHistoryOrderPresenter customerHistoryOrderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_history_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(this)).build();
        customerHistoryOrderPresenter = myComponent.customerHistoryOrderPresenter();

        customerId = getIntent().getStringExtra("customerId");

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);

        customerHistoryOrderPresenter.updateOrderList(orderAdapter,customerId);
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