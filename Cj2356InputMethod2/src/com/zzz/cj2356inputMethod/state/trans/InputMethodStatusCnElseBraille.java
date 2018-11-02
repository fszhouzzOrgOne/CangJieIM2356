package com.zzz.cj2356inputMethod.state.trans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.utils.BrailleTest;

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
        if (null == code || "".equals(code.trim())) {
            return null;
        }
        List<Item> items = new ArrayList<Item>();
        List<String> cadis = BrailleTest.getCandidatesByInput(code);
        int i = 1;
        for (String ca : cadis) {
            Item it = new Item(i, this.getSubType(), code, ca);
            items.add(it);
            i++;
        }
        return items;
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return true;
    }

    @Override
    public String getInputingCnValueForEnter() {
        return this.getInputingCnCode();
    }

    @Override
    public String getComposingTextForInputConn() {
        return this.getInputingCnCode();
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        String letters1 = "qwertyuiopasdfghjklzxcvbnm";
        String letters2 = " 0 1 2 3 4 5 6 7 8 p a s d f g h j k l z x c v b n m ";
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        int index = 0;
        for (String one : letters2.trim().split(" +")) {
            mbTransMap.put(letters1.substring(index, index + 1), one);
            index++;
        }
        return mbTransMap;
    }

}
