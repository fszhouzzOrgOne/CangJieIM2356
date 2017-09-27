package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import android.content.Context;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

public class InputMethodStatusCnCj2 extends InputMethodStatusCnCj {

    public InputMethodStatusCnCj2(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGEN2);
        this.setSubTypeName("倉2");
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusCnCj6(this.getContext());
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(MbUtils.TYPE_CODE_CJGEN2, code, false, null, extraResolve);
    }
    
    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(MbUtils.TYPE_CODE_CJGEN2, cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.countDBLikeCode(MbUtils.TYPE_CODE_CJGEN2, code) > 0;
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGEN2);
    }

}
