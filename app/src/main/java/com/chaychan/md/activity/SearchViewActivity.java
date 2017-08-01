package com.chaychan.md.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chaychan.md.R;
import com.chaychan.md.utils.FileUtils;

import java.lang.reflect.Method;

import static com.chaychan.md.R.id.toolbar;


public class SearchViewActivity extends AppCompatActivity{

    private static final String TAG = SearchViewActivity.class.getSimpleName();
    private static final int REQ_PERMISSION = 100;
    private SearchView.SearchAutoComplete mSearchAutoComplete;
    private SearchView mSearchView;
    private Toolbar mToolbar;
    private ListView mLvMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_view);

        initView();
        initToolbar();
        requestPermission();
    }


    private void initView() {
        mToolbar = (Toolbar) findViewById(toolbar);
        mLvMusic = (ListView) findViewById(R.id.lv_music);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchAutoComplete.isShown()) {
                    try {
                        mSearchAutoComplete.setText("");//清除文本
                        //利用反射调用收起SearchView的onCloseClicked()方法
                        Method method = mSearchView.getClass().getDeclaredMethod("onCloseClicked");
                        method.setAccessible(true);
                        method.invoke(mSearchView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    finish();
                }
            }
        });
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //如果还没有读取SD卡的权限,申请
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQ_PERMISSION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_view, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint("输入歌曲名查找");

        mSearchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        mSearchView.setIconified(true);//设置searchView处于展开状态

//        searchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件
                Toast.makeText(SearchViewActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //当输入框内容改变的时候回调
//                Log.i(TAG,"内容: " + newText);
                quertMusic(newText);
                return true;
            }
        });


        mSearchAutoComplete = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);

        //设置输入框内容文字和提示文字的颜色
        mSearchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white));
        mSearchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 模糊查找音乐
     * @param key
     */
    private void quertMusic(String key) {
        String[] musics = new String[]{};
        if (!TextUtils.isEmpty(key)){
            musics = FileUtils.queryMusic(this, key);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, musics);
        mLvMusic.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_PERMISSION) {
           if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
               Toast.makeText(this, "读取SD卡权限被拒绝了", Toast.LENGTH_SHORT).show();
           }
        }
    }
}
