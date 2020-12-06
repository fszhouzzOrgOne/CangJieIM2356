package com.zzz.cj2356inputMethod.listener;

import java.util.Timer;

import com.zzz.cj2356inputMethod.task.LongSpaceTask;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/**
 * 數字鍵盤的空格長按
 */
public class OnSpaceNumLongClickListener implements OnLongClickListener {

    private Context context;

    public OnSpaceNumLongClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public boolean onLongClick(View v) {
        // 定時刪除
        final Timer timer = new Timer();
        timer.schedule(new LongSpaceTask(context), 500, 50);
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
