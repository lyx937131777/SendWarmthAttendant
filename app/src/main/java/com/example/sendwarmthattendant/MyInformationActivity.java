package com.example.sendwarmthattendant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.db.User;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyInformationActivity extends AppCompatActivity
{
    private User user;

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        initUser();

        logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MyInformationActivity.this).edit();
                editor.remove("userID");
                editor.remove("password");
                editor.apply();
                Intent intent_logout = new Intent(MyInformationActivity.this, LoginActivity.class);
                startActivity(intent_logout);
                MainActivity.instance.finish();
                finish();
            }
        });
    }

    private void initUser()
    {
        CircleImageView profile = findViewById(R.id.profile);
        Glide.with(this).load(R.drawable.profile_uri).into(profile);

    }
}