package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

public class InputMethodStatusCnCj6 extends InputMethodStatusCnCj {

    public InputMethodStatusCnCj6(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGEN6);
        this.setSubTypeName("蒼6");
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusCnCj5(this.getContext());
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        mbTransMap.put("h", "的");
        mbTransMap.put("x", "止");
        mbTransMap.put("z", "片");
        return mbTransMap;
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(new String[] {
                MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN6 },
                code, false, null, extraResolve);
    }
    
    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(new String[] {
                MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGEN6 }, cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils
                .countDBLikeCode(new String[] { MbUtils.TYPE_CODE_CJINTERSECT,
                        MbUtils.TYPE_CODE_CJGEN6 }, code) > 0;
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGEN6);
    }

}
