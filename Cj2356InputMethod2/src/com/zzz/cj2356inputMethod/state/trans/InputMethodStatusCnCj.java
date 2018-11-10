package com.zzz.cj2356inputMethod.state.trans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;

/**
 * 倉頡輸入法
 */
public abstract class InputMethodStatusCnCj extends InputMethodStatusCn {

    InputMethodStatusCnCj(Context con) {
        super(con);
        this.setType("cj");
        this.setTypeName("倉");
    }

    @Override
    public void setKeysBackground(List<View> letterViews,
            List<Integer> letterViewsBgIds) {
        for (int i = 0; i < letterViews.size(); i++) {
            View v = letterViews.get(i);
            v.setBackgroundResource(letterViewsBgIds.get(i));
        }
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        Map<String, Object> mbTransMap = new HashMap<String, Object>();
        mbTransMap.put("a", "日");
        mbTransMap.put("b", "月");
        mbTransMap.put("c", "金");
        mbTransMap.put("d", "木");
        mbTransMap.put("e", "水");
        mbTransMap.put("f", "火");
        mbTransMap.put("g", "土");
        mbTransMap.put("h", "竹");
        mbTransMap.put("i", "戈");
        mbTransMap.put("j", "十");
        mbTransMap.put("k", "大");
        mbTransMap.put("l", "中");
        mbTransMap.put("m", "一");
        mbTransMap.put("n", "弓");
        mbTransMap.put("o", "人");
        mbTransMap.put("p", "心");
        mbTransMap.put("q", "手");
        mbTransMap.put("r", "口");
        mbTransMap.put("s", "尸");
        mbTransMap.put("t", "廿");
        mbTransMap.put("u", "山");
        mbTransMap.put("v", "女");
        mbTransMap.put("w", "田");
        mbTransMap.put("x", "難");
        mbTransMap.put("y", "卜");
        mbTransMap.put("z", "符");
        return mbTransMap;
    }
}
