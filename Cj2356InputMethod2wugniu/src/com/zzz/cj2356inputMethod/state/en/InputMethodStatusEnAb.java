package com.zzz.cj2356inputMethod.state.en;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;

public class InputMethodStatusEnAb extends InputMethodStatusEn {

    public static final String SUBTYPE_CODE = "Aa";
    public static final String SUBTYPE_NAME = "大小寫";

    public InputMethodStatusEnAb(Context con) {
        super(con);
        this.setSubType(SUBTYPE_CODE);
        this.setSubTypeName(SUBTYPE_NAME);
    }

    @Override
    public String getInputMethodName() {
        return "英文大小寫";
    }

    @Override
    public boolean mainKeyTouchAction(String btnText, String keyPrsd) {
        if (super.mainKeyTouchAction(btnText, keyPrsd)) {
            // 如果是大小寫狀態，馬上回到小寫
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputMethodStatus stat = ser.getInputMethodStatus();
            InputMethodStatus statNext = stat;
            do {
                statNext = statNext.getNextStatus();
                if (statNext.getSubType().equals(stat.getSubType())) {
                    // 轉了一圈，回到原點
                    break;
                }
            } while (!InputMethodStatusEnaa.SUBTYPE_CODE
                    .equals(statNext.getSubType()));
            ser.setInputMethodStatus(statNext);
            return true;
        }
        return false;
    }
}
