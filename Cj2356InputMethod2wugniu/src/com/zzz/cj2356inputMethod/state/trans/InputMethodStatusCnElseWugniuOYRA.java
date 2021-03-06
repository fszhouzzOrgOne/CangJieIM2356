package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;

import android.content.Context;

/**
 * 吳語甌越瑞安
 */
public class InputMethodStatusCnElseWugniuOYRA extends InputMethodStatusCnElse {

    public InputMethodStatusCnElseWugniuOYRA(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGEN_WUGNIUOYRUIAN);
        this.setSubTypeName("RA");
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(this.getSubType());
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(this.getSubType(), code, false, code, false);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(this.getSubType(), code);
    }

}
