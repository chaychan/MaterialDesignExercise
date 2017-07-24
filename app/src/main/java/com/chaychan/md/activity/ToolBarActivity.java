package com.chaychan.md.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chaychan.md.R;
import com.chaychan.md.fragment.ContentFragment;

import java.util.ArrayList;
import java.util.List;

public class ToolBarActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<ContentFragment> mFragments = new ArrayList<>();
    private ListView mLvList;

    private String[] mMenuTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tool_bar);


        initView();
        initToolBar();
        initMenuTitles();
        initFragments();
        initDrawerLayout();
    }


    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawlayout);
        mLvList = (ListView) findViewById(R.id.lv_list);
    }

    private void initToolBar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg));//设置Toolbar的背景颜色

        mToolbar.setNavigationIcon(R.mipmap.ic_menu);//设置导航的图标
        mToolbar.setLogo(R.mipmap.ic_launcher);//设置logo

        mToolbar.setTitle("title");//设置标题
        mToolbar.setSubtitle("subtitle");//设置子标题

        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));//设置标题的字体颜色
        mToolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));//设置子标题的字体颜色

        //设置右上角的填充菜单
        mToolbar.inflateMenu(R.menu.menu_tool_bar);
        //设置导航图标的点击事件
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ToolBarActivity.this, "菜单", Toast.LENGTH_SHORT).show();
            }
        });
        //设置右侧菜单项的点击事件
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                String tip = "";
                switch (id) {
                    case R.id.action_search:
                        tip = "搜索";
                        break;
                    case R.id.action_add:
                        tip = "添加";
                        break;
                    case R.id.action_setting:
                        tip = "设置";
                        break;
                    case R.id.action_help:
                        tip = "帮助";
                        break;
                }
                Toast.makeText(ToolBarActivity.this, tip, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        ;
    }

    /**
     * 设置左侧菜单条目的标题
     */
    private void initMenuTitles() {
        mMenuTitles = getResources().getStringArray(R.array.menuTitles);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mMenuTitles);
        mLvList.setAdapter(arrayAdapter);
        mLvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switchFragment(position);//切换fragment
                mDrawerLayout.closeDrawers();//收起DrawerLayout
            }
        });
    }

    private void initFragments() {
        ContentFragment fragment1 = new ContentFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString(ContentFragment.TEXT,getString(R.string.menu_local_music));
        fragment1.setArguments(bundle1);


        ContentFragment fragment2 = new ContentFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(ContentFragment.TEXT,getString(R.string.menu_net_music));
        fragment2.setArguments(bundle2);

        mFragments.add(fragment1);
        mFragments.add(fragment2);
    }


    /**
     * 初始化DrawerLayout
     */
    private void initDrawerLayout() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,mToolbar, R.string.open, R.string.close);

        mDrawerToggle.syncState();;//同步

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        switchFragment(0);
    }


    /**
     * 切换fragment
     * @param index 下标
     */
    private void switchFragment(int index) {
        ContentFragment contentFragment = mFragments.get(index);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content,contentFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
}
