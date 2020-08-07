package com.example.sendwarmthattendant.fragment.adapter;

import android.content.Context;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.fragment.MapDetailFragment;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MapPagerAdapter extends FragmentStatePagerAdapter
{
    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.title_running_orders, R.string.title_all_orders};
    private final Context mContext;

    public MapPagerAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a EventRecordFragment (defined as a static inner class below).
        return MapDetailFragment.newInstance(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount()
    {
        return 2;
    }
}
