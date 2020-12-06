package com.zzz.cj2356inputMethod.task;

import java.util.TimerTask;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;

public class LongSpaceTask extends TimerTask {
    private Context context;

    public LongSpaceTask(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void run() {
        Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
        InputMethodStatus stat = ser.getInputMethodStatus();
        stat.mainSpaceClickAction();
    }

}
