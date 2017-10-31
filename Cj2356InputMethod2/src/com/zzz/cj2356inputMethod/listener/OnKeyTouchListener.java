package com.zzz.cj2356inputMethod.listener;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputConnection;
import android.widget.Button;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnAb;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnaa;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.StringUtils;

public class OnKeyTouchListener implements OnTouchListener {

    private Context context;

    public OnKeyTouchListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Button button = (Button) v;
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputConnection inputConnection = ser.getCurrentInputConnection();
            InputMethodStatus stat = ser.getInputMethodStatus();
            // 如果是中文輸入
            if (InputMethodStatusCn.TYPE_CODE.equals(stat.getType())) {
                String value = button.getText().toString();
                // 一個英文編碼
                String key = stat.getKeyByValue(value);

                // 取當前輸入編碼
                String code = ((InputMethodStatusCn) stat).getInputingCnCode();
                List<Item> items = ((InputMethodStatusCn) stat)
                        .getCandidatesInfo(code + key, true);
                // 輸入的編碼帶上新鍵，沒有字對應，直接返回
                if (null == items || items.isEmpty()) {
                    // 如果再打字也沒有了
                    if (!((InputMethodStatusCn) stat)
                            .couldContinueInputing(code + key)) {
                        return false;
                    } else {
                        // 還可以繼續鍵入，所以生成一個空的，防止以後報錯
                        items = new ArrayList<Item>();
                    }
                }

                String composingText = ((InputMethodStatusCn) stat)
                        .inputingCnCode(key, value);
                if (StringUtils.hasText(composingText)) {
                    // 提交正在編輯的內容
                    if (Cj2356InputMethodService.SHOW_COMPOSING_TEXT) {
                        inputConnection.setComposingText(composingText, 1);
                    }

                    // 取當前輸入編碼的候選項
                    ser.setSuggestions(items);
                }
            } else {
                // 英文直接輸入
                // commitText方法第2个参数值为1，表示在当前位置插入文本
                inputConnection.commitText(button.getText(), 1);
                
                // 如果是大小寫狀態，馬上回到小寫
                if (InputMethodStatusEnAb.SUBTYPE_CODE
                        .equals(stat.getSubType())) {
                    String aa = "bb";
                    ser.setInputMethodStatus(new InputMethodStatusEnaa(context));
                }
            }
        }
        return false;
    }
}
