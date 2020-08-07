package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.MyInformationActivity;
import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.ServiceWorkActivity;
import com.example.sendwarmthattendant.db.Menu;
import com.example.sendwarmthattendant.util.LogUtil;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>
{
    private Context mContext;
    private List<Menu> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        ImageView menuImage;
        TextView menuName;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            menuImage = view.findViewById(R.id.menu_image);
            menuName = view.findViewById(R.id.menu_name);
        }
    }

    public MenuAdapter(List<Menu> menuList){
        mList = menuList;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent,false);
        final MenuAdapter.ViewHolder holder = new MenuAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                Menu menu = mList.get(position);
                switch (menu.getType()){
                    case "ServiceWork":{
                        Intent intent = new Intent(mContext, ServiceWorkActivity.class);
                        intent.putExtra("menuName",menu.getMenuName());
                        intent.putExtra("type",menu.getName());
                        mContext.startActivity(intent);
                        break;
                    }
                    case "information":{
                        switch (menu.getName()){
                            case "myInformation":{
                                Intent intent = new Intent(mContext, MyInformationActivity.class);
                                mContext.startActivity(intent);
                                break;
                            }

                        }
                       break;
                    }
                }
                LogUtil.e("MenuAdapter", menu.getName() + "  " + menu.getMenuName());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position)
    {
        Menu menu = mList.get(position);
        Glide.with(mContext).load(menu.getImageId()).into(holder.menuImage);
        holder.menuName.setText(menu.getMenuName());
    }
    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
