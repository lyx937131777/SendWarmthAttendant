package com.example.sendwarmthattendant.fragment.adapter;

import android.content.Context;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.fragment.OrderFragment;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class OrderPagerAdapter extends FragmentStatePagerAdapter
{
    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.title_all_orders, R.string.title_running_orders,R.string.title_unstart_orders,R.string.title_unevaluated_orders,R.string.title_completed_orders};
    private final Context mContext;

    public OrderPagerAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a EventRecordFragment (defined as a static inner class below).
        return OrderFragment.newInstance(position);
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
        return 5;
    }
}
