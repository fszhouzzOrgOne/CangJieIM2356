package com.zzz.cj2356inputMethod.listener;

import android.content.Context;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;

public class OnDeleteNumClickListener implements OnClickListener {

    private Context context;

    public OnDeleteNumClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
        InputConnection inputConnection = ser.getCurrentInputConnection();

        long eventTime = SystemClock.uptimeMillis();
        inputConnection.sendKeyEvent(new KeyEvent(eventTime, eventTime,
                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0,
                KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
        inputConnection
                .sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(),
                        eventTime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL, 0,
                        0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD
                                | KeyEvent.FLAG_KEEP_TOUCH_MODE));
    }

}
