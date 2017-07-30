package com.chaychan.md.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.chaychan.md.R;
import com.chaychan.md.adapter.TabAdapter;
import com.chaychan.md.fragment.ContentFragment;

import java.util.ArrayList;
import java.util.List;


public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mVpContent;

    private List<ContentFragment> mFragments = new ArrayList<>();
    private String[] mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);

        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
    }

    private void initData() {
        mTitles = getResources().getStringArray(R.array.channel);
        for (int i = 0; i < mTitles.length; i++) {
            ContentFragment fragment = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ContentFragment.TEXT, mTitles[i]);
            fragment.setArguments(bundle);
            mFragments.add(fragment);//添加到fragment中
        }
    }

    private void initListener() {
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mVpContent.setAdapter(tabAdapter);//为viewPager设置adapter
        mTabLayout.setupWithViewPager(mVpContent);//将TabLayout和ViewPager关联
    }
}
