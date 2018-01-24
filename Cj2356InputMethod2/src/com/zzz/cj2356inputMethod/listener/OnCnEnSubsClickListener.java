package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.view.KeyboardBodyIniter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class OnCnEnSubsClickListener implements OnClickListener {

    private Context context;

    public OnCnEnSubsClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        try {
            // 如果原來是中文狀態，而且正在打字，先提交鍵名串
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputMethodStatus stat = ser.getInputMethodStatus();

            // 如果是中文輸入
            if (stat.isShouldTranslate()) {
                if (((InputMethodStatusCn) stat).isInputingCn()) {
                    // 模擬點擊一下打字鍵盤上的回車
                    KeyboardBodyIniter.performClickEnter();
                }
            }

            if (v.getId() == R.id.keybtnShift) {
                ser.setInputMethodStatus(ser.getInputMethodStatus().getNextStatus());
            }
        } catch (Exception e) {

        }
    }

}
