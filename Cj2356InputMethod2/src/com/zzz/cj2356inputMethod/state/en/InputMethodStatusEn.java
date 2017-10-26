package com.zzz.cj2356inputMethod.state.en;

import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;

/**
 * 英文輸入狀態
 * 
 * @author t
 * @time 2017-1-7下午9:54:59
 */
public abstract class InputMethodStatusEn extends InputMethodStatus {

    public static final String TYPE_CODE = "en";
    public static final String TYPE_NAME = "英";

    InputMethodStatusEn(Context con) {
        super(con);
        this.setType(TYPE_CODE);
        this.setTypeName(TYPE_NAME);
    }

}
