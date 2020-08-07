package com.example.sendwarmthattendant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.PensionInstitutionActivity;
import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.db.PensionInstitution;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PensionInstitutionAdapter extends RecyclerView.Adapter<PensionInstitutionAdapter.ViewHolder>
{
    private Context mContext;
    private List<PensionInstitution> mList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        ImageView picture;
        TextView name;
        TextView address;
        TextView tel;
        TextView contact;

        public  ViewHolder(View view)
        {
            super(view);
            this.view = view;
            picture = view.findViewById(R.id.picture);
            name = view.findViewById(R.id.name);
            tel = view.findViewById(R.id.tel);
            address = view.findViewById(R.id.address);
            contact = view.findViewById(R.id.contact);
        }
    }

    public PensionInstitutionAdapter(List<PensionInstitution> menuList){
        mList = menuList;
    }

    @Override
    public PensionInstitutionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pension_institutions, parent,false);
        final PensionInstitutionAdapter.ViewHolder holder = new PensionInstitutionAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int position = holder.getAdapterPosition();
                PensionInstitution pensionInstitution = mList.get(position);
                Intent intent = new Intent(mContext, PensionInstitutionActivity.class);
                intent.putExtra("pensionInstitution",pensionInstitution);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(PensionInstitutionAdapter.ViewHolder holder, int position)
    {
        PensionInstitution pensionInstitution = mList.get(position);
        Glide.with(mContext).load(pensionInstitution.getPicture()).into(holder.picture);
        holder.name.setText(pensionInstitution.getName());
        holder.address.setText("地址："+pensionInstitution.getAddress());
        holder.tel.setText("联系电话："+pensionInstitution.getTel());
        holder.contact.setText("联系人："+pensionInstitution.getContact());
    }
    @Override
    public int getItemCount()
    {
        return mList.size();
    }
}
