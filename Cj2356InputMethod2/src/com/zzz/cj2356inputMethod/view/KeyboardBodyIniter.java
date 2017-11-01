package com.zzz.cj2356inputMethod.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.listener.OnCnEnSubsClickListener;
import com.zzz.cj2356inputMethod.listener.OnCommaPeriodTouchListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteLongClickListener;
import com.zzz.cj2356inputMethod.listener.OnEnterClickListener;
import com.zzz.cj2356inputMethod.listener.OnKeyTouchListener;
import com.zzz.cj2356inputMethod.listener.OnSpaceClickListener;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.Cangjie2356IMsUtils;
import com.zzz.cj2356inputMethod.utils.StringUtils;

import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class KeyboardBodyIniter {

    private static Integer currentKeyboardId = null;

    private static Context context;
    private static View keyboardView;

    // 26個英文字母鍵列表
    private static List<View> letterViews = new ArrayList<View>();
    private static List<Integer> letterViewsBgIds = new ArrayList<Integer>(); // 背景

    private static Button keybtnShift; // 中文換代鍵，英文大小寫轉換鍵
    private static InputMethodStatus inputStat; // 當前輸入法狀態

    public static void initKeyboardBody(Context con, View kbView,
            Integer keyboardId) {
        context = con;
        keyboardView = kbView;

        Integer theKeyboardId = currentKeyboardId;
        if (null != keyboardId) {
            theKeyboardId = keyboardId;
        }
        if (null == theKeyboardId) {
            theKeyboardId = R.layout.keyboard_qwerty1;
        }
        initKeyboardLayout(theKeyboardId);
        inputStat = null;
    }

    private static void initKeyboardLayout(int keyboardId) {
        // 裝入鍵盤
        View keyboard = ((InputMethodService) context).getLayoutInflater()
                .inflate(keyboardId, null);
        LinearLayout keyboardBody = (LinearLayout) keyboardView
                .findViewById(R.id.keyboardBodyContent);
        keyboardBody.removeAllViews();
        keyboardBody.addView(keyboard);

        init26LetterViews();

        keybtnShift = (Button) keyboardView.findViewById(R.id.keybtnShift);
        keybtnShift.setOnClickListener(new OnCnEnSubsClickListener(context));

        keyboardView.findViewById(R.id.keybtnSpace).setOnClickListener(
                new OnSpaceClickListener(context));

        keyboardView.findViewById(R.id.keybtnDelete).setOnClickListener(
                new OnDeleteClickListener(context));
        keyboardView.findViewById(R.id.keybtnDelete).setOnLongClickListener(
                new OnDeleteLongClickListener(context));

        keyboardView.findViewById(R.id.keybtnEnter).setOnClickListener(
                new OnEnterClickListener(context));

        // 數字鍵
        keyboardView.findViewById(R.id.keybtnNum).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != inputStat) {
                            // 停止中文輸入狀態
                            if (inputStat.isShouldTranslate()) {
                                if (((InputMethodStatusCn) inputStat)
                                        .isInputingCn()) {
                                    // 先模擬點擊一下打字鍵盤上的回車
                                    ImageButton keyboardEnter = (ImageButton) keyboardView
                                            .findViewById(R.id.keybtnEnter);
                                    keyboardEnter.performClick();
                                }
                            }
                        }

                        // 界面切換
                        ViewFlipper viewFlipper = (ViewFlipper) keyboardView
                                .findViewById(R.id.keyboardBodyFlipper);
                        viewFlipper.showNext();
                    }
                });

        // 符號鍵
        keyboardView.findViewById(R.id.keybtnSim).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (null != inputStat) {
                                // 停止中文輸入狀態
                                if (inputStat.isShouldTranslate()) {
                                    if (((InputMethodStatusCn) inputStat)
                                            .isInputingCn()) {
                                        // 先模擬點擊一下打字鍵盤上的回車
                                        ImageButton keyboardEnter = (ImageButton) keyboardView
                                                .findViewById(R.id.keybtnEnter);
                                        keyboardEnter.performClick();
                                    }
                                }
                            }

                            // 界面切換
                            ViewFlipper viewFlipper = (ViewFlipper) keyboardView
                                    .findViewById(R.id.keyboardBodyFlipper);
                            viewFlipper.showNext();
                            viewFlipper.showNext();
                        } catch (Exception e) {
                            Toast.makeText(context, "跳转到符號鍵鍵盤失敗"+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        // 逗號
        keyboardView.findViewById(R.id.keybtnSimComma).setOnTouchListener(new OnCommaPeriodTouchListener(context));

        // 句號
        keyboardView.findViewById(R.id.keybtnSimPeriod).setOnTouchListener(new OnCommaPeriodTouchListener(context));

        currentKeyboardId = keyboardId;
    }

    /**
     * 初始化26個字母鍵
     */
    private static void init26LetterViews() {
        letterViews = new ArrayList<View>();
        letterViews.add(keyboardView.findViewById(R.id.keybtnA));
        letterViews.add(keyboardView.findViewById(R.id.keybtnB));
        letterViews.add(keyboardView.findViewById(R.id.keybtnC));
        letterViews.add(keyboardView.findViewById(R.id.keybtnD));
        letterViews.add(keyboardView.findViewById(R.id.keybtnE));
        letterViews.add(keyboardView.findViewById(R.id.keybtnF));
        letterViews.add(keyboardView.findViewById(R.id.keybtnG));

        letterViews.add(keyboardView.findViewById(R.id.keybtnH));
        letterViews.add(keyboardView.findViewById(R.id.keybtnI));
        letterViews.add(keyboardView.findViewById(R.id.keybtnJ));
        letterViews.add(keyboardView.findViewById(R.id.keybtnK));
        letterViews.add(keyboardView.findViewById(R.id.keybtnL));
        letterViews.add(keyboardView.findViewById(R.id.keybtnM));
        letterViews.add(keyboardView.findViewById(R.id.keybtnN));

        letterViews.add(keyboardView.findViewById(R.id.keybtnO));
        letterViews.add(keyboardView.findViewById(R.id.keybtnP));
        letterViews.add(keyboardView.findViewById(R.id.keybtnQ));
        letterViews.add(keyboardView.findViewById(R.id.keybtnR));
        letterViews.add(keyboardView.findViewById(R.id.keybtnS));
        letterViews.add(keyboardView.findViewById(R.id.keybtnT));

        letterViews.add(keyboardView.findViewById(R.id.keybtnU));
        letterViews.add(keyboardView.findViewById(R.id.keybtnV));
        letterViews.add(keyboardView.findViewById(R.id.keybtnW));
        letterViews.add(keyboardView.findViewById(R.id.keybtnX));
        letterViews.add(keyboardView.findViewById(R.id.keybtnY));
        letterViews.add(keyboardView.findViewById(R.id.keybtnZ));
        // 背景主鍵
        letterViewsBgIds.add(R.drawable.background_button_a);
        letterViewsBgIds.add(R.drawable.background_button_b);
        letterViewsBgIds.add(R.drawable.background_button_c);
        letterViewsBgIds.add(R.drawable.background_button_d);
        letterViewsBgIds.add(R.drawable.background_button_e);
        letterViewsBgIds.add(R.drawable.background_button_f);
        letterViewsBgIds.add(R.drawable.background_button_g);
        letterViewsBgIds.add(R.drawable.background_button_h);
        letterViewsBgIds.add(R.drawable.background_button_i);
        letterViewsBgIds.add(R.drawable.background_button_j);
        letterViewsBgIds.add(R.drawable.background_button_k);
        letterViewsBgIds.add(R.drawable.background_button_l);
        letterViewsBgIds.add(R.drawable.background_button_m);
        letterViewsBgIds.add(R.drawable.background_button_n);
        letterViewsBgIds.add(R.drawable.background_button_o);
        letterViewsBgIds.add(R.drawable.background_button_p);
        letterViewsBgIds.add(R.drawable.background_button_q);
        letterViewsBgIds.add(R.drawable.background_button_r);
        letterViewsBgIds.add(R.drawable.background_button_s);
        letterViewsBgIds.add(R.drawable.background_button_t);
        letterViewsBgIds.add(R.drawable.background_button_u);
        letterViewsBgIds.add(R.drawable.background_button_v);
        letterViewsBgIds.add(R.drawable.background_button_w);
        letterViewsBgIds.add(R.drawable.background_button_x);
        letterViewsBgIds.add(R.drawable.background_button_y);
        letterViewsBgIds.add(R.drawable.background_button_z);

        for (View v : letterViews) {
            ((Button) v).setTextColor(Color.BLACK);
            v.setOnTouchListener(new OnKeyTouchListener(context));
        }
    }

    /**
     * 設置輸入法狀態
     * 
     * @author t
     * @time 2017-1-7下午10:33:25
     */
    public static void setInputMethodStatus(InputMethodStatus stat) {
        InputMethodStatus oldStat = inputStat;
        if (oldStat == null) {
            oldStat = Cangjie2356IMsUtils.getFirstIm();
        }
        inputStat = stat; // 新狀態

        // 按鍵顯示的轉換
        Map<String, Object> keysNameMap = inputStat.getKeysNameMap();
        List<String> keysList = new ArrayList<String>(keysNameMap.keySet());
        Collections.sort(keysList);
        for (int i = 0; i < keysList.size(); i++) {
            Button btn = (Button) letterViews.get(i);
            btn.setText(keysNameMap.get(keysList.get(i)).toString());
        }

        // 輸入法切換和逗號句號要特殊處理
        if (inputStat.isShouldTranslate()) {
            keybtnShift.setText(inputStat.getSubTypeName());
            ((Button) keyboardView.findViewById(R.id.keybtnSimComma)).setText("，");
            ((Button) keyboardView.findViewById(R.id.keybtnSimPeriod)).setText("。");

            // 26個按鍵背景修改
            for (int i = 0; i < letterViews.size(); i++) {
                View v = letterViews.get(i);
                v.setBackgroundResource(letterViewsBgIds.get(i));
            }
        } else {
            keybtnShift.setText(inputStat.getSubType());
            ((Button) keyboardView.findViewById(R.id.keybtnSimComma)).setText(",");
            ((Button) keyboardView.findViewById(R.id.keybtnSimPeriod)).setText(".");

            // 26個按鍵背景修改
            for (View v : letterViews) {
                v.setBackgroundResource(R.drawable.background_button);
            }
        }

        // 空格上顯示輸入法名字
        String spaceName = "空格";
        try {
            String tempName = inputStat.getInputMethodName();
            if (StringUtils.hasText(tempName)) {
                spaceName = "[" + tempName + "]";
            }
            ((Button) keyboardView.findViewById(R.id.keybtnSpace))
                    .setText(spaceName);
        } catch (Exception e) {

        }
    }

    public static InputMethodStatus getInputMethodStatus() {
        return inputStat;
    }

}
