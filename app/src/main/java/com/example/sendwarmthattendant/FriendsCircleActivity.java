package com.example.sendwarmthattendant;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.db.FriendsCircle;
import com.example.sendwarmthattendant.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsCircleActivity extends AppCompatActivity
{
    private FriendsCircle friendsCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_circle);

        initFriendsCircle();
    }

    private void initFriendsCircle()
    {
        friendsCircle= (FriendsCircle) getIntent().getSerializableExtra("friendsCircle");
        ImageView pictrue = findViewById(R.id.picture);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        CircleImageView authorProfile = findViewById(R.id.author_profile);
        TextView author = findViewById(R.id.author);
        TextView time = findViewById(R.id.time);
        TextView description = findViewById(R.id.description);

        Glide.with(this).load(friendsCircle.getPicture()).into(pictrue);
        collapsingToolbarLayout.setTitle(friendsCircle.getTitle());
        Glide.with(this).load(R.drawable.profile_uri).into(authorProfile);
        author.setText(friendsCircle.getAuthor());
        time.setText(friendsCircle.getTime());
        description.setText(friendsCircle.getDescription());
    }
}