package com.zzz.cj2356inputMethod.listener;

import java.util.Timer;
import java.util.TimerTask;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.view.KeyboardBodyIniter;

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
        // 如果原來是中文狀態，而且正在打字，先提交鍵名串
        Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
        InputMethodStatus stat = ser.getInputMethodStatus();
        // 如果是中文輸入
        if (stat.isShouldTranslate()) {
            if (((InputMethodStatusCn) stat).isInputingCn()) {
                // 先模擬點擊一下打字鍵盤上的回車
                KeyboardBodyIniter.performClickEnter();
            }
        }

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

class LongDeleteRightTask extends TimerTask {
    private Context context;

    public LongDeleteRightTask(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void run() {
        SendKeyEventUtil.doPerformDeleteRight(context);
    }

}
