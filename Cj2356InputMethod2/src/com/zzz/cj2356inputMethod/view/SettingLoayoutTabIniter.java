package com.zzz.cj2356inputMethod.view;

import java.util.ArrayList;
import java.util.List;

import com.zzz.cj2356inputMethod.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 桌面圖標打開頁面的選項卡初始化
 * 
 * @author fsz
 * @time 2017年9月26日下午3:18:22
 */
public class SettingLoayoutTabIniter {

    public static final String SETTING_TAB_LOG = "版本說明";
    public static final String SETTING_TAB_DICT = "倉頡字典";

    private static Context context;

    private static LinearLayout settingLayoutScrollContent;
    private static List<String> settingTabNames = null;
    private static List<TextView> settingTextViews = null;

    public static void initSettingLoayoutTab(Context con) {
        context = con;

        settingLayoutScrollContent = (LinearLayout) ((Activity) context).findViewById(R.id.settingLayoutScrollContent);

        settingTabNames = new ArrayList<String>();
        settingTabNames.add(SETTING_TAB_LOG);
        settingTabNames.add(SETTING_TAB_DICT);

        settingTextViews = new ArrayList<TextView>();
        for (int i = 0; i < settingTabNames.size(); i++) {
            TextView textView = new TextView(context);
            textView.setText(settingTabNames.get(i));
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
            textView.setPadding(50, 0, 50, 0);
            textView.setSingleLine();
            RelativeLayout.LayoutParams lpParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(lpParams);

            textView.setOnClickListener(new OnTabChooseSettingListener(context));

            settingLayoutScrollContent.addView(textView);
            settingTextViews.add(textView);
        }
        setTabBiggerTextSiz(0);

        SettingVLogIniter.initSettingVLog(context);

        SettingDictIniter.initSettingDict(context);
        // 先隱藏字典
        SettingDictIniter.hideSettingVLog();
    }

    /**
     * 字體调大些
     * 
     * @author fsz
     * @time 2017年9月26日 下午9:30:02
     * @param curIndex
     */
    private static void setTabBiggerTextSiz(int curIndex) {
        for (int i = 0; i < settingTextViews.size(); i++) {
            TextView textView = settingTextViews.get(i);
            if (curIndex == i) {
                textView.setTextSize(17);
                textView.setTextColor(Color.LTGRAY);
            } else {
                textView.setTextSize(16);
                textView.setTextColor(Color.GRAY);
            }
        }
    }

    /**
     * 內部类，切換選項卡
     * 
     * @author fsz
     * @time 2017年9月26日 下午9:06:40
     */
    static class OnTabChooseSettingListener implements View.OnClickListener {

        private Context context;

        public OnTabChooseSettingListener(Context con) {
            super();
            this.context = con;
        }

        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            int index = settingTextViews.indexOf(tv);
            setTabBiggerTextSiz(index);

            if (SettingLoayoutTabIniter.SETTING_TAB_LOG.equals(tv.getText())) {
                SettingDictIniter.hideSettingVLog();
                SettingVLogIniter.showSettingVLog();

            } else if (SettingLoayoutTabIniter.SETTING_TAB_DICT.equals(tv.getText())) {
                SettingVLogIniter.hideSettingVLog();
                SettingDictIniter.showSettingVLog();

            } else {
                Toast.makeText(context, "未知選項卡" + tv.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
