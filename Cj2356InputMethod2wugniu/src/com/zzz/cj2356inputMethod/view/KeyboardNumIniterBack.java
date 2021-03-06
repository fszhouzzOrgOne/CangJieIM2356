package com.zzz.cj2356inputMethod.view;

/**
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.adapter.KeyBoardNumAdapter;
import com.zzz.cj2356inputMethod.listener.OnDeleteNumClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteNumLongClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteRightClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteRightLongClickListener;
import com.zzz.cj2356inputMethod.listener.OnEnterClickListener;
import com.zzz.cj2356inputMethod.listener.OnSpaceNumClickListener;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
*/

/**
 * 原來的數字鍵盤代碼
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2019年10月19日 下午10:53:37
 */
public class KeyboardNumIniterBack {
/**
    public static final int keyboardNumPageSize = 4; // 数字鍵一頁行数
    public static int currentKeyboardNumPage = 0;
    // 數字鍵的鍵，把普通鍵盤上的東西都丢進去，製表符用個t代替，需要特殊處理
    // 一行幾個，按這個數組的元素長度定，設置到@+id/keyboardBodyNumGrid上。
    public static String[] keyboardBodyNums = { "789/", "456*", "123-",
            ",0.+" };
    public static List<String> keyboardBodyNumSims = Arrays.asList("#", "@",
            "=", "_", "'", "?", "¿?", "!", "¡!", ":", ";", "' '", "\" \"", "$",
            "%", "℅", "‰", "‱", "^", "&", "<", ">", "Tab", "~", "̃", "`", "‒",
            "–", "|", "\\", "( )", "[ ]", "{ }", "·", "•", "¥", "￥", "Ұ", "ұ");

    private static Context context;
    private static View keyboardView;
    private static GridView keyboardBodyNumGrid; // 數字鍵盤

    public static void initKeyboardSim(Context con, View kbView) {
        context = con;
        keyboardView = kbView;

        // 符號滑動，ScrollView中只能有一個控件
        LinearLayout numSimsScrollContent = (LinearLayout) keyboardView
                .findViewById(R.id.keyboardBodyNumSimScrollContent);
        for (String sim : keyboardBodyNumSims) {
            numSimsScrollContent
                    .addView(new KeyboardNumSimItemTextView(context, sim));
        }

        keyboardBodyNumGrid = (GridView) keyboardView
                .findViewById(R.id.keyboardBodyNumGrid);
        initKeyboardBodyNumGridPage(currentKeyboardNumPage);

        // 回車鍵
        keyboardView.findViewById(R.id.keybtnNumEnter)
                .setOnClickListener(new OnEnterClickListener(context));
        // 數字鍵盤的空格鍵
        keyboardView.findViewById(R.id.keybtnNumSpace)
                .setOnClickListener(new OnSpaceNumClickListener(context));
        // 數字鍵盤的刪除鍵
        keyboardView.findViewById(R.id.keybtnNumDelete)
                .setOnClickListener(new OnDeleteNumClickListener(context));
        keyboardView.findViewById(R.id.keybtnNumDelete).setOnLongClickListener(
                new OnDeleteNumLongClickListener(context));
        // 刪除右邊
        keyboardView.findViewById(R.id.keybtnNumDeleteRight)
                .setOnClickListener(new OnDeleteRightClickListener(context));
        keyboardView.findViewById(R.id.keybtnNumDeleteRight)
                .setOnLongClickListener(
                        new OnDeleteRightLongClickListener(context));
        // 數字鍵盤返回
        Button btnNumBack = (Button) keyboardView
                .findViewById(R.id.keybtnNumBack);
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

    public static void resetKeyboardNumPage() {
        initKeyboardBodyNumGridPage(0);
    }

    private static void initKeyboardBodyNumGridPage(int page) {
        int tatalPage = keyboardBodyNums.length / keyboardNumPageSize;
        if (0 != keyboardBodyNums.length % keyboardNumPageSize) {
            tatalPage++;
        }
        int index = (page <= 0) ? 0
                : (page >= tatalPage ? tatalPage - 1 : page);
        int start = index * keyboardNumPageSize;
        int end = (start + keyboardNumPageSize >= keyboardBodyNums.length)
                ? keyboardBodyNums.length : start + keyboardNumPageSize;
        // 如果不是首頁
        if (index > 0) {
            start = end - keyboardNumPageSize;
        }
        String content = "";
        for (int i = start; i < end; i++) {
            content += keyboardBodyNums[i];
        }

        ArrayList<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < content.length(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            Character cha = content.charAt(i);
            map.put(KeyBoardNumAdapter.ITEM_KEY_NAME, cha.toString());
            valueList.add(map);
        }
        KeyBoardNumAdapter kKeyBoardNumAdapter = new KeyBoardNumAdapter(context,
                valueList, R.layout.keyboardnumitem, false);
        keyboardBodyNumGrid.setNumColumns(keyboardBodyNums[0].length());
        keyboardBodyNumGrid.setAdapter(kKeyBoardNumAdapter);

        currentKeyboardNumPage = index;
    }
*/
}
