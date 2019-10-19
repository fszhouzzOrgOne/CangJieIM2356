package com.zzz.cj2356inputMethod.state.trans;

import java.util.List;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;

import android.content.Context;
import android.view.View;

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
    public void setKeysBackground(List<View> letterViews,
            List<Integer> letterViewsBgIds) {
        super.setKeysBackground(letterViews, letterViewsBgIds);
        // 注音符號：q加聲調背景
        View vm = letterViews.get(7 + 7 + 1);
        vm.setBackgroundResource(R.drawable.keyboard_button_tone_selector);
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_ZYFH);
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        return MbUtils.selectDbByCode(MbUtils.TYPE_CODE_ZYFH, code, true, code,
                false);
    }

    @Override
    public List<Item> getCandidatesInfoByChar(String cha) {
        return MbUtils.selectDbByChar(this.getSubType(), cha);
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.existsDBLikeCode(MbUtils.TYPE_CODE_ZYFH, code);
    }

}
