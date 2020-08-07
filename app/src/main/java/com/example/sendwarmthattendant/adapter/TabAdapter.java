package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.GoodsType;
import com.example.sendwarmthattendant.util.LogUtil;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.ViewHolder>
{
    private Context mContext;
    private List<GoodsType> mList;
    private String chosenName;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        View chosenLine;
        TextView typeName;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            chosenLine = view.findViewById(R.id.chosen_line);
            typeName = view.findViewById(R.id.type_name);
        }
    }

    public TabAdapter(List<GoodsType> menuList){
        mList = menuList;
        if(menuList.size() > 0)
            chosenName = menuList.get(0).getName();
    }

    @Override
    public TabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab, parent,false);
        final TabAdapter.ViewHolder holder = new TabAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                GoodsType goodsType = mList.get(position);
                chosenName = goodsType.getName();
                notifyDataSetChanged();
                LogUtil.e("MenuAdapter", goodsType.getName() + "  " + goodsType.getTypeName());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TabAdapter.ViewHolder holder, int position)
    {
        GoodsType goodsType = mList.get(position);
        holder.typeName.setText(goodsType.getTypeName());
        if(goodsType.getName().equals(chosenName)){
            holder.chosenLine.setBackgroundResource(R.color.colorPrimary);
            holder.view.setBackgroundResource(R.color.white);
            holder.typeName.getPaint().setFakeBoldText(true);
        }else{
            holder.chosenLine.setBackgroundResource(R.color.gainsboro);
            holder.view.setBackgroundResource(R.color.gainsboro);
            holder.typeName.getPaint().setFakeBoldText(false);
        }

    }
    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
