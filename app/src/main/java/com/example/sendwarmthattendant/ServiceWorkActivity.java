package com.example.sendwarmthattendant;

import android.os.Bundle;

import com.example.sendwarmthattendant.adapter.ServiceWorkAdapter;
import com.example.sendwarmthattendant.db.ServiceWork;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceWorkActivity extends AppCompatActivity
{
    private ServiceWork[] serviceWorks = {new ServiceWork("业务名称1","propertyMaintenance","相关描述xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",50,"次",R.drawable.temp),
            new ServiceWork("业务名称2","propertyMaintenance","相关描述xxxxxxxxxxxx",10,"小时",R.drawable.temp),
            new ServiceWork("业务名称3","propertyMaintenance","相关描述xxxxxxxxxxxxxxxxxxxxx",50,"次",R.drawable.temp)};
    private List<ServiceWork> serviceWorkList = new ArrayList<>();
    private ServiceWorkAdapter serviceWorkAdapter;
    private RecyclerView serviceWorkRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_work);

        Toolbar toolbar = findViewById(R.id.toolbar);
        String title = getIntent().getStringExtra("menuName");
        toolbar.setTitle(title);
        getIntent().getStringExtra("type");

        initServiceWorks();
        serviceWorkRecycler = findViewById(R.id.recycler_service_work);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        serviceWorkRecycler.setLayoutManager(linearLayoutManager);
        serviceWorkAdapter = new ServiceWorkAdapter(serviceWorkList);
        serviceWorkRecycler.setAdapter(serviceWorkAdapter);

    }

    private void initServiceWorks()
    {
        serviceWorkList.clear();
        for(int i = 0; i < serviceWorks.length; i++){
            serviceWorkList.add(serviceWorks[i]);
        }
    }
}