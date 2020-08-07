package com.example.sendwarmthattendant;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.db.InterestingActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class InterestringActivityActivity extends AppCompatActivity
{
    private InterestingActivity interestingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interestring_activity);

        initInterestingActivity();
    }

    private void initInterestingActivity()
    {
        interestingActivity = (InterestingActivity) getIntent().getSerializableExtra("interestingActivity");
        ImageView pictrue = findViewById(R.id.picture);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        CircleImageView authorProfile = findViewById(R.id.author_profile);
        TextView author = findViewById(R.id.author);
        TextView time = findViewById(R.id.time);
        TextView description = findViewById(R.id.description);

        Glide.with(this).load(interestingActivity.getPicture()).into(pictrue);
        collapsingToolbarLayout.setTitle(interestingActivity.getTitle());
        Glide.with(this).load(R.drawable.profile_uri).into(authorProfile);
        author.setText(interestingActivity.getAuthor());
        time.setText(interestingActivity.getTime());
        description.setText(interestingActivity.getDescription());
    }
}