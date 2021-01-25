package com.example.sendwarmthattendant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.OrderActivity;
import com.example.sendwarmthattendant.adapter.Menu3Adapter;
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
    private Menu[] myOrderMenus = {new Menu("running",R.drawable.running,"进行中"),
            new Menu("unstart",R.drawable.unstart,"未开始"),
            new Menu("unevaluated",R.drawable.to_be_evaluated,"待评价"),
            new Menu("completed",R.drawable.completed,"已完成")};
    private List<Menu> myOrderMenuList = new ArrayList<>();
    private MenuAdapter myOrderMenuAdapter;

    private RecyclerView mMenuRecycler;
    private Menu[] mMenus = {new Menu("myInformation",R.drawable.my_information,"我的信息"),
            new Menu("myStar",R.drawable.my_star,"我的星级"),
            new Menu("myBusinessCard",R.drawable.my_business_card,"我的名片"),
            new Menu("myEvaluate",R.drawable.my_evaluate,"客户评价"),
            new Menu("systemMessage",R.drawable.system_message,"系统消息"),
            new Menu("feedback",R.drawable.feedback,"意见反馈")};
    private List<Menu> mMenuList = new ArrayList<>();
    private Menu3Adapter mMenuAdapter;

    private View allOrder;

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
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 3);
        mMenuRecycler.setLayoutManager(layoutManager2);
        mMenuAdapter = new Menu3Adapter(mMenuList);
        mMenuRecycler.setAdapter(mMenuAdapter);

        allOrder = root.findViewById(R.id.all_orders);
        allOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("index",0);
                startActivity(intent);
            }
        });
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