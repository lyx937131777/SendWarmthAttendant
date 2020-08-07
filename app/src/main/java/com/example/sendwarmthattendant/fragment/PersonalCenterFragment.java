package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.MenuAdapter;
import com.example.sendwarmthattendant.db.Menu;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PersonalCenterFragment extends Fragment
{
    private RecyclerView myOrderMenuRecycler;
    private Menu[] myOrderMenus = {new Menu("toBePaid",R.drawable.to_be_paid,"待付款"),
            new Menu("toBeReceived",R.drawable.to_be_received,"待收货"),
            new Menu("toBeEvaluated",R.drawable.to_be_evaluated,"待评价"),
            new Menu("completed",R.drawable.completed,"已完成")};
    private List<Menu> myOrderMenuList = new ArrayList<>();
    private MenuAdapter myOrderMenuAdapter;

    private RecyclerView mMenuRecycler;
    private Menu[] mMenus = {new Menu("myInformation",R.drawable.my_information,"我的信息"),
            new Menu("myEvaluate",R.drawable.my_evaluate,"我的评价"),
            new Menu("myStar",R.drawable.my_star,"我的星级"),
            new Menu("myMessage",R.drawable.my_message,"我的留言"),
            new Menu("systemMessage",R.drawable.system_message,"系统消息"),
            new Menu("feedback",R.drawable.feedback,"意见反馈"),
            new Menu("myBusinessCard",R.drawable.my_business_card,"我的名片"),
            new Menu("hotline",R.drawable.hotline,"热线电话")};
    private List<Menu> mMenuList = new ArrayList<>();
    private MenuAdapter mMenuAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_personal_center, container, false);

        initMenus();
        myOrderMenuRecycler = root.findViewById(R.id.recycler_menu_my_order);
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 4);
        myOrderMenuRecycler.setLayoutManager(layoutManager1);
        myOrderMenuAdapter = new MenuAdapter(myOrderMenuList);
        myOrderMenuRecycler.setAdapter(myOrderMenuAdapter);

        mMenuRecycler = root.findViewById(R.id.recycler_menu_me);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 4);
        mMenuRecycler.setLayoutManager(layoutManager2);
        mMenuAdapter = new MenuAdapter(mMenuList);
        mMenuRecycler.setAdapter(mMenuAdapter);

        return root;
    }

    private void initMenus()
    {
        myOrderMenuList.clear();
        for(int i = 0; i < myOrderMenus.length; i++) {
            myOrderMenus[i].setType("order");
            myOrderMenuList.add(myOrderMenus[i]);
        }

        mMenuList.clear();
        for(int i = 0; i < mMenus.length; i++){
            mMenus[i].setType("information");
            mMenuList.add(mMenus[i]);
        }

    }

}