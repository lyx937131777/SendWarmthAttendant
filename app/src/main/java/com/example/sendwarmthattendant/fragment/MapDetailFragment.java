package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MapDetailFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int index;

    public static MapDetailFragment newInstance(int index)
    {
        MapDetailFragment fragment = new MapDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        index = 0;
        if (getArguments() != null)
        {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_map_detail, container, false);

        return root;
    }
}
