package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;

import android.content.Context;
import android.view.View;

/**
 * 泰文輸入法
 * 
 * @author t
 * @time 2025-01-21
 */
public class InputMethodStatusCnElseThai extends InputMethodStatusCnElse {
    /**
     * 用什麼代替聲調
     */
    private static final String TONE_REPLACE_CHAR = "q";

    public InputMethodStatusCnElseThai(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGEN_THAI);
        this.setSubTypeName("泰");
    }

    @Override
    public void setKeysBackground(List<View> letterViews, List<Integer> letterViewsBgIds) {
        super.setKeysBackground(letterViews, letterViewsBgIds);
        // q加聲調背景
        View vm = letterViews.get(7 + 7 + 2);
        vm.setBackgroundResource(R.drawable.keyboard_button_tone_selector);
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_CJGEN_THAI);
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(MbUtils.TYPE_CODE_CJGEN_THAI, code, true, code,
                false);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(MbUtils.TYPE_CODE_CJGEN_THAI, code);
    }
}
