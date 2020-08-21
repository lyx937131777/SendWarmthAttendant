package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sendwarmthattendant.db.Task;
import com.example.sendwarmthattendant.util.MapUtil;

public class TaskDetailActivity extends AppCompatActivity
{
    private Task task;

    private TextView state;
    private Button startComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        task = (Task) getIntent().getSerializableExtra("task");

        state = findViewById(R.id.state);
        startComplete = findViewById(R.id.start_complete);

        state.setText(MapUtil.getTaskTypeState(task.getType()));
        if(task.getType().equals("running")){
            startComplete.setText("完成订单");
        }else if(task.getType().equals("unstart")){
            startComplete.setText("开始订单");
        }else {
            startComplete.setVisibility(View.GONE);
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