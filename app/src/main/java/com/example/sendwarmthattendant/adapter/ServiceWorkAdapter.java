package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.OrderingActivity;
import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.ServiceWork;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public
class ServiceWorkAdapter extends RecyclerView.Adapter<ServiceWorkAdapter.ViewHolder>
{
    private Context mContext;
    private List<ServiceWork> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        ImageView picture;
        TextView title;
        TextView description;
        TextView price;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            picture = view.findViewById(R.id.picture);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            price = view.findViewById(R.id.price);
        }
    }

    public ServiceWorkAdapter(List<ServiceWork> serviceWorkList){
        mList = serviceWorkList;
    }

    @Override
    public ServiceWorkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_service_work, parent,false);
        final ServiceWorkAdapter.ViewHolder holder = new ServiceWorkAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                ServiceWork serviceWork = mList.get(position);
                Intent intent = new Intent(mContext, OrderingActivity.class);
                intent.putExtra("serviceWork",serviceWork);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ServiceWorkAdapter.ViewHolder holder, int position)
    {
        ServiceWork serviceWork = mList.get(position);
        Glide.with(mContext).load(serviceWork.getPicture()).into(holder.picture);
        holder.title.setText(serviceWork.getName());
//        holder.description.setText(serviceWork.getDescription());
//        holder.price.setText(serviceWork.getPrice()+"元/"+serviceWork.getUnit());
    }
    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
