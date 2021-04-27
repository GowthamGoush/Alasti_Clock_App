package com.example.alasti.Adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.alasti.Fragments.AlarmFragment;
import com.example.alasti.Fragments.TimerFragment;
import com.example.alasti.Fragments.StopWatchFragment;
import com.example.alasti.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2 , R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch(position){

            case 0 : fragment = AlarmFragment.newInstance("Alarm","Tab1");
                break;
            case 1 : fragment = TimerFragment.newInstance("Stopwatch","Tab2");
                break;
            case 2 : fragment = StopWatchFragment.newInstance("sample","samp");
                break;
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}