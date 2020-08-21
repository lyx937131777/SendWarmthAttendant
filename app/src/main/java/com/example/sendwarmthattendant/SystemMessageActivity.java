package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sendwarmthattendant.adapter.SystemMessageAdapter;
import com.example.sendwarmthattendant.db.SystemMessage;

import java.util.ArrayList;
import java.util.List;

public class SystemMessageActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private SystemMessage[] systemMessages = {new SystemMessage("15:04","今天","您有一个订单已取消"),
            new SystemMessage("14:26","今天","您有一个新的订单开始了"),
            new SystemMessage("15:00","昨天","您有一个订单已完成"),
            new SystemMessage("14:43","昨天","您有一个新的订单开始了"),
            new SystemMessage("16:08","周一","您有一个订单已完成"),
            new SystemMessage("14:25","周一","您有一个新的订单开始了")};
    private List<SystemMessage> systemMessageList = new ArrayList<>();
    private SystemMessageAdapter systemMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initSystemMessage();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        systemMessageAdapter = new SystemMessageAdapter(systemMessageList);
        recyclerView.setAdapter(systemMessageAdapter);

    }

    private void initSystemMessage()
    {
        systemMessageList.clear();
        for(int i = 0; i < systemMessages.length; i++){
            systemMessageList.add(systemMessages[i]);
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
}