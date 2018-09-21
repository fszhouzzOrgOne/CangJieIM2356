package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 刪除右邊
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年9月21日 下午11:55:50
 */
public class OnDeleteRightClickListener implements OnClickListener {

    private Context context;

    public OnDeleteRightClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.keybtnDeleteRight) {
            SendKeyEventUtil.doPerformDeleteRight(context);
        }
    }

}
