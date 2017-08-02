package com.chaychan.md.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaychan.md.R;


public class PaletteActivity1 extends AppCompatActivity {

    private TextView mTvVibrant;
    private TextView mTvLightVibrant;
    private TextView mTvDarkVibrant;
    private TextView mTvMuted;
    private TextView mTvLightMuted;
    private TextView mTvDarkMuted;
    private ImageView mIvPic;
    private TextView mTvTitle;
    private TextView mTvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        initView();
        pickPicColors();
    }

    private void initView() {
        mIvPic = (ImageView) findViewById(R.id.iv_pic);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvBody = (TextView) findViewById(R.id.tv_body);

        mTvVibrant = (TextView) findViewById(R.id.tv_vibrant);
        mTvLightVibrant = (TextView) findViewById(R.id.tv_light_vibrant);
        mTvDarkVibrant = (TextView) findViewById(R.id.tv_dark_vibrant);

        mTvMuted = (TextView) findViewById(R.id.tv_muted);
        mTvLightMuted = (TextView) findViewById(R.id.tv_light_muted);
        mTvDarkMuted = (TextView) findViewById(R.id.tv_dark_muted);
    }

    public void pickPicColors() {
        Bitmap bitmap = ((BitmapDrawable) mIvPic.getDrawable()).getBitmap();
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //提取有活力的颜色
                int vibrantColor = palette.getVibrantColor(Color.RED);
                mTvVibrant.setBackgroundColor(vibrantColor);

                //提取有活力的 亮色
                int lightVibrantColor = palette.getLightVibrantColor(Color.RED);
                mTvLightVibrant.setBackgroundColor(lightVibrantColor);

                //提取有活力的 暗色
                int darkVibrantColor = palette.getDarkVibrantColor(Color.RED);
                mTvDarkVibrant.setBackgroundColor(darkVibrantColor);

                //提取柔和的颜色
                int mutedColor = palette.getMutedColor(Color.RED);
                mTvMuted.setBackgroundColor(mutedColor);

                //提取柔和的亮色
                int lightMutedColor = palette.getLightMutedColor(Color.RED);
                mTvLightMuted.setBackgroundColor(lightMutedColor);

                //提取柔和的暗色
                int darkMutedColor = palette.getDarkMutedColor(Color.RED);
                mTvDarkMuted.setBackgroundColor(darkMutedColor);


                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();//获取有活力的颜色样本
                Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();//获取有活力 亮色的样本
                Palette.Swatch drakVibrantSwatch = palette.getDarkVibrantSwatch();//获取有活力 暗色的样本

                Palette.Swatch mutedSwatch = palette.getMutedSwatch();//获取柔和的颜色样本
                Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();//获取柔和 亮色的样本
                Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();//获取柔和 暗色的样本

//                int rgb = vibrantSwatch.getRgb();//获取对应样本的rgb
//                float[] hsl = vibrantSwatch.getHsl();//获取hsl颜色
//                int population = vibrantSwatch.getPopulation();//获取像素的数量
//                int titleTextColor = vibrantSwatch.getTitleTextColor();//获取适合标题文字的颜色
//                int bodyTextColor = vibrantSwatch.getBodyTextColor();//获取适配内容文字的颜色


                //获取适合标题文字的颜色
                int titleTextColor = drakVibrantSwatch.getTitleTextColor();
                mTvTitle.setTextColor(titleTextColor);
                ;
                //获取适合内容文字的颜色
                int bodyTextColor = drakVibrantSwatch.getBodyTextColor();
                mTvBody.setTextColor(bodyTextColor);

            }
        });
    }

    private int generateTransparentColor(float percent, int rgb) {
        int red = Color.red(rgb);
        int green = Color.green(rgb);
        int blue = Color.blue(rgb);
        int alpha = Color.alpha(rgb);
        alpha = (int) (alpha * percent);
        return Color.argb(alpha, red, green, blue);
    }
}
