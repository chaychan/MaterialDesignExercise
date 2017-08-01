package com.chaychan.md;

import android.test.AndroidTestCase;
import android.util.Log;

import com.chaychan.md.utils.FileUtils;

/**
 * @author ChayChan
 * @date 2017/7/31  21:04
 */

public class Test extends AndroidTestCase {

    public void testQuery(){
        String[] musics = FileUtils.queryMusic(getContext(), "黄家驹");
        for (String music : musics) {
            Log.i("Test",music);
        }
    }
}
