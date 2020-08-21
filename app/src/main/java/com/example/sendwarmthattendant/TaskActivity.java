package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sendwarmthattendant.db.Task;
import com.example.sendwarmthattendant.fragment.adapter.TaskPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TaskActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ViewPager viewPager = findViewById(R.id.view_pager);
        TaskPagerAdapter taskPagerAdapter = new TaskPagerAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(taskPagerAdapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("index",0));
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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