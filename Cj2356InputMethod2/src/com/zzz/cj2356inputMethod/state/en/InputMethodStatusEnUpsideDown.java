package com.zzz.cj2356inputMethod.state.en;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;

public class InputMethodStatusEnUpsideDown extends InputMethodStatusEn {

    public static final String SUBTYPE_CODE = "ɐq";
    public static final String SUBTYPE_NAME = "英文反轉";

    public InputMethodStatusEnUpsideDown(Context con) {
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
        String letters2 = " ɐ q ɔ p ə ɟ ɓ ɥ ᴉ ſ̣ ʞ ɿ ɯ u o d b ɹ s ʇ n ʌ ʍ x ʎ z ";
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        int index = 0;
        for (String one : letters2.trim().split(" +")) {
            mbTransMap.put(letters1.substring(index, index + 1), one);
            index++;
        }
        return mbTransMap;
    }

    @Override
    public boolean isNewCursorPositionRight() {
        return false;
    }
}
