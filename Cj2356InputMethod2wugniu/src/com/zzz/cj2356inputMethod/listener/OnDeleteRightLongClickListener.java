package com.zzz.cj2356inputMethod.listener;

import java.util.Timer;

import com.zzz.cj2356inputMethod.task.LongDeleteRightTask;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/**
 * 刪除右邊，長按
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年9月21日 下午11:55:32
 */
public class OnDeleteRightLongClickListener implements OnLongClickListener {

    private Context context;

    public OnDeleteRightLongClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public boolean onLongClick(View v) {
        // 定時刪除
        final Timer timer = new Timer();
        timer.schedule(new LongDeleteRightTask(context), 500, 50);
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
