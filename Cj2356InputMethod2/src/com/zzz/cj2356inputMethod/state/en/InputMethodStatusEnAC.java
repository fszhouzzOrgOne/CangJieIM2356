package com.zzz.cj2356inputMethod.state.en;

import android.content.Context;

public class InputMethodStatusEnAC extends InputMethodStatusEn {

    public static final String SUBTYPE_CODE = "AA";
    public static final String SUBTYPE_NAME = "大寫";
    
    public InputMethodStatusEnAC(Context con) {
        super(con);
        this.setSubType(SUBTYPE_CODE);
        this.setSubTypeName(SUBTYPE_NAME);
    }

    @Override
    public String getInputMethodName() {
        return "英文" + SUBTYPE_NAME;
    }
}
