package com.zzz.cj2356inputMethod.listener;

import java.util.Timer;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethodWugniu.R;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.task.LongSpaceTask;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/** 空格鍵長按事件 */
public class OnSpaceLongClickListener implements OnLongClickListener {

    private Context context;

    public OnSpaceLongClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.keybtnSpace) {
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputMethodStatus stat = ser.getInputMethodStatus();
            stat.mainSpaceClickAction();
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
        return false;
    }

}
