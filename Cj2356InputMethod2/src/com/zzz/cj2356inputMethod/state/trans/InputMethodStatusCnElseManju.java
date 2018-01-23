package com.zzz.cj2356inputMethod.state.trans;

import java.util.ArrayList;
import java.util.List;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.utils.ManjuTypingTest;

import android.content.Context;

/**
 * 圈點滿文
 * 
 * @author t
 * @time 2017-2-9下午9:36:55
 */
public class InputMethodStatusCnElseManju extends InputMethodStatusCnElse {

    public InputMethodStatusCnElseManju(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGENMANJU);
        this.setSubTypeName("滿");
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        List<Item> res = new ArrayList<Item>();
        if (null != code && code.length() > 0) {
            List<String> manjus = ManjuTypingTest.getManjuFromRoman(code);
            if (null != manjus && !manjus.isEmpty()) {
                for (String geul : manjus) {
                    Item it = new Item(null, MbUtils.TYPE_CODE_CJGENMANJU, code, geul);
                    res.add(it);
                }
            }
        }
        return res;
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return true;
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(this.getSubType());
    }

}