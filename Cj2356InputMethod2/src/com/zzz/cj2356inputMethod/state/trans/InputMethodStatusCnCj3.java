package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import android.content.Context;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

public class InputMethodStatusCnCj3 extends InputMethodStatusCnCj {

    public InputMethodStatusCnCj3(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGEN3);
        this.setSubTypeName("倉3");
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusCnCjYhqm(this.getContext());
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(new String[] {
                MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN3 },
                code, false, null, extraResolve);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils
                .countDBLikeCode(new String[] { MbUtils.TYPE_CODE_CJINTERSECT,
                        MbUtils.TYPE_CODE_CJGEN3 }, code) > 0;
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGEN3);
    }
}
