package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;

import android.content.Context;

/**
 * 國際音標
 * 
 * @author t
 * @time 2017-1-9下午10:10:25
 */
public class InputMethodStatusCnElseIpa extends InputMethodStatusCnElse {

    public InputMethodStatusCnElseIpa(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGEN_IPA);
        this.setSubTypeName("音");
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGEN_IPA);
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(MbUtils.TYPE_CODE_CJGEN_IPA, code, false, code, false);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(MbUtils.TYPE_CODE_CJGEN_IPA, code);
    }

}
