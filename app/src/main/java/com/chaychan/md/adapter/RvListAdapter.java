package com.chaychan.md.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaychan.md.R;

import java.util.List;

/**
 * @author ChayChan
 * @date 2017/8/3  17:28
 */

public class RvListAdapter extends RecyclerView.Adapter<RvListAdapter.MyHolder> {

    private Context mContext;
    private List<String> mDatas;

    public RvListAdapter(Context context, List<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_list2, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.setDataAndRefreshUI(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        private TextView mTv;

        public MyHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.tv);
        }

        public void setDataAndRefreshUI(String content){
            mTv.setText(content);
        }
    }
}
