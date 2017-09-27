package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import android.content.Context;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

public class InputMethodStatusCnCj5 extends InputMethodStatusCnCj {

    public InputMethodStatusCnCj5(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGEN5);
        this.setSubTypeName("倉5");
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusCnCj3(this.getContext());
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(new String[] {
                MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN5 },
                code, false, null, extraResolve);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils
                .countDBLikeCode(new String[] { MbUtils.TYPE_CODE_CJINTERSECT,
                        MbUtils.TYPE_CODE_CJGEN5 }, code) > 0;
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGEN5);
    }

}
