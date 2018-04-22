package com.zzz.cj2356inputMethod.view;

import java.util.ArrayList;
import java.util.List;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.Cangjie2356IMsUtils;
import com.zzz.cj2356inputMethod.utils.DipPxUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private static LinearLayout chooseKeyboardLayoutScrollContent;
    private static List<TextView> textViews = null;

    // 三種類別輸入法的當前輸入法主鍵
    private static final String ORDER_TYPE_EN = Cangjie2356IMsUtils.ORDER_TYPE_EN;
    private static final String ORDER_TYPE_CJ = Cangjie2356IMsUtils.ORDER_TYPE_CJ;
    @SuppressWarnings("unused")
    private static final String ORDER_TYPE_ELSE = Cangjie2356IMsUtils.ORDER_TYPE_ELSE;

    public static void initChooseKeyboardLayoutTab(Context con, View kbView) {
        context = con;
        keyboardView = kbView;

        chooseKeyboardTabScroll = keyboardView.findViewById(R.id.chooseKeyboardLayoutScroll);
        chooseKeyboardLayoutScrollContent = (LinearLayout) keyboardView
                .findViewById(R.id.chooseKeyboardLayoutScrollContent);

        textViews = new ArrayList<TextView>();
        // 按類型配置，生成選項卡
        String imOrderTypeCfg = Cangjie2356IMsUtils.getImOrderTypeCfg();
        String orderTypes[] = imOrderTypeCfg.split(",");
        for (String type : orderTypes) {
            String tabIndi = (ORDER_TYPE_CJ.equals(type)) ? "倉頡" : ((ORDER_TYPE_EN.equals(type)) ? "En" : "其它");

            TextView textView = new TextView(context);
            textView.setText(tabIndi);
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
            textView.setSingleLine();
            RelativeLayout.LayoutParams lpParams = new RelativeLayout.LayoutParams(
                    DipPxUtil.dip(context, 80), RelativeLayout.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(lpParams);
            textView.setBackgroundResource(R.drawable.num_button_selector);

            textView.setOnClickListener(new MyChooseKeyboardClickListener(context));

            chooseKeyboardLayoutScrollContent.addView(textView);
            textViews.add(textView);
        }
        setTabBiggerTextSiz(0);
    }

    /**
     * 字體调大些
     * 
     * @author fsz
     * @time 2017年9月26日 下午9:30:02
     * @param curIndex
     */
    private static void setTabBiggerTextSiz(int curIndex) {
        for (int i = 0; i < textViews.size(); i++) {
            TextView textView = textViews.get(i);
            if (curIndex == i) {
                textView.setTextSize(17);
                textView.setTextColor(Color.BLACK);
            } else {
                textView.setTextSize(16);
                textView.setTextColor(Color.GRAY);
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

    static class MyChooseKeyboardClickListener implements View.OnClickListener {

        private Context context;

        public MyChooseKeyboardClickListener(Context con) {
            super();
            this.context = con;
        }

        @Override
        public void onClick(View v) {
            try {
                int index = textViews.indexOf(v);
                setTabBiggerTextSiz(index);

                String imOrderTypeCfg = Cangjie2356IMsUtils.getImOrderTypeCfg();
                String orderTypes[] = imOrderTypeCfg.split(",");
                String theType = orderTypes[index];

                InputMethodStatus statOld = ((Cj2356InputMethodService) context).getInputMethodStatus();
                // 停止中文輸入狀態
                if (statOld.isShouldTranslate()) {
                    if (((InputMethodStatusCn) statOld).isInputingCn()) {
                        // 先模擬點擊一下打字鍵盤上的回車
                        KeyboardBodyIniter.performClickEnter();
                    }
                }

                InputMethodStatus stat = Cangjie2356IMsUtils.getCurrentIm(theType);
                ((Cj2356InputMethodService) context).setInputMethodStatus(stat);
            } catch (Exception e) {
                Toast.makeText(context, "切換輸入法種類失敗：" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }
}