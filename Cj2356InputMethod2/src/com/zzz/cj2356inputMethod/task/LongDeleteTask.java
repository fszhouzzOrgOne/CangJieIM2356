package com.zzz.cj2356inputMethod.task;

import java.util.TimerTask;

import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;

import android.content.Context;

/**
 * 長刪除任務
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2019年10月17日 下午10:53:52
 */
public class LongDeleteTask extends TimerTask {
    private Context context;

    public LongDeleteTask(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void run() {
        SendKeyEventUtil.doPerformDelete(context);
    }

}
