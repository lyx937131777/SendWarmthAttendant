package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.Goods;
import com.example.sendwarmthattendant.db.GoodsType;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GoodsTypeAdapter extends RecyclerView.Adapter<GoodsTypeAdapter.ViewHolder>
{
    private Context mContext;
    private List<GoodsType> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        TextView goodsType;
        RecyclerView recyclerView;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            goodsType = view.findViewById(R.id.goods_type);
            recyclerView = view.findViewById(R.id.recycler_goods);
        }
    }

    public GoodsTypeAdapter(List<GoodsType> menuList){
        mList = menuList;
    }

    @Override
    public GoodsTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_type, parent,false);
        final GoodsTypeAdapter.ViewHolder holder = new GoodsTypeAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                GoodsType goodsType = mList.get(position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(GoodsTypeAdapter.ViewHolder holder, int position)
    {
        GoodsType goodsType = mList.get(position);
        holder.goodsType.setText(goodsType.getTypeName());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        Goods[] goods = {new Goods("物品1",R.drawable.temp,goodsType,"一些描述",10),
                new Goods("物品2",R.drawable.temp,goodsType,"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",11),
                new Goods("物品3",R.drawable.temp,goodsType,"1233211231231231",12)};
        List<Goods> goodsList = new ArrayList<>();
        for(int i = 0; i < goods.length; i++)
            goodsList.add(goods[i]);
        GoodsAdapter goodsAdapter = new GoodsAdapter(goodsList);
        holder.recyclerView.setAdapter(goodsAdapter);
    }
    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
