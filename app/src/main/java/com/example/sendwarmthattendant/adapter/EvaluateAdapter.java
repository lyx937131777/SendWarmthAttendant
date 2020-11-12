package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.OrderDetailActivity;
import com.example.sendwarmthattendant.db.Evaluate;
import com.example.sendwarmthattendant.db.Order;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder>
{
    private Context mContext;
    private List<Evaluate> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        TextView number;
        TextView date;
        TextView star;
        TextView evaluate;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            number = view.findViewById(R.id.number);
            date = view.findViewById(R.id.date);
            star = view.findViewById(R.id.star);
            evaluate = view.findViewById(R.id.evaluate);
        }
    }

    public EvaluateAdapter(List<Evaluate> evaluateList)
    {
        mList = evaluateList;
    }

    @NonNull
    @Override
    public EvaluateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate,parent,false);
        final EvaluateAdapter.ViewHolder holder = new EvaluateAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = holder.getAdapterPosition();
                Evaluate evaluate = mList.get(position);
                //TODO
//                Intent intent = new Intent(mContext, OrderDetailActivity.class);
//                Order order = new Order(evaluate.getNumber(),"customer","","time","complete","","",50);
//                intent.putExtra("order", order);
//                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluateAdapter.ViewHolder holder, int position)
    {
        Evaluate evaluate = mList.get(position);
        holder.number.setText("订单编号："+evaluate.getNumber());
        holder.date.setText(evaluate.getDate());
        if(evaluate.getStar() == 5){
            holder.star.setText("满意度：★★★★★");
        }else{
            holder.star.setText("满意度：★★★★☆");
        }
        holder.evaluate.setText(evaluate.getEvaluate());
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
