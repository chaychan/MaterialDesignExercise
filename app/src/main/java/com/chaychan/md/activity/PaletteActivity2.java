package com.chaychan.md.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.chaychan.md.R;
import com.chaychan.md.adapter.PaletteTabAdapter;
import com.chaychan.md.fragment.PicFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.chaychan.md.R.id.toolbar;


public class PaletteActivity2 extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private String[] mTitles = new String[]{
            "风景",
            "美女",
            "花儿"
    };
    private List<PicFragment> mFragments = new ArrayList<>();
    private int[] mImgResources = new int[]{
            R.mipmap.scenery,
            R.mipmap.beauty,
            R.mipmap.flower
    };
    private ViewPager mVpContent;
    private HashMap<Integer,Palette.Swatch> mSwatchMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_palette2);

        initView();
        initToolbar();
        initFragments();
        initListener();
    }


    private void initView() {
        mToolbar = (Toolbar) findViewById(toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initFragments() {
        for (int i = 0; i < mTitles.length; i++) {
            PicFragment fragment = new PicFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(PicFragment.IMG_RESOURCE,mImgResources[i]);
            fragment.setArguments(bundle);
            mFragments.add(fragment);//添加到集合中
        }
    }

    private void initListener() {
        changeColor(0);
        PaletteTabAdapter tabAdapter = new PaletteTabAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mVpContent.setAdapter(tabAdapter);
        mVpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 changeColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mVpContent);
    }

    /**
     * 改变各部分的颜色
     * @param position 下标
     */
    private void changeColor(final int position) {
        Palette.Swatch swatch = mSwatchMap.get(position);
        if (swatch != null){
            setColor(swatch);//设置颜色
            return;
        }
        // 用来提取颜色的Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),mImgResources[position]);
        // Palette的部分
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //获取到充满活力的样本
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                setColor(vibrant);//设置颜色
                mSwatchMap.put(position,vibrant);//保存对应位置的样本对象
            }
        });
    }

    /**
     * 设置颜色
     * @param vibrant
     */
    private void setColor(Palette.Swatch vibrant) {
        // 将颜色设置给状态栏
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setStatusBarColor(deepenColor(vibrant.getRgb()));//设置状态栏的颜色，设置颜色之前对颜色进行加深处理
            window.setNavigationBarColor(deepenColor(vibrant.getRgb()));//设置导航栏的颜色，设置颜色之前对颜色进行加深处理
        }

        mToolbar.setBackgroundColor(vibrant.getRgb());//设置Toolbar背景色
        mTabLayout.setBackgroundColor(vibrant.getRgb());//设置TabLayout背景色
        mTabLayout.setSelectedTabIndicatorColor(deepenColor(vibrant.getRgb()));//设置TabLayout指示器的颜色
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private int deepenColor(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }
}
