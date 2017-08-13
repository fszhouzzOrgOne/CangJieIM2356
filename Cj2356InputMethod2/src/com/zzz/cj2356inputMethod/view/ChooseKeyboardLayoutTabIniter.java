package com.zzz.cj2356inputMethod.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

/**
 * 選擇鍵盤佈局的選項卡的初始化
 * 
 * @author t
 * @time 2017-2-21下午8:44:00
 */
public class ChooseKeyboardLayoutTabIniter {

    private static Context context;
    private static View keyboardView;

    private static View chooseKeyboardTabScroll;
    private static TabHost chooseKeyboardTabhost;

    public static void initChooseKeyboardLayoutTab(Context con, View kbView) {
        context = con;
        keyboardView = kbView;

        chooseKeyboardTabScroll = keyboardView
                .findViewById(R.id.chooseKeyboardLayoutScroll);
        chooseKeyboardTabhost = (TabHost) keyboardView
                .findViewById(R.id.chooseKeyboardLayoutTabhost);
        chooseKeyboardTabhost.setup();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.keyboardsim_tab_content,
                chooseKeyboardTabhost.getTabContentView());

        chooseKeyboardTabhost.addTab(chooseKeyboardTabhost
                .newTabSpec(R.layout.keyboard_qwerty1 + "").setIndicator("常規鍵盤")
                .setContent(R.id.linearLayoutSimTab));

        chooseKeyboardTabhost.addTab(chooseKeyboardTabhost
                .newTabSpec(R.layout.keyboard_abcxyz1 + "").setIndicator("順序鍵盤")
                .setContent(R.id.linearLayoutSimTab));

        int tabCnt = chooseKeyboardTabhost.getTabWidget().getChildCount();
        for (int i = 0; i < tabCnt; i++) {
            TextView textView = (TextView) chooseKeyboardTabhost.getTabWidget()
                    .getChildAt(i).findViewById(android.R.id.title);
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(0, 0, 0, 0);
            textView.setSingleLine();
            textView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
            textView.getLayoutParams().width = LayoutParams.WRAP_CONTENT;
        }

        chooseKeyboardTabhost
                .setOnTabChangedListener(new OnTabChooseKeyboardChangedListener(
                        context));
    }

    /** 隱藏換鍵盤控件 */
    public static void hideKeyboardChooser() {
        if (null != chooseKeyboardTabScroll) {
            chooseKeyboardTabScroll.setVisibility(View.INVISIBLE);
        }
    }

    /** 顯示換鍵盤控件 */
    public static void showKeyboardChooser() {
        if (null != chooseKeyboardTabScroll) {
            chooseKeyboardTabScroll.setVisibility(View.VISIBLE);
        }
    }

    static class OnTabChooseKeyboardChangedListener implements
            OnTabChangeListener {

        private Context context;

        public OnTabChooseKeyboardChangedListener(Context con) {
            super();
            this.context = con;
        }

        @Override
        public void onTabChanged(String tabId) {
            InputMethodStatus stat = ((Cj2356InputMethodService) context)
                    .getInputMethodStatus();

            KeyboardBodyIniter.initKeyboardBody(context, keyboardView,
                    Integer.parseInt(tabId));
            ((Cj2356InputMethodService) context).setInputMethodStatus(stat);
        }

    }
}