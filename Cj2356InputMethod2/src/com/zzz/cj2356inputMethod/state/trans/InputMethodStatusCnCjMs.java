package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;
import java.util.Map;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;

import android.content.Context;

/**
 * 微軟倉頡
 * 
 * @author t
 * @time 2017-2-24下午10:23:45
 */
public class InputMethodStatusCnCjMs extends InputMethodStatusCnCj {

    public InputMethodStatusCnCjMs(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGENMS);
        this.setSubTypeName("MS");
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        mbTransMap.put("z", "重");
        return mbTransMap;
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(new String[] { MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGENMS }, code,
                false, null, extraResolve);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(new String[] { MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGENMS }, cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(new String[] { MbUtils.TYPE_CODE_CJINTERSECT, MbUtils.TYPE_CODE_CJGENMS },
                code);
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGENMS);
    }
}
