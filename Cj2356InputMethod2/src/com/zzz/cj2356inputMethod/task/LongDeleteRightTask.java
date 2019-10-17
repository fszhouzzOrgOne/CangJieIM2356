package com.zzz.cj2356inputMethod.task;

import java.util.TimerTask;

import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;

import android.content.Context;

/**
 * 長刪除右邊任務
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2019年10月17日 下午11:15:06
 */
public class LongDeleteRightTask extends TimerTask {
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