package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 主鍵盤刪除左邊
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年9月21日 下午11:56:00
 */
public class OnDeleteClickListener implements OnClickListener {

    private Context context;

    public OnDeleteClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.keybtnDelete) {
            // 如果原來是中文狀態，而且正在打字
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputMethodStatus stat = ser.getInputMethodStatus();
            stat.mainDeleteClickAction();
        }
    }

}
