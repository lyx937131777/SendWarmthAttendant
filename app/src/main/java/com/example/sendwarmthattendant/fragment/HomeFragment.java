package com.example.sendwarmthattendant.fragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.OrderStateAdapter;
import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.presenter.HomePresenter;
import com.example.sendwarmthattendant.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment
{
    private RecyclerView recyclerView;
    private String[] orderStates = {"mine","not_accepted"};
    private List<String> orderStateList = new ArrayList<>();
    private OrderStateAdapter orderStateAdapter;

    private SoundPool soundPool;
    private MediaPlayer mediaPlayer;
    private HomePresenter homePresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(getContext())).build();
        homePresenter = myComponent.homePresenter();

        initOrderStates();
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderStateAdapter = new OrderStateAdapter(orderStateList);
        recyclerView.setAdapter(orderStateAdapter);

//        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,5);
//        soundPool.load(getContext(), R.raw.new_order,1);
        mediaPlayer = MediaPlayer.create(getContext(),R.raw.new_order);
        return root;
    }

    public void playSound(){
        LogUtil.e("HomeFragment","playSound!");
//        soundPool.play(1,1,1,0,0,1);
        mediaPlayer.start();
    }

    private void initOrderStates()
    {
        orderStateList.clear();
        for(int i = 0; i < orderStates.length; i++){
            orderStateList.add(orderStates[i]);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        homePresenter.updateOrderList(orderStateAdapter);
    }
}