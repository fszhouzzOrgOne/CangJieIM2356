package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;
import android.view.View;
import android.view.View.OnLongClickListener;

/**
 * 主鍵盤刪除左邊，長按
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年9月21日 下午11:56:14
 */
public class OnDeleteLongClickListener implements OnLongClickListener {

    private Context context;

    public OnDeleteLongClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.keybtnDelete) {
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputMethodStatus stat = ser.getInputMethodStatus();
            stat.mainDeleteLongClickAction(v);
        }
        return false;
    }
}
