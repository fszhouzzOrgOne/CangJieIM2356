package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import android.content.Context;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

/**
 * 注音符號
 * 
 * @author t
 * @time 2017-1-9下午10:10:25
 */
public class InputMethodStatusCnElseZyfh extends InputMethodStatusCnElse {

    public InputMethodStatusCnElseZyfh(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_ZYFH);
        this.setSubTypeName("注");
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_ZYFH);
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(MbUtils.TYPE_CODE_ZYFH, code, true, code, false);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.countDBLikeCode(MbUtils.TYPE_CODE_ZYFH, code) > 0;
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusCnElseKarina(this.getContext());
    }

}
