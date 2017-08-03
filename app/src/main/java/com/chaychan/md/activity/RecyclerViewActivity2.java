package com.chaychan.md.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chaychan.md.MyDividerItemDecoration;
import com.chaychan.md.R;
import com.chaychan.md.adapter.RvListAdapter;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewActivity2 extends AppCompatActivity {

    private RecyclerView mRv;
    private List<String> mDatas = new ArrayList<>();
    private Handler mHandler = new Handler();
    private SwipeRefreshLayout mSrlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);


        initView();
        initData();
        initListener();
    }

    private void initView() {
        mSrlRoot = (SwipeRefreshLayout) findViewById(R.id.srl);
        mRv = (RecyclerView) findViewById(R.id.rv);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mDatas.add("我是条目" + i);
        }
    }

    private void initListener() {
        mRv.addItemDecoration(new MyDividerItemDecoration(this,1,getResources().getColor(R.color.divider)));
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(new RvListAdapter(this,mDatas));

        mSrlRoot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSrlRoot.setRefreshing(false);
                        Toast.makeText(RecyclerViewActivity2.this, "刷新完毕", Toast.LENGTH_SHORT).show();
                    }
                },2000);
            }
        });
    }
}
