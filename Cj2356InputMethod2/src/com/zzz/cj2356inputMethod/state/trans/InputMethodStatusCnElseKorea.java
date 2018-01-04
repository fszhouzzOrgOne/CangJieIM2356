package com.zzz.cj2356inputMethod.state.trans;

import java.util.Collections;
import java.util.Comparator;
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
public class InputMethodStatusCnElseKorea extends InputMethodStatusCnElse {

    private static String yanwenPtn = "[\\uAC00-\\uD7AF\\u1100-\\u11FF\\u3130-\\u318F\\uA960-\\uA97F\\uD7B0-\\uD7FF}]+";

    public InputMethodStatusCnElseKorea(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGENKOREA);
        this.setSubTypeName("韓");
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        List<Item> items = MbUtils.selectDbByCode(this.getSubType(), code, (null != code && code.length() > 1), code,
                false);
        // 排序，把韓文放前面
        if (null != items && !items.isEmpty()) {
            Collections.sort(items, new Comparator<Item>() {
                @Override
                public int compare(Item lhs, Item rhs) {
                    if (null == lhs.getEncode() || null == rhs.getEncode()) {
                        if (null == lhs.getEncode()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        if (lhs.getCharacter().matches(yanwenPtn)) {
                            return -1;
                        } else if (rhs.getCharacter().matches(yanwenPtn)) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return items;
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(this.getSubType(), code);
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(this.getSubType());
    }

}