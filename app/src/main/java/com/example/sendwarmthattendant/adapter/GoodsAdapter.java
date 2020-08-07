package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.Goods;
import com.example.sendwarmthattendant.util.LogUtil;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder>
{
    private Context mContext;
    private List<Goods> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        ImageView picture;
        TextView title;
        TextView description;
        TextView price;
        Button addButton;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            picture = view.findViewById(R.id.picture);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            price = view.findViewById(R.id.price);
            addButton = view.findViewById(R.id.add_button);
        }
    }

    public GoodsAdapter(List<Goods> menuList){
        mList = menuList;
    }

    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods, parent,false);
        final GoodsAdapter.ViewHolder holder = new GoodsAdapter.ViewHolder(view);
        holder.addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                Goods goods = mList.get(position);
                LogUtil.e("GoodsAdapter",goods.getName()+" "+goods.getDescription());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(GoodsAdapter.ViewHolder holder, int position)
    {
        Goods goods = mList.get(position);
        Glide.with(mContext).load(goods.getPicture()).into(holder.picture);
        holder.title.setText(goods.getName());
        holder.description.setText(goods.getDescription());
        holder.price.setText("￥"+goods.getPrice());
    }
    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
