package com.example.sendwarmthattendant;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sendwarmthattendant.db.PensionInstitution;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PensionInstitutionActivity extends AppCompatActivity
{
    private PensionInstitution pensionInstitution;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pension_institution);

        initPensionInstitution();
    }

    private void initPensionInstitution()
    {
        pensionInstitution = (PensionInstitution) getIntent().getSerializableExtra("pensionInstitution");
        ImageView pictrue = findViewById(R.id.picture);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        TextView address = findViewById(R.id.address);
        TextView tel = findViewById(R.id.tel);
        TextView contact = findViewById(R.id.contact);
        TextView description = findViewById(R.id.description);

        Glide.with(this).load(pensionInstitution.getPicture()).into(pictrue);
        collapsingToolbarLayout.setTitle(pensionInstitution.getName());
        address.setText(pensionInstitution.getAddress());
        tel.setText(pensionInstitution.getTel());
        contact.setText(pensionInstitution.getContact());
        description.setText(pensionInstitution.getDescription());
    }
}