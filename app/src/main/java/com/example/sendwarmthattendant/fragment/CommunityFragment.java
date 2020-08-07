package com.example.sendwarmthattendant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendwarmthattendant.fragment.adapter.MyFragAdapter;
import com.example.sendwarmthattendant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class CommunityFragment extends Fragment
{
    private Toolbar toolbar;

    private ViewPager viewPager;
    private List<Fragment> listFragment;
    private BottomNavigationView navView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_community, container, false);

        toolbar = root.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        navView = root.findViewById(R.id.top_nav_view);
        viewPager = root.findViewById(R.id.view_pager);
        listFragment = new ArrayList<>();
        listFragment.add(new InterestingActivityFragment());
        listFragment.add(new HealthBroadcastFragment());
        listFragment.add(new FriendsCircleFragment());
        listFragment.add(new PensionInstitutionsFragment());
        MyFragAdapter myAdapter = new MyFragAdapter(getChildFragmentManager(), getContext(), listFragment);
        viewPager.setAdapter(myAdapter);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
//                    case R.id.navigation_interesting_activity:
//                        viewPager.setCurrentItem(0);
//                        return true;
//                    case R.id.navigation_health_broadcast:
//                        viewPager.setCurrentItem(1);
//                        return true;
//                    case R.id.navigation_friends_circle:
//                        viewPager.setCurrentItem(2);
//                        return true;
//                    case R.id.navigation_pension_institutions:
//                        viewPager.setCurrentItem(3);
//                        return true;
//                    default:
//                        break;
                }
                return false;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                navView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        return root;
    }


    public Toolbar getToolbar(){
        return toolbar;
    }

//    @Override
//    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater)
//    {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.community_menu, menu);
//    }
}