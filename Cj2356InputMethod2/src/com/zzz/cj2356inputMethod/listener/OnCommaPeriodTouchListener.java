package com.zzz.cj2356inputMethod.listener;

import java.util.List;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.StringUtils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputConnection;
import android.widget.Button;

/**
 * 逗號和句號點擊事件：<br/>
 * 英文直接輸出，翻譯類的，先輸出第一個字
 * 
 * @author fszhouzz@qq.com
 * @time 2017年10月31日下午1:15:56
 */
public class OnCommaPeriodTouchListener implements OnTouchListener {

    private Context context;

    public OnCommaPeriodTouchListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 如果原來是中文狀態，而且正在打字，提交候選第一個字
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputMethodStatus stat = ser.getInputMethodStatus();
            InputConnection inputConnection = ser.getCurrentInputConnection();
            // 如果是中文輸入
            if (stat.isShouldTranslate()) {
                if (((InputMethodStatusCn) stat).isInputingCn()) {
                    String value = " ";
                    List<Item> sugs = ser.getSuggestions();
                    if (null != sugs && !sugs.isEmpty()) {
                        value = sugs.get(0).getCharacter();
                    }
                    if (StringUtils.hasText(value)) {
                        // 获得InputConnection对象
                        inputConnection.commitText(value, 1);
                    }
                    ((InputMethodStatusCn) stat).setInputingCn(false);
                }
            }

            // 輸入符號
            Button button = (Button) v;
            inputConnection.commitText(button.getText(), 1);
        }
        return false;
    }

}
