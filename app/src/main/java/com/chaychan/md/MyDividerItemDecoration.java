package com.chaychan.md;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author ChayChan
 * @date 2017/8/3  17:18
 */

public class MyDividerItemDecoration extends RecyclerView.ItemDecoration{

    private Paint mPaint;
    private int mDividerHeight;

    public MyDividerItemDecoration(Context context, int dividerHeight, int dividerColor){
        mDividerHeight = dividerHeight;
        mPaint = new Paint();
        mPaint.setColor(dividerColor);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mDividerHeight;//矩形的底部赋值分割线的高度
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();//获取到子View的个数
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + mDividerHeight;//子View底部添加分割线的高度
            c.drawRect(left, top, right, bottom, mPaint);//绘制
        }
    }
}
