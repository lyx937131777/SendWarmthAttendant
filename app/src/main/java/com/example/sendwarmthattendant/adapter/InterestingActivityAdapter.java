package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.InterestringActivityActivity;
import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.InterestingActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public
class InterestingActivityAdapter extends RecyclerView.Adapter<InterestingActivityAdapter.ViewHolder>
{
    private Context mContext;
    private List<InterestingActivity> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        ImageView picture;
        TextView title;
        TextView description;
        TextView time;
        CircleImageView author;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            picture = view.findViewById(R.id.picture);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            time = view.findViewById(R.id.time);
            author = view.findViewById(R.id.author);
        }
    }

    public InterestingActivityAdapter(List<InterestingActivity> menuList){
        mList = menuList;
    }

    @Override
    public InterestingActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_friends_circle, parent,false);
        final InterestingActivityAdapter.ViewHolder holder = new InterestingActivityAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                InterestingActivity interestingActivity = mList.get(position);
                Intent intent = new Intent(mContext, InterestringActivityActivity.class);
                intent.putExtra("interestingActivity",interestingActivity);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(InterestingActivityAdapter.ViewHolder holder, int position)
    {
        InterestingActivity interestingActivity = mList.get(position);
        Glide.with(mContext).load(interestingActivity.getPicture()).into(holder.picture);
        Glide.with(mContext).load(R.drawable.profile_uri).into(holder.author);
        holder.title.setText(interestingActivity.getTitle());
        holder.description.setText(interestingActivity.getDescription());
        holder.time.setText(interestingActivity.getTime());
    }
    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
