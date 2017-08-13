package com.zzz.cj2356inputMethod.state.en;

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
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusEnAC(this.getContext());
    }

    @Override
    public String getInputMethodName() {
        return "英文大小寫";
    }
}
