package com.zzz.cj2356inputMethod.view;

import com.zzz.cj2356inputMethod.R;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 桌面圖標打開頁面的選項卡初始化
 * 
 * @author fsz
 * @time 2017年9月26日下午3:18:22
 */
public class SettingLoayoutTabIniter {
    
    public static final String SETTING_TAB_ID_LOG = "setting_tab_log";
    public static final String SETTING_TAB_ID_DICT = "setting_tab_dict";

    private static Context context;

    private static TabHost settingLayoutTabhost;

    public static void initSettingLoayoutTab(Context con) {
        context = con;

        settingLayoutTabhost = (TabHost) ((Activity) context).findViewById(R.id.settingLayoutTabhost);
        settingLayoutTabhost.setup();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.setting_tab_content, settingLayoutTabhost.getTabContentView());
        // setContent 內容要在上面的inflate中，才能找到資源
        settingLayoutTabhost.addTab(settingLayoutTabhost.newTabSpec(SETTING_TAB_ID_LOG).setIndicator("版本說明")
                .setContent(R.id.linearLayoutSetTab));
        settingLayoutTabhost.addTab(settingLayoutTabhost.newTabSpec(SETTING_TAB_ID_DICT).setIndicator("倉頡字典")
                .setContent(R.id.linearLayoutSetTab));

        int tabCnt = settingLayoutTabhost.getTabWidget().getChildCount();
        for (int i = 0; i < tabCnt; i++) {
            TextView textView = (TextView) settingLayoutTabhost.getTabWidget().getChildAt(i)
                    .findViewById(android.R.id.title);
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(0, 0, 0, 0);
            textView.setSingleLine();
            textView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
            textView.getLayoutParams().width = LayoutParams.WRAP_CONTENT;
        }

        settingLayoutTabhost.setOnTabChangedListener(new OnTabChooseSettingListener(context));

        SettingVLogIniter.initSettingVLog(context);

        // 先隱藏字典
        LinearLayout setTabDictSearchLayout = (LinearLayout) ((Activity) context)
                .findViewById(R.id.setTabDictSearchLayout);
        setTabDictSearchLayout.setVisibility(View.INVISIBLE);
    }
}

class OnTabChooseSettingListener implements OnTabChangeListener {

    private Context context;

    public OnTabChooseSettingListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onTabChanged(String tabId) {
        if (SettingLoayoutTabIniter.SETTING_TAB_ID_LOG.equals(tabId)) {
            // 先隱藏字典
            LinearLayout setTabDictSearchLayout = (LinearLayout) ((Activity) context)
                    .findViewById(R.id.setTabDictSearchLayout);
            setTabDictSearchLayout.setVisibility(View.INVISIBLE);
            
            SettingVLogIniter.showSettingVLog();
            
        } else if (SettingLoayoutTabIniter.SETTING_TAB_ID_DICT.equals(tabId)) {
            SettingVLogIniter.hideSettingVLog();
            
            LinearLayout setTabDictSearchLayout = (LinearLayout) ((Activity) context)
                    .findViewById(R.id.setTabDictSearchLayout);
            setTabDictSearchLayout.setVisibility(View.VISIBLE);
            
        } else {
            Toast.makeText(context, "未知選項卡" + tabId, Toast.LENGTH_SHORT).show();
        }
    }
}
