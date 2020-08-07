package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.GoodsTypeAdapter;
import com.example.sendwarmthattendant.adapter.TabAdapter;
import com.example.sendwarmthattendant.db.GoodsType;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingMallFragment extends Fragment
{
    private RecyclerView tabRecycler;
    private GoodsType[] goodsTypes = {new GoodsType("healthyFood","健康食品"),new GoodsType("dailyGoods","生活用品"),
        new GoodsType("medicalEquipment","医疗器械"),new GoodsType("healthProduct","保健品"),
            new GoodsType("else","其他")};
    private List<GoodsType> goodsTypeList = new ArrayList<>();
    private TabAdapter tabAdapter;

    private RecyclerView goodsTypeRecycler;
    private GoodsTypeAdapter goodsTypeAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_shopping_mall, container, false);

        initGoodsTypes();
        tabRecycler = root.findViewById(R.id.recycler_tab);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        tabRecycler.setLayoutManager(linearLayoutManager);
        tabAdapter = new TabAdapter(goodsTypeList);
        tabRecycler.setAdapter(tabAdapter);

        goodsTypeRecycler = root.findViewById(R.id.recycler_goods_type);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        goodsTypeRecycler.setLayoutManager(linearLayoutManager2);
        goodsTypeAdapter = new GoodsTypeAdapter(goodsTypeList);
        goodsTypeRecycler.setAdapter(goodsTypeAdapter);

        return root;
    }

    private void initGoodsTypes()
    {
        goodsTypeList.clear();
        for (int i = 0; i < goodsTypes.length; i++)
            goodsTypeList.add(goodsTypes[i]);
    }
}
