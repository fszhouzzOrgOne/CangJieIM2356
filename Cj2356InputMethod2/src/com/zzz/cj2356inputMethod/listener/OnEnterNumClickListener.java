package com.zzz.cj2356inputMethod.listener;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;

/**
 * 數字鍵盤的回車鍵按下，發佈事件
 */
public class OnEnterNumClickListener implements OnClickListener {

    private Context context;

    public OnEnterNumClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        doPerformEnter(context);
    }

    public static void doPerformEnter(Context context) {
        InputConnection inputConnection = (InputConnection) ((InputMethodService) context).getCurrentInputConnection();

        long eventTime = SystemClock.uptimeMillis();
        inputConnection.sendKeyEvent(new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 0,
                0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
        inputConnection.sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), eventTime, KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_ENTER, 0, 0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
    }
}
