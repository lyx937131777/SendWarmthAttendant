package com.example.sendwarmthattendant;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.db.HealthBroadcast;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class HealthBroadcastActivity extends AppCompatActivity
{
    private HealthBroadcast healthBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_broadcast);

        initHealthBroadcast();
    }

    private void initHealthBroadcast()
    {
        healthBroadcast = (HealthBroadcast) getIntent().getSerializableExtra("healthBroadcast");
        ImageView pictrue = findViewById(R.id.picture);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        CircleImageView authorProfile = findViewById(R.id.author_profile);
        TextView author = findViewById(R.id.author);
        TextView time = findViewById(R.id.time);
        TextView description = findViewById(R.id.description);

        Glide.with(this).load(healthBroadcast.getPicture()).into(pictrue);
        collapsingToolbarLayout.setTitle(healthBroadcast.getTitle());
        Glide.with(this).load(R.drawable.profile_uri).into(authorProfile);
        author.setText("王专家");
        time.setText(healthBroadcast.getTime());
        description.setText(healthBroadcast.getDescription());
    }
}