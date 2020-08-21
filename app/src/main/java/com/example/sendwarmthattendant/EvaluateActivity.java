package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sendwarmthattendant.adapter.EvaluateAdapter;
import com.example.sendwarmthattendant.db.Evaluate;

import java.util.ArrayList;
import java.util.List;

public class EvaluateActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private Evaluate[] evaluates = {new Evaluate("001","2020-08-07",4.5,"服务态度非常好，非常满意，稍微迟到了一点不是很好，希望下次能按时到"),
        new Evaluate("002","2020-08-07",5,"服务态度非常好，非常满意"),
        new Evaluate("003","2020-08-07",5,""),
        new Evaluate("004","2020-08-07",4.5,"无")};
    private List<Evaluate> evaluateList = new ArrayList<>();
    private EvaluateAdapter evaluateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initEvaluate();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        evaluateAdapter = new EvaluateAdapter(evaluateList);
        recyclerView.setAdapter(evaluateAdapter);
    }

    private void initEvaluate()
    {
        evaluateList.clear();
        for(int i = 0; i < evaluates.length; i++){
            evaluateList.add(evaluates[i]);
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