package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sendwarmthattendant.adapter.EvaluateAdapter;
import com.example.sendwarmthattendant.adapter.FeedbackAdapter;
import com.example.sendwarmthattendant.db.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private Feedback[] feedbacks = {new Feedback("张三","2020-08-07",5,"非常满意，非常棒的APP"),
            new Feedback("李四","2020-08-07",4,"非常不错，但是xxxx方面稍微有所欠缺"),
            new Feedback("匿名用户","2020-08-05",5,"非常满意"),
            new Feedback("匿名用户","2020-08-04",3,"感觉一般"),};
    private List<Feedback> feedbackList = new ArrayList<>();
    private FeedbackAdapter feedbackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initFeedback();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedbackAdapter = new FeedbackAdapter(feedbackList);
        recyclerView.setAdapter(feedbackAdapter);
    }

    private void initFeedback()
    {
        feedbackList.clear();
        for(int i = 0; i <feedbacks.length; i++){
            feedbackList.add(feedbacks[i]);
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