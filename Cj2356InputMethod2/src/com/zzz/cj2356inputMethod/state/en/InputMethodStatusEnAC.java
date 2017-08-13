package com.zzz.cj2356inputMethod.state.en;

import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;

public class InputMethodStatusEnAC extends InputMethodStatusEn {

    public InputMethodStatusEnAC(Context con) {
        super(con);
        this.setSubType("AA");
        this.setSubTypeName("大寫");
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusEnaa (this.getContext());
    }
    
    @Override
    public String getInputMethodName() {
        return "英文大寫";
    }
}
