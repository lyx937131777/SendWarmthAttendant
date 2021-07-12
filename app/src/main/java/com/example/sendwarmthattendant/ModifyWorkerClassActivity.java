package com.example.sendwarmthattendant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.sendwarmthattendant.dagger2.DaggerMyComponent;
import com.example.sendwarmthattendant.dagger2.MyComponent;
import com.example.sendwarmthattendant.dagger2.MyModule;
import com.example.sendwarmthattendant.db.ServiceSubject;
import com.example.sendwarmthattendant.presenter.ModifyWorkerClassPresenter;

import java.util.ArrayList;
import java.util.List;

public class ModifyWorkerClassActivity extends AppCompatActivity {

    private Spinner workTypeSpinner1;
    private List<ServiceSubject> workTypeList1 = new ArrayList<>();
    private ArrayAdapter<ServiceSubject> workTypeArrayAdapter1;
    private int workType1;

    private Spinner workTypeSpinner2;
    private List<ServiceSubject> workTypeList2 = new ArrayList<>();
    private ArrayAdapter<ServiceSubject> workTypeArrayAdapter2;
    private int workType2;

    private ModifyWorkerClassPresenter modifyWorkerClassPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_worker_class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        MyComponent myComponent = DaggerMyComponent.builder().myModule(new MyModule(this)).build();
        modifyWorkerClassPresenter = myComponent.modifyWorkerClassPresenter();

        initworkTypeList();
        workTypeArrayAdapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,workTypeList1);
        workTypeArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypeSpinner1.setAdapter(workTypeArrayAdapter1);
        workTypeArrayAdapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,workTypeList2);
        workTypeArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypeSpinner2.setAdapter(workTypeArrayAdapter2);
        modifyWorkerClassPresenter.updateServiceSubject(workTypeArrayAdapter1, workTypeList1, workTypeSpinner1, workTypeArrayAdapter2, workTypeList2, workTypeSpinner2);

    }

    private void initworkTypeList() {
        workTypeSpinner1 = findViewById(R.id.work_type_1);
        workTypeSpinner2 = findViewById(R.id.work_type_2);
        workTypeSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                workType1 = Integer.parseInt(workTypeList1.get(i).getInternetId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        workTypeSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                workType2 = Integer.parseInt(workTypeList2.get(i).getInternetId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.commit:{
                modifyWorkerClassPresenter.modifyHelper(workType1, workType2);
                break;
            }
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.commit_menu, menu);
        return true;
    }
}