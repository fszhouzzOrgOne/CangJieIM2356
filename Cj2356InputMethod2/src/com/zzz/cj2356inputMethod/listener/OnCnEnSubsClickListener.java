package com.zzz.cj2356inputMethod.listener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.StringUtils;

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
            if (InputMethodStatusCn.TYPE_CODE.equals(stat.getType())) {
                if (((InputMethodStatusCn) stat).isInputingCn()) {
                    String value = ((InputMethodStatusCn) stat).getInputingCnValue();
                    if (StringUtils.hasText(value)) {
                        // 获得InputConnection对象
                        InputConnection inputConnection = ser.getCurrentInputConnection();
                        inputConnection.commitText(value, 1);
                    }
                    ((InputMethodStatusCn) stat).setInputingCn(false);
                }
            }

            if (v.getId() == R.id.keybtnCnEn) {
                ser.setInputMethodStatus(ser.getInputMethodStatus().getNextStatusType());
            } else if (v.getId() == R.id.keybtnShift) {
                ser.setInputMethodStatus(ser.getInputMethodStatus().getNextStatus());
            }
        } catch (Exception e) {
            Toast.makeText(context, "OnCnEnSubs: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
