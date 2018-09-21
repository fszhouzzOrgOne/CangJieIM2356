package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class OnDeleteNumClickListener implements OnClickListener {

    private Context context;

    public OnDeleteNumClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        SendKeyEventUtil.doPerformDelete(context);
    }

}
