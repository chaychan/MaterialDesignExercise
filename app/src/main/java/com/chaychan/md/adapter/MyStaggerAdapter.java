package com.chaychan.md.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.md.R;
import com.chaychan.md.bean.DataBean;

import java.util.List;

public class MyStaggerAdapter extends RecyclerView.Adapter<MyStaggerAdapter.MyHolder> {

    private List<DataBean> list;
    private Context context;

    public MyStaggerAdapter(Context context, List<DataBean> list) {
        this.list = list;
        this.context = context;
    }

    /**创建条目布局*/
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_stragger, null);
        return new MyHolder(view);
    }

    /**绑定数据*/
    @Override
    public void onBindViewHolder(MyHolder myHolder, int position) {
        myHolder.setDataAndRefreshUI(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        private ImageView mIv;
        private TextView mTv;

        public MyHolder(View itemView) {
            super(itemView);
            mIv = (ImageView) itemView.findViewById(R.id.item_straggered_iv);
            mTv = (TextView) itemView.findViewById(R.id.item_straggered_tv);
        }

        public void setDataAndRefreshUI(DataBean dataBean){
             mIv.setImageResource(dataBean.iconId);
             mTv.setText(dataBean.content);
        }
    }
}
