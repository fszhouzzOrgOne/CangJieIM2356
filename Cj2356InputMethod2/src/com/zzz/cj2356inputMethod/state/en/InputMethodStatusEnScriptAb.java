package com.zzz.cj2356inputMethod.state.en;

import java.util.List;
import java.util.Map;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;
import android.view.View;

public class InputMethodStatusEnScriptAb extends InputMethodStatusEn {

    public static final String SUBTYPE_CODE = "Sa";
    public static final String SUBTYPE_NAME = "花體大小寫";

    public InputMethodStatusEnScriptAb(Context con) {
        super(con);
        this.setSubType(SUBTYPE_CODE);
        this.setSubTypeName(SUBTYPE_NAME);
    }

    @Override
    public void setKeysBackground(List<View> letterViews,
            List<Integer> letterViewsBgIds) {
        for (int i = 0; i < letterViews.size(); i++) {
            View v = letterViews.get(i);
            v.setBackgroundResource(letterViewsBgIds.get(i));
        }
    }

    @Override
    public String getInputMethodName() {
        return SUBTYPE_NAME;
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        String letters1 = "abcdefghijklmnopqrstuvwxyz";
        String letters2 = " 𝒜 ℬ 𝒞 𝒟 ℰ ℱ 𝒢 ℋ ℐ 𝒥 𝒦 ℒ ℳ 𝒩 𝒪 𝒫 𝒬 ℛ 𝒮 𝒯 𝒰 𝒱 𝒲 𝒳 𝒴 𝒵 ";
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        int index = 0;
        for (String one : letters2.trim().split(" +")) {
            mbTransMap.put(letters1.substring(index, index + 1), one);
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
            } while (!InputMethodStatusEnScriptaa.SUBTYPE_CODE
                    .equals(statNext.getSubType()));
            ser.setInputMethodStatus(statNext);
            return true;
        }
        return false;
    }
}
