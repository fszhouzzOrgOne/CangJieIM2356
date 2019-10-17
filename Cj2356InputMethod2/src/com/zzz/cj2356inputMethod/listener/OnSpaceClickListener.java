package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/** 空格鍵事件 */
public class OnSpaceClickListener implements OnClickListener {

    private Context context;

    public OnSpaceClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.keybtnSpace) {
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputMethodStatus stat = ser.getInputMethodStatus();
            stat.mainSpaceClickAction();
        }
    }

}
