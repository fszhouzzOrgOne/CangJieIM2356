package com.zzz.cj2356inputMethod.listener;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.StringUtils;

/**
 * 回車鍵按下，發佈事件
 * 
 * @author t
 * @time 2017-1-7下午7:32:05
 */
public class OnEnterClickListener implements OnClickListener {

    private Context context;

    public OnEnterClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.keybtnEnter) {
            // 如果原來是中文狀態，而且正在打字，提交鍵名串，就返回
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputMethodStatus stat = ser.getInputMethodStatus();
            // 如果是中文輸入
            if (InputMethodStatusCn.TYPE_CODE.equals(stat.getType())) {
                if (((InputMethodStatusCn) stat).isInputingCn()) {
                    String value = ((InputMethodStatusCn) stat)
                            .getInputingCnValueForEnter();
                    if (StringUtils.hasText(value)) {
                        // 获得InputConnection对象
                        InputConnection inputConnection = ser
                                .getCurrentInputConnection();
                        inputConnection.commitText(value, 1);
                    }
                    ((InputMethodStatusCn) stat).setInputingCn(false);
                    return;
                }
            }

            InputConnection inputConnection = (InputConnection) ((InputMethodService) context)
                    .getCurrentInputConnection();

            long eventTime = SystemClock.uptimeMillis();
            inputConnection
                    .sendKeyEvent(new KeyEvent(eventTime, eventTime,
                            KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 0, 0,
                            0, 0, KeyEvent.FLAG_SOFT_KEYBOARD
                                    | KeyEvent.FLAG_KEEP_TOUCH_MODE));
            inputConnection
                    .sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(),
                            eventTime, KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_ENTER, 0, 0, 0, 0,
                            KeyEvent.FLAG_SOFT_KEYBOARD
                                    | KeyEvent.FLAG_KEEP_TOUCH_MODE));
        }
    }
}
