package com.zzz.cj2356inputMethod.state.trans;

import android.content.Context;

import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnaa;

/**
 * 其他輸入法
 * 
 * @author t
 * @time 2017-2-9下午9:36:55
 */
public abstract class InputMethodStatusCnElse extends InputMethodStatusCn {

    InputMethodStatusCnElse(Context con) {
        super(con);
        this.setTypeName("牠");
    }

    public final InputMethodStatus getNextStatusType() {
        return new InputMethodStatusEnaa(this.getContext());
    }

}
