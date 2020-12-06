package com.zzz.cj2356inputMethod.view;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.zzz.cj2356inputMethodWugniu.R;
import com.zzz.cj2356inputMethod.mb.IOUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * 桌面圖標打開頁面：版本變更展示
 * 
 * @author fsz
 * @time 2017年9月26日下午5:38:10
 */
public class SettingVLogIniter {
    private static Context context;

    private static TextView setTextview;

    /**
     * 版本變更展示
     * 
     * @author fsz
     * @time 2017年9月26日下午5:39:37
     * @param con
     */
    public static void initSettingVLog(Context con) {
        context = con;

        setTextview = (TextView) ((Activity) context).findViewById(R.id.setsuMyouTextview);
        setTextview.setTextColor(Color.LTGRAY);
        setTextview.setText(readLogTxt());
        setTextview.setTextIsSelectable(true);
    }

    /**
     * 讀取版本更新日誌
     * 
     * @author 日月遞炤
     * @time 2017年9月27日 下午9:50:54
     * @return
     */
    private static String readLogTxt() {
        String str = "讀取版本更新日誌失敗，請聯繫製作人：日月遞炤 fszhouzz@qq.com";
        String log = "version" + File.separator + "log.txt";
        try {
            List<String> result = IOUtils.readLines(context.getResources().getAssets().open(log));
            if (null != result && !result.isEmpty()) {
                str = "";
                for (String line : result) {
                    str += "    " + line + "\n";
                }
            }
            return str;
        } catch (IOException e) {
        }
        return str;
    }

    /** 隱藏版本變更 */
    public static void hideSettingVLog() {
        if (null != setTextview) {
            setTextview.setVisibility(View.GONE);
        }
    }

    /** 顯示版本變更 */
    public static void showSettingVLog() {
        if (null != setTextview) {
            setTextview.setVisibility(View.VISIBLE);
        }
    }
}
