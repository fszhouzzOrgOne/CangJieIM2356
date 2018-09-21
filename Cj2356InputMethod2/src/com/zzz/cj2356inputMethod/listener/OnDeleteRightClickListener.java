package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.view.KeyboardBodyIniter;

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

            SendKeyEventUtil.doPerformDeleteRight(context);
        }
    }

}
