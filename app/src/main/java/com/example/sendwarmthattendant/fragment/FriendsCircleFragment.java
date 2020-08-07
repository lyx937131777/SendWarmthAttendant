package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.FriendsCircleAdapter;
import com.example.sendwarmthattendant.db.FriendsCircle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsCircleFragment extends Fragment
{
    private RecyclerView recyclerView;
    private FriendsCircle[] friendsCircles = {new FriendsCircle("面朝大海春暖花开",R.drawable.temp,"李思思","大家好xxxxxxxxxxxx","2020-03-05 11:00"),
            new FriendsCircle("面朝大海春暖花开",R.drawable.temp,"李思思","大家好xxxxxxxxxxxx","2020-03-05 11:00"),
            new FriendsCircle("面朝大海春暖花开",R.drawable.temp,"李思思","大家好xxxxxxxxxxxx","2020-03-05 11:00")};
    private List<FriendsCircle> friendsCircleList = new ArrayList<>();
    private FriendsCircleAdapter friendsCircleAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_friends_circle, container, false);
        initFriendsCircle();
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendsCircleAdapter = new FriendsCircleAdapter(friendsCircleList);
        recyclerView.setAdapter(friendsCircleAdapter);
        return root;
    }

    private void initFriendsCircle()
    {
        friendsCircleList.clear();
        for(int i = 0; i < friendsCircles.length; i++)
            friendsCircleList.add(friendsCircles[i]);
    }
}
