package com.zzz.cj2356inputMethod.state.trans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.utils.SionTanTest;

import android.content.Context;
import android.view.View;

/**
 * 曾版湘潭話
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年12月15日 上午10:14:02
 */
public class InputMethodStatusCnElseSionTanTseng
        extends InputMethodStatusCnElse {

    public InputMethodStatusCnElseSionTanTseng(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_CJGEN_SIONTANTSENG);
        this.setSubTypeName("潭");
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(this.getSubType());
    }

    @Override
    public List<Item> getCandidatesInfo(String code, boolean extraResolve) {
        if (null == code || "".equals(code)) {
            return null;
        }
        List<String> ipas = SionTanTest.getResultList(code);
        if (null == ipas || ipas.isEmpty()) {
            return null;
        }
        String tempCode = ipas.get(0);
        // 只有沒有聲調的才模糊查詢，有聲調了就不再模糊查詢了
        boolean isPrompt = null != tempCode && tempCode.trim().length() > 0
                && !tempCode.matches("[^¹²³⁴⁵]+[¹²³⁴⁵]+.*");
        List<Item> items = MbUtils.selectDbByCode(this.getSubType(), tempCode,
                isPrompt, tempCode, false);

        List<Item> res = new ArrayList<Item>();
        for (String ipa : ipas) {
            res.add(new Item(null, this.getSubType(), ipas.get(0), ipa));
        }
        res.addAll(items);
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
    public void setKeysBackground(List<View> letterViews,
            List<Integer> letterViewsBgIds) {
        super.setKeysBackground(letterViews, letterViewsBgIds);
        // Q加聲調背景
        View vq = letterViews.get(7 + 7 + 3 - 1);
        vq.setBackgroundResource(R.drawable.keyboard_button_tone_selector);
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        Map<String, Object> map = SionTanTest.getKeyNameMap();
        map.put("q", "q");
        return map;
    }

    // q不展示鍵名
    @Override
    public String getKeyName(String key) {
        if (null == key || "q".equals(key.toLowerCase())) {
            return null;
        }
        Map<String, Object> map = SionTanTest.getKeyNameMap();
        return map.get(key.toLowerCase()).toString();
    }
}
