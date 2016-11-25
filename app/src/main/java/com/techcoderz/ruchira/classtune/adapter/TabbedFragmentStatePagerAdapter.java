package com.techcoderz.ruchira.classtune.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.techcoderz.ruchira.classtune.fragment.NewReleaseFragment;
import com.techcoderz.ruchira.classtune.fragment.TopRatedFragment;
import com.techcoderz.ruchira.classtune.fragment.UpcommingFragment;


/**
 * Created by apollo on 11/8/2015.
 */
public class TabbedFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"New Release", "Top Rated", "Upcoming"};

    public TabbedFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewReleaseFragment();
            case 1:
                return new TopRatedFragment();
            case 2:
                return new UpcommingFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}