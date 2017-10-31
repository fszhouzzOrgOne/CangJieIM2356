package com.zzz.cj2356inputMethod.view;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.Cangjie2356IMsUtils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

/**
 * 選擇鍵盤佈局的選項卡的初始化<br/>
 * 倉頡、英文、其它輸入法的切換放到候選欄，去除用切換選擇鍵盤佈局功能
 * 
 * @author t
 * @time 2017-2-21下午8:44:00
 */
public class ChooseKeyboardLayoutTabIniter {

    private static Context context;
    private static View keyboardView;

    private static View chooseKeyboardTabScroll;
    private static TabHost chooseKeyboardTabhost;

    // 三種類別輸入法的當前輸入法主鍵
    private static final String ORDER_TYPE_EN = Cangjie2356IMsUtils.ORDER_TYPE_EN;
    private static final String ORDER_TYPE_CJ = Cangjie2356IMsUtils.ORDER_TYPE_CJ;
    @SuppressWarnings("unused")
    private static final String ORDER_TYPE_ELSE = Cangjie2356IMsUtils.ORDER_TYPE_ELSE;

    public static void initChooseKeyboardLayoutTab(Context con, View kbView) {
        context = con;
        keyboardView = kbView;

        chooseKeyboardTabScroll = keyboardView.findViewById(R.id.chooseKeyboardLayoutScroll);
        chooseKeyboardTabhost = (TabHost) keyboardView.findViewById(R.id.chooseKeyboardLayoutTabhost);
        chooseKeyboardTabhost.setup();
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.keyboardsim_tab_content, chooseKeyboardTabhost.getTabContentView());

        // 按類型配置，生成選項卡
        String imOrderTypeCfg = Cangjie2356IMsUtils.getImOrderTypeCfg();
        String orderTypes[] = imOrderTypeCfg.split(",");
        for (String type : orderTypes) {
            String tabIndi = (ORDER_TYPE_CJ.equals(type)) ? "倉頡" : ((ORDER_TYPE_EN.equals(type)) ? "En" : "其它");
            chooseKeyboardTabhost.addTab(
                    chooseKeyboardTabhost.newTabSpec(type).setIndicator(tabIndi).setContent(R.id.linearLayoutSimTab));
        }

        int tabCnt = chooseKeyboardTabhost.getTabWidget().getChildCount();
        for (int i = 0; i < tabCnt; i++) {
            TextView textView = (TextView) chooseKeyboardTabhost.getTabWidget().getChildAt(i)
                    .findViewById(android.R.id.title);
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(0, 0, 0, 0);
            textView.setSingleLine();
            textView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
            textView.getLayoutParams().width = LayoutParams.WRAP_CONTENT;
        }
        setTabBiggerTextSiz(0);

        chooseKeyboardTabhost.setOnTabChangedListener(new OnTabChooseKeyboardChangedListener(context));
    }

    /**
     * 调整選項卡的顯示
     * 
     * @author zhaozizhao
     * @time 2017年9月26日 下午9:08:22
     */
    private static void changeTabShow() {
        int curIndex = chooseKeyboardTabhost.getCurrentTab();
        setTabBiggerTextSiz(curIndex);
    }

    /**
     * 字體调大些
     * 
     * @author fsz
     * @time 2017年9月26日 下午9:30:02
     * @param curIndex
     */
    private static void setTabBiggerTextSiz(int curIndex) {
        int tabCnt = chooseKeyboardTabhost.getTabWidget().getChildCount();
        for (int i = 0; i < tabCnt; i++) {
            TextView textView = (TextView) chooseKeyboardTabhost.getTabWidget().getChildAt(i)
                    .findViewById(android.R.id.title);
            if (curIndex == i) {
                textView.setTextSize(17);
            } else {
                textView.setTextSize(16);
            }
        }
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

    static class OnTabChooseKeyboardChangedListener implements OnTabChangeListener {

        private Context context;

        public OnTabChooseKeyboardChangedListener(Context con) {
            super();
            this.context = con;
        }

        @Override
        public void onTabChanged(String tabId) {
            changeTabShow();

            InputMethodStatus statOld = ((Cj2356InputMethodService) context).getInputMethodStatus();
            // 停止中文輸入狀態
            if (statOld.isShouldTranslate()) {
                if (((InputMethodStatusCn) statOld).isInputingCn()) {
                    // 先模擬點擊一下打字鍵盤上的回車
                    ImageButton keyboardEnter = (ImageButton) keyboardView.findViewById(R.id.keybtnEnter);
                    keyboardEnter.performClick();
                }
            }

            InputMethodStatus stat = Cangjie2356IMsUtils.getCurrentIm(tabId);
            ((Cj2356InputMethodService) context).setInputMethodStatus(stat);
        }

    }
}