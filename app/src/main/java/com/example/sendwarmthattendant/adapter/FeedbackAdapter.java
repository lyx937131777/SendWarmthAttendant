package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.Feedback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder>
{
    private Context mContext;
    private List<Feedback> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        TextView customer;
        TextView date;
        TextView star;
        TextView content;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            customer = view.findViewById(R.id.customer);
            date = view.findViewById(R.id.date);
            star = view.findViewById(R.id.star);
            content = view.findViewById(R.id.content);
        }
    }

    public FeedbackAdapter(List<Feedback> feedbackList)
    {
        mList = feedbackList;
    }

    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_feedback,parent,false);
        final FeedbackAdapter.ViewHolder holder = new FeedbackAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int position)
    {
        Feedback feedback = mList.get(position);
        holder.customer.setText(feedback.getCustomer());
        holder.date.setText(feedback.getDate());
        if(feedback.getStar() == 5){
            holder.star.setText("满意度：★★★★★");
        }else{
            holder.star.setText("满意度：★★★★☆");
        }
        holder.content.setText(feedback.getContent());
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
