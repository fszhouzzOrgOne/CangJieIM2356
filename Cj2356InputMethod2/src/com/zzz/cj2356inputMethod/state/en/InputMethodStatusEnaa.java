package com.zzz.cj2356inputMethod.state.en;

import java.util.Map;

import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;

public class InputMethodStatusEnaa extends InputMethodStatusEn {

    public InputMethodStatusEnaa(Context con) {
        super(con);
        this.setSubType("aa");
        this.setSubTypeName("小寫");
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusEnAb(this.getContext());
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        for (String key : mbTransMap.keySet()) {
            mbTransMap.put(key, mbTransMap.get(key).toString().toLowerCase());
        }
        return mbTransMap;
    }

    @Override
    public String getInputMethodName() {
        return "英文小寫";
    }
}
