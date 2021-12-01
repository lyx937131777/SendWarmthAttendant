package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.BusinessCardActivity;
import com.example.sendwarmthattendant.EvaluateActivity;
import com.example.sendwarmthattendant.FeedbackActivity;
import com.example.sendwarmthattendant.MyInformationActivity;
import com.example.sendwarmthattendant.MyStarActivity;
import com.example.sendwarmthattendant.OrderActivity;
import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.SystemMessageActivity;
import com.example.sendwarmthattendant.db.Helper;
import com.example.sendwarmthattendant.db.Menu;
import com.example.sendwarmthattendant.db.Worker;
import com.example.sendwarmthattendant.fragment.PersonalCenterFragment;
import com.example.sendwarmthattendant.util.LogUtil;

import org.litepal.LitePal;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class Menu3Adapter extends RecyclerView.Adapter<Menu3Adapter.ViewHolder>
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

    public Menu3Adapter(List<Menu> menuList){
        mList = menuList;
    }

    @Override
    public Menu3Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu3, parent,false);
        final Menu3Adapter.ViewHolder holder = new Menu3Adapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                Menu menu = mList.get(position);
                switch (menu.getType()){
                    case "information":{
                        switch (menu.getName()){
                            case "myInformation":{
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
                                String role = pref.getString("role","");
                                String credential = pref.getString("credential","");
                                Intent intent = new Intent(mContext, MyInformationActivity.class);
                                intent.putExtra("role",role);
                                if(role.equals("helper")){
                                    Helper helper = LitePal.where("credential = ?",credential).findFirst(Helper.class);
                                    intent.putExtra("helper",helper);
                                }else{
                                    Worker worker = LitePal.where("credential = ?",credential).findFirst(Worker.class);
                                    intent.putExtra("worker",worker);
                                }
                                mContext.startActivity(intent);
                                break;
                            }
                            case "myStar":{
                                Intent intent = new Intent(mContext, MyStarActivity.class);
                                mContext.startActivity(intent);
                                break;
                            }
                            case "myBusinessCard":{
                                Intent intent = new Intent(mContext, BusinessCardActivity.class);
                                mContext.startActivity(intent);
                                break;
                            }
                            case "myEvaluate":{
                                Intent intent = new Intent(mContext, EvaluateActivity.class);
                                mContext.startActivity(intent);
                                break;
                            }
                            case "systemMessage":{
                                Intent intent = new Intent(mContext, SystemMessageActivity.class);
                                mContext.startActivity(intent);
                                break;
                            }
                            case "feedback":{
                                Intent intent = new Intent(mContext, FeedbackActivity.class);
                                mContext.startActivity(intent);
                                break;
                            }

                        }
                        break;
                    }
                }
                LogUtil.e("Menu3Adapter", menu.getName() + "  " + menu.getMenuName());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(Menu3Adapter.ViewHolder holder, int position)
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