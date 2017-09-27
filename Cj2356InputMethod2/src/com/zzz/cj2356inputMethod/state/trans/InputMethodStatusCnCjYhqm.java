package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;
import java.util.Map;

import android.content.Context;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

/**
 * 雅虎奇摩倉頡
 * 
 * @author t
 * @time 2017-2-24下午10:23:45
 */
public class InputMethodStatusCnCjYhqm extends InputMethodStatusCnCj {

    public InputMethodStatusCnCjYhqm(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGENYAHOO);
        this.setSubTypeName("YH");
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        mbTransMap.put("z", "重");
        return mbTransMap;
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusCnCjMs(this.getContext());
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(new String[] {
                MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGENYAHOO },
                code, false, null, extraResolve);
    }
    
    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(new String[] {
                MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGENYAHOO }, cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.countDBLikeCode(new String[] {
                MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGENYAHOO },
                code) > 0;
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGENYAHOO);
    }
}
