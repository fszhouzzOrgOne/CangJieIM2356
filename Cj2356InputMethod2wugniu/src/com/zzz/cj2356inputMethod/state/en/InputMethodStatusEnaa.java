package com.zzz.cj2356inputMethod.state.en;

import java.util.Map;

import android.content.Context;

public class InputMethodStatusEnaa extends InputMethodStatusEn {

    public static final String SUBTYPE_CODE = "aa";
    public static final String SUBTYPE_NAME = "小寫";
    
    public InputMethodStatusEnaa(Context con) {
        super(con);
        this.setSubType(SUBTYPE_CODE);
        this.setSubTypeName(SUBTYPE_NAME);
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        for (String key : mbTransMap.keySet()) {
            mbTransMap.put(key, mbTransMap.get(key).toString().toLowerCase());
        }
        return mbTransMap;
    }

    @Override
    public String getInputMethodName() {
        return "英文" + SUBTYPE_NAME;
    }
}
