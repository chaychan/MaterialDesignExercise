package com.chaychan.md.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

/**
 * @author ChayChan
 * @date 2017/7/31  20:55
 */

public class FileUtils {

    /**
     * 根据歌名查看音乐
     * @param context 上下文
     * @param key 关键字
     * @return
     */
    public static String[] queryMusic(Context context, String key) {
        ArrayList<String> nameList = new ArrayList<>();
        Cursor c = null;
        try {
            c = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                    MediaStore.Audio.Media.DISPLAY_NAME + " LIKE '%" + key + "%'",
                    null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));// 路径

                if (!FileUtils.isExists(path)) {
                    continue;
                }

                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)); // 歌曲名
                nameList.add(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        if (nameList.isEmpty()){
            return new String[]{};
        }
        return (String[])nameList.toArray(new String[nameList.size()]);
    }

    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }
}
