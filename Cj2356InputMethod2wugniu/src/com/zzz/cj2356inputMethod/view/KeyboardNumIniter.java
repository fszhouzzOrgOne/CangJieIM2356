package com.zzz.cj2356inputMethod.view;

import java.util.ArrayList;
import java.util.List;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethodWugniu.R;
import com.zzz.cj2356inputMethod.listener.OnDeleteNumClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteNumLongClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteRightClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteRightLongClickListener;
import com.zzz.cj2356inputMethod.listener.OnEnterClickListener;
import com.zzz.cj2356inputMethod.listener.OnKeyNumTouchListener;
import com.zzz.cj2356inputMethod.listener.OnSpaceNumClickListener;
import com.zzz.cj2356inputMethod.listener.OnSpaceNumLongClickListener;

import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * 數字鍵盤初始化
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2019年10月19日 下午10:54:42
 */
public class KeyboardNumIniter {

    private static Context context;
    private static View keyboardView;
    // 40個鍵列表
    private static List<View> letterViews = null;
    private static String[] letterViewsText = { "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "0", "!", "@", "#", "$", "%", "^", "&", "*", "()",
            "[]", "~", "`", "'", "\"", "–", "_", "+", "-", "=", "{}", ",", ".",
            "<", ">", ":", ";", "?", "/", "\\", "|" };

    public static void initKeyboardSim(Context con, View kbView) {
        context = con;
        keyboardView = kbView;

        // 裝入鍵盤
        View keyboardNum40 = ((InputMethodService) context).getLayoutInflater()
                .inflate(R.layout.keyboard_num40, null);
        LinearLayout keyboardBodyNum = (LinearLayout) keyboardView
                .findViewById(R.id.keyboardBodyNumContent);
        keyboardBodyNum.removeAllViews();
        keyboardBodyNum.addView(keyboardNum40);

        init40LetterViews();

        // 回車鍵
        keyboardView.findViewById(R.id.keybtnNum40Enter)
                .setOnClickListener(new OnEnterClickListener(context));
        // 數字鍵盤的空格鍵
        keyboardView.findViewById(R.id.keybtnNum40Space)
                .setOnClickListener(new OnSpaceNumClickListener(context));
        keyboardView.findViewById(R.id.keybtnNum40Space).setOnLongClickListener(
                new OnSpaceNumLongClickListener(context));
        // 數字鍵盤的刪除鍵
        keyboardView.findViewById(R.id.keybtnNum40DeleteLeft)
                .setOnClickListener(new OnDeleteNumClickListener(context));
        keyboardView.findViewById(R.id.keybtnNum40DeleteLeft)
                .setOnLongClickListener(
                        new OnDeleteNumLongClickListener(context));
        // 刪除右邊
        keyboardView.findViewById(R.id.keybtnNum40DeleteRight)
                .setOnClickListener(new OnDeleteRightClickListener(context));
        keyboardView.findViewById(R.id.keybtnNum40DeleteRight)
                .setOnLongClickListener(
                        new OnDeleteRightLongClickListener(context));
        // 數字鍵盤符號
        Button btnSim = (Button) keyboardView.findViewById(R.id.keybtnNum40Sim);
        btnSim.setTextColor(Color.DKGRAY);
        btnSim.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((Cj2356InputMethodService) context)
                            .setMainBoardEnterSims(false);
                    // 界面切換
                    ViewFlipper viewFlipper = (ViewFlipper) keyboardView
                            .findViewById(R.id.keyboardBodyFlipper);
                    viewFlipper.showNext();
                } catch (Exception e) {
                    Toast.makeText(context, "跳转到符號鍵鍵盤失敗" + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // 數字鍵盤返回
        Button btnNumBack = (Button) keyboardView
                .findViewById(R.id.keybtnNum40Back);
        btnNumBack.setTextColor(Color.DKGRAY);
        btnNumBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 界面切換
                ViewFlipper viewFlipper = (ViewFlipper) keyboardView
                        .findViewById(R.id.keyboardBodyFlipper);

                viewFlipper.showPrevious();
            }
        });
    }

    /**
     * 初始化40個鍵
     */
    private static void init40LetterViews() {
        letterViews = new ArrayList<View>();
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4011));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4012));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4013));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4014));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4015));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4016));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4017));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4018));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4019));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4010));

        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4021));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4022));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4023));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4024));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4025));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4026));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4027));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4028));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4029));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4020));

        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4031));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4032));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4033));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4034));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4035));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4036));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4037));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4038));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4039));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4030));

        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4041));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4042));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4043));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4044));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4045));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4046));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4047));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4048));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4049));
        letterViews.add(keyboardView.findViewById(R.id.keybtnNum4040));

        for (int i = 0; i < letterViews.size(); i++) {
            Button v = (Button) letterViews.get(i);
            v.setText(letterViewsText[i]);
            v.setTextColor(Color.BLACK);
            v.setOnTouchListener(new OnKeyNumTouchListener(context));
        }
    }

    public static void resetKeyboardNumPage() {

    }
}
