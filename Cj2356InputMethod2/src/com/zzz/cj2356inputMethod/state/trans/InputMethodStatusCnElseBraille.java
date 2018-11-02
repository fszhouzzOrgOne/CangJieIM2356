package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;

import android.content.Context;

/**
 * 盲文
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年11月2日 下午9:00:23
 */
public class InputMethodStatusCnElseBraille extends InputMethodStatusCnElse {

    public InputMethodStatusCnElseBraille(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGENBRAILLE);
        this.setSubTypeName("盲");
    }

    @Override
    public String getInputMethodName() {
        return "盲文點陣";
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return null;
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        List<Item> items = null;
        return items;
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return true;
    }

}
