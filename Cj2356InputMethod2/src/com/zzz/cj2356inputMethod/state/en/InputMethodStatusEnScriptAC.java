package com.zzz.cj2356inputMethod.state.en;

import java.util.Map;

import android.content.Context;

public class InputMethodStatusEnScriptAC extends InputMethodStatusEn {

    public static final String SUBTYPE_CODE = "SA";
    public static final String SUBTYPE_NAME = "小字";

    public InputMethodStatusEnScriptAC(Context con) {
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
        String letters2 = "𝒜ℬ𝒞𝒟ℰℱ𝒢ℋℐ𝒥𝒦ℒℳ𝒩𝒪𝒫𝒬ℛ𝒮𝒯𝒰𝒱𝒲𝒳𝒴𝒵";
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        int index = 0;
        while (index <= letters1.length() - 1) {
            mbTransMap.put(letters1.substring(index, index + 1), letters2.substring(index, index + 1));
            index++;
        }
        return mbTransMap;
    }
}
