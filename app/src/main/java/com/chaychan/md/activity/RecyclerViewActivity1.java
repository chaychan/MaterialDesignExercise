package com.chaychan.md.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chaychan.md.R;
import com.chaychan.md.adapter.MyListAdapter;
import com.chaychan.md.adapter.MyStaggerAdapter;
import com.chaychan.md.bean.DataBean;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewActivity1 extends AppCompatActivity {

    private RecyclerView mRv;

    private List<DataBean> mDatas = new ArrayList<DataBean>();
    private List<DataBean> mStaggerDatas = new ArrayList<DataBean>();

    private int[] mListIcons = new int[]{R.mipmap.g1, R.mipmap.g2, R.mipmap.g3, R.mipmap.g4,
            R.mipmap.g5, R.mipmap.g6, R.mipmap.g7, R.mipmap.g8, R.mipmap.g9, R.mipmap.g10, R
            .mipmap.g11, R.mipmap.g12, R.mipmap.g13, R.mipmap.g14, R.mipmap.g15, R.mipmap
            .g16, R.mipmap.g17, R.mipmap.g18, R.mipmap.g19, R.mipmap.g20, R.mipmap.g21, R
            .mipmap.g22, R.mipmap.g23, R.mipmap.g24, R.mipmap.g25, R.mipmap.g26, R.mipmap
            .g27, R.mipmap.g28, R.mipmap.g29};

    private int[] mStaggeredIcons = new int[]{R.mipmap.p1, R.mipmap.p2, R.mipmap.p3, R
            .mipmap.p4, R.mipmap.p5, R.mipmap.p6, R.mipmap.p7, R.mipmap.p8, R.mipmap.p9, R
            .mipmap.p10, R.mipmap.p11, R.mipmap.p12, R.mipmap.p13, R.mipmap.p14, R.mipmap
            .p15, R.mipmap.p16, R.mipmap.p17, R.mipmap.p18, R.mipmap.p19, R.mipmap.p20, R
            .mipmap.p21, R.mipmap.p22, R.mipmap.p23, R.mipmap.p24, R.mipmap.p25, R.mipmap
            .p26, R.mipmap.p27, R.mipmap.p28, R.mipmap.p29, R.mipmap.p30, R.mipmap.p31, R
            .mipmap.p32, R.mipmap.p33, R.mipmap.p34, R.mipmap.p35, R.mipmap.p36, R.mipmap
            .p37, R.mipmap.p38, R.mipmap.p39, R.mipmap.p40, R.mipmap.p41, R.mipmap.p42, R
            .mipmap.p43, R.mipmap.p44};
    private MyListAdapter mListAdapter;
    private MyStaggerAdapter mStaggerAdapter;
    private SwipeRefreshLayout mSrlRoot;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initView();
        initData();
        initListAdapterV();//默认是垂直的线性布局
        initListener();
    }


    private void initView() {
        mSrlRoot = (SwipeRefreshLayout) findViewById(R.id.srl);
        mRv = (RecyclerView) findViewById(R.id.rv);
    }

    private void initData(){
        for (int i = 0; i < mListIcons.length; i++) {
            int iconId = mListIcons[i];
            DataBean dataBean = new DataBean();
            dataBean.iconId = iconId;
            dataBean.content = "我是条目" + i;
            mDatas.add(dataBean);
        }

        for (int i = 0; i < mStaggeredIcons.length; i++) {
            int iconId = mStaggeredIcons[i];
            DataBean dataBean = new DataBean();
            dataBean.iconId = iconId;
            dataBean.content = "我是条目" + i;
            mStaggerDatas.add(dataBean);
        }

        mListAdapter = new MyListAdapter(this, mDatas);
        mStaggerAdapter = new MyStaggerAdapter(this, mStaggerDatas);
    }

    private void initListener() {
        mSrlRoot.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);
        mSrlRoot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSrlRoot.setRefreshing(false);//收起下拉刷新
                        Toast.makeText(RecyclerViewActivity1.this, "刷新完毕", Toast.LENGTH_SHORT).show();
                    }
                },3000);
            }
        });
        
        mListAdapter.setOnItemClickListener(new MyListAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(RecyclerViewActivity1.this, mDatas.get(postion).content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //纵向列表布局
    private void initListAdapterV() {
        LinearLayoutManager layoutManger = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRv.setLayoutManager(layoutManger);
        mRv.setAdapter(mListAdapter);
    }

    //横向列表布局
    private void initListAdapterH(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mListAdapter);
    }

    //纵向的网格布局
    private void initGridAdapterV() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,2, GridLayout.VERTICAL, false);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mListAdapter);
    }

    //横向的网格布局
    private void initGridAdapterH() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,2,GridLayout.HORIZONTAL, false);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mListAdapter);
    }

    //纵向的瀑布流布局
    private void initStaggerAdapterV(){
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mStaggerAdapter);
    }

    //横向的瀑布流布局
    private void initStaggerAdapterH(){
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mStaggerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list_v://纵向列表布局
                initListAdapterV();
                break;
            case R.id.action_list_h://横向列表布局
                initListAdapterH();
                break;
            case R.id.action_grid_v://纵向网格布局
                initGridAdapterV();
                break;
            case R.id.action_grid_h://横向网格布局
                initGridAdapterH();
                break;
            case R.id.action_stagger_v://纵向瀑布流布局
                initStaggerAdapterV();
                break;
            case R.id.action_stagger_h://横向瀑布流布局
                initStaggerAdapterH();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
