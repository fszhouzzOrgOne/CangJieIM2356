package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import android.content.Context;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

/**
 * 圈點滿文
 * 
 * @author t
 * @time 2017-2-9下午9:36:55
 */
public class InputMethodStatusCnElseManju extends
        InputMethodStatusCnElse {

    public InputMethodStatusCnElseManju(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGENMANJU);
        this.setSubTypeName("滿");
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(this.getSubType(), code,
                (null != code), code, false);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.countDBLikeCode(this.getSubType(), code) > 0;
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusCnElseKorea(this.getContext());
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(this.getSubType());
    }

}