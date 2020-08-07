package com.example.sendwarmthattendant;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.db.ServiceWork;

import androidx.appcompat.app.AppCompatActivity;

public class OrderingActivity extends AppCompatActivity
{
    private ServiceWork serviceWork;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        initServiceWork();

    }

    private void initServiceWork()
    {
        serviceWork = (ServiceWork) getIntent().getSerializableExtra("serviceWork");
        View serviceWorkView = findViewById(R.id.service_work);
        ImageView picture = serviceWorkView.findViewById(R.id.picture);
        TextView title = serviceWorkView.findViewById(R.id.title);
        TextView description = serviceWorkView.findViewById(R.id.description);
        TextView price = serviceWorkView.findViewById(R.id.price);

        Glide.with(this).load(serviceWork.getPicture()).into(picture);
        title.setText(serviceWork.getName());
        description.setText(serviceWork.getDescription());
        price.setText(serviceWork.getPrice()+"元/"+serviceWork.getUnit());
    }
}