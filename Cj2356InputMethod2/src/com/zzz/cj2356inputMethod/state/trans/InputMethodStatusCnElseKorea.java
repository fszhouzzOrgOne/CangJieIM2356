package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;

import android.content.Context;

/**
 * 朝鮮諺文
 * 
 * @author t
 * @time 2017-2-9下午9:36:55
 */
public class InputMethodStatusCnElseKorea extends
        InputMethodStatusCnElse {

    public InputMethodStatusCnElseKorea(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGENKOREA);
        this.setSubTypeName("韓");
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(this.getSubType(), code,
                (null != code && code.length() > 1), code, false);
    }
    
    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.countDBLikeCode(this.getSubType(), code) > 0;
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(this.getSubType());
    }

}