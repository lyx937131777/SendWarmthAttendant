package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.adapter.PensionInstitutionAdapter;
import com.example.sendwarmthattendant.db.PensionInstitution;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PensionInstitutionsFragment extends Fragment
{
    private RecyclerView recyclerView;
    private PensionInstitution[] pensionInstitutions = {new PensionInstitution("养老中心1","xxx街道xx号","12312345678","李xx","该养老中心地处....是一个.....欢迎.....",R.drawable.temp),
            new PensionInstitution("养老中心2","xxx街道xx号","12312345678","王xx","该养老中心地处....是一个.....欢迎.....",R.drawable.temp),
            new PensionInstitution("养老中心3","xxx街道xx号","12312345678","李xx","该养老中心地处....是一个.....欢迎.....",R.drawable.temp)};
    private List<PensionInstitution> pensionInstitutionList = new ArrayList<>();
    private PensionInstitutionAdapter pensionInstitutionAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_pension_institutions, container, false);
        initPensionInstitutions();
        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pensionInstitutionAdapter = new PensionInstitutionAdapter(pensionInstitutionList);
        recyclerView.setAdapter(pensionInstitutionAdapter);
        return root;
    }

    private void initPensionInstitutions()
    {
        pensionInstitutionList.clear();
        for(int i = 0; i < pensionInstitutions.length; i++)
            pensionInstitutionList.add(pensionInstitutions[i]);
    }
}
