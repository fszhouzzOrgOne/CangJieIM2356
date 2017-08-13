package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import android.content.Context;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

public class InputMethodStatusCnCj35 extends InputMethodStatusCnCj {

    public InputMethodStatusCnCj35(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGEN35);
        this.setSubTypeName("倉35");
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusCnCjYhqm(this.getContext());
    }

    @Override
    public List<Item> getCandidatesInfo(String code) {
        return MbUtils.selectDbByCode(new String[] {
                MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN35 },
                code, false, null);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils
                .countDBLikeCode(new String[] { MbUtils.TYPE_CODE_CJINTERSECT,
                        MbUtils.TYPE_CODE_CJGEN35 }, code) > 0;
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGEN35);
    }
}
