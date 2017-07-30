package com.chaychan.md.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chaychan.md.fragment.ContentFragment;

import java.util.List;

/**
 * @author ChayChan
 * @date 2017/7/30  20:33
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private List<ContentFragment> mFragments;
    private String[] mTitles;

    public TabAdapter(FragmentManager fm, List<ContentFragment> fragments,String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
