package com.zzz.cj2356inputMethod.listener;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.StringUtils;

public class OnDeleteClickListener implements OnClickListener {

    private Context context;

    public OnDeleteClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.keybtnDelete) {
            // 如果原來是中文狀態，而且正在打字
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputConnection inputConnection = ser.getCurrentInputConnection();
            InputMethodStatus stat = ser.getInputMethodStatus();
            // 如果是中文輸入
            if (InputMethodStatusCn.TYPE_CODE.equals(stat.getType())) {
                if (((InputMethodStatusCn) stat).isInputingCn()) {
                    String getInputingCnCode = ((InputMethodStatusCn) stat)
                            .getInputingCnCode();
                    // 如果當前只打了一個鍵
                    if (getInputingCnCode.length() <= 1) {
                        inputConnection.commitText("", 1);
                        ((InputMethodStatusCn) stat).setInputingCn(false);
                    } else {
                        // 如果當前只打了两個以上的鍵
                        // 將臨時輸入置成第一至倒數第二個鍵
                        String code = "";
                        String value = "";
                        for (int index = 0; index < getInputingCnCode.length() - 1; index++) {
                            Character thecode = getInputingCnCode.charAt(index);
                            code += thecode.toString();
                            value += ((InputMethodStatusCn) stat)
                                    .getKeysNameMap().get(thecode.toString());
                        }

                        // 先置空，再放進去
                        ((InputMethodStatusCn) stat).inputingCnCode(null, null);
                        String composingText = ((InputMethodStatusCn) stat)
                                .inputingCnCode(code, value);
                        if (StringUtils.hasText(composingText)) {
                            // 不再提交正在編輯的內容
                            if (Cj2356InputMethodService.SHOW_COMPOSING_TEXT) {
                                inputConnection.setComposingText(composingText, 1);
                            }

                            // 取當前輸入編碼的候選項
                            List<Item> items = ((InputMethodStatusCn) stat)
                                    .getCandidatesInfo(code);
                            if (items == null) {
                                // 還可以繼續鍵入，所以生成一個空的，防止以後報錯
                                items = new ArrayList<Item>();
                            }
                            ser.setSuggestions(items);
                        }
                    }
                    return;
                }
            }

            long eventTime = SystemClock.uptimeMillis();
            inputConnection
                    .sendKeyEvent(new KeyEvent(eventTime, eventTime,
                            KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, 0,
                            0, 0, KeyEvent.FLAG_SOFT_KEYBOARD
                                    | KeyEvent.FLAG_KEEP_TOUCH_MODE));
            inputConnection
                    .sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(),
                            eventTime, KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_DEL, 0, 0, 0, 0,
                            KeyEvent.FLAG_SOFT_KEYBOARD
                                    | KeyEvent.FLAG_KEEP_TOUCH_MODE));
        }
    }

}
