package com.zzz.cj2356inputMethod.utils;

import android.content.Context;

/**
 * 長寬單位轉換
 * 
 * @author fszhouzz@qq.com
 * @time 2018年3月20日下午2:28:48
 */
public class DipPxUtil {

    public static int dip(Context context, int pixels) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }

}
