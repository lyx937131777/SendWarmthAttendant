package com.example.sendwarmthattendant.fragment.adapter;

import android.content.Context;

import com.example.sendwarmthattendant.R;
import com.example.sendwarmthattendant.fragment.MapDetailFragment;
import com.example.sendwarmthattendant.fragment.TaskFragment;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TaskPagerAdapter  extends FragmentStatePagerAdapter
{
    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.title_all_tasks, R.string.title_running_tasks,R.string.title_unstart_tasks,R.string.title_canceled_tasks,R.string.title_completed_tasks};
    private final Context mContext;

    public TaskPagerAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        // getItem is called to instantiate the fragment for the given page.
        // Return a EventRecordFragment (defined as a static inner class below).
        return TaskFragment.newInstance(position);
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
