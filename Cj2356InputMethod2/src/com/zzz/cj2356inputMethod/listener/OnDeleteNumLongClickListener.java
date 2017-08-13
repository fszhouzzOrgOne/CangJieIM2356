package com.zzz.cj2356inputMethod.listener;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputConnection;

public class OnDeleteNumLongClickListener implements OnLongClickListener {

    private Context context;

    public OnDeleteNumLongClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public boolean onLongClick(View v) {
        // 定時刪除
        final Timer timer = new Timer();
        timer.schedule(new LongDeleteTaskNum(context), 1000, 50);
        // 擡手就不再刪除
        v.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 抬起操作
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    timer.cancel();
                }
                return false;
            }

        });
        return false;
    }
}

class LongDeleteTaskNum extends TimerTask {
    private Context context;

    public LongDeleteTaskNum(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void run() {
        InputConnection inputConnection = ((InputMethodService) context)
                .getCurrentInputConnection();

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
