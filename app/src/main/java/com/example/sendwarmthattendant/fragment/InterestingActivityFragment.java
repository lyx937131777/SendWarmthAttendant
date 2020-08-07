package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.InterestingActivityAdapter;
import com.example.sendwarmthattendant.db.InterestingActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InterestingActivityFragment extends Fragment
{
    private RecyclerView recyclerView;
    private InterestingActivity[] interestingActivities = {new InterestingActivity("趣味活动1","2020-05-05 11:30",R.drawable.temp,"跳舞活动xxxxxxxxxxxx","幸福社区"),
            new InterestingActivity("趣味活动2","2020-05-05 11:30",R.drawable.temp,"跳舞活动xxxxxxxxxxxx","幸福社区"),
            new InterestingActivity("趣味活动3","2020-05-05 11:30",R.drawable.temp,"跳舞活动xxxxxxxxxxxx","幸福社区")};
    private List<InterestingActivity> interestingActivityList = new ArrayList<>();
    private InterestingActivityAdapter interestingActivityAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_interesting_activity, container, false);
        initInterestingActivities();
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        interestingActivityAdapter = new InterestingActivityAdapter(interestingActivityList);
        recyclerView.setAdapter(interestingActivityAdapter);
        return root;
    }

    private void initInterestingActivities()
    {
        interestingActivityList.clear();
        for(int i = 0; i < interestingActivities.length; i++)
            interestingActivityList.add(interestingActivities[i]);
    }
}
