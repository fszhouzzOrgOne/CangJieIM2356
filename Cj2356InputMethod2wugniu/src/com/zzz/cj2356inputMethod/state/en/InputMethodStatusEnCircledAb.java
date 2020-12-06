package com.zzz.cj2356inputMethod.state.en;

import java.util.Map;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;

public class InputMethodStatusEnCircledAb extends InputMethodStatusEn {

    public static final String SUBTYPE_CODE = "Ⓐⓐ";
    public static final String SUBTYPE_NAME = "大小寫圈";

    public InputMethodStatusEnCircledAb(Context con) {
        super(con);
        this.setSubType(SUBTYPE_CODE);
        this.setSubTypeName(SUBTYPE_NAME);
    }

    @Override
    public String getInputMethodName() {
        return "英文" + SUBTYPE_NAME;
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        String letters1 = "abcdefghijklmnopqrstuvwxyz";
        String letters2 = "ⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏ";
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        int index = 0;
        while (index <= letters1.length() - 1) {
            mbTransMap.put(letters1.substring(index, index + 1),
                    letters2.substring(index, index + 1));
            index++;
        }
        return mbTransMap;
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
            } while (!InputMethodStatusEnCircledaa.SUBTYPE_CODE
                    .equals(statNext.getSubType()));
            ser.setInputMethodStatus(statNext);
            return true;
        }
        return false;
    }
}
