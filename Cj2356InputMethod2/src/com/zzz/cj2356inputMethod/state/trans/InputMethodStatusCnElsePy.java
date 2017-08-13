package com.zzz.cj2356inputMethod.state.trans;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

/**
 * 普通話拼音
 */
public class InputMethodStatusCnElsePy extends InputMethodStatusCnElse {

    public InputMethodStatusCnElsePy(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_PINYIN);
        this.setSubTypeName("拼");
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_PINYIN);
    }

    @Override
    public List<Item> getCandidatesInfo(String code) {
        List<Item> items = MbUtils.selectDbByCode(
                MbUtils.TYPE_CODE_PINYIN,
                code,
                (null != code && (code.length() > 1
                        || "a".equalsIgnoreCase(code)
                        || "e".equalsIgnoreCase(code) || "o"
                        .equalsIgnoreCase(code))), code + "m");

        // 排序
        if (null != items && !items.isEmpty()) {
            try {
                Collections.sort(items, new Comparator<Item>() {

                    @Override
                    public int compare(Item one, Item two) {
                        String num1 = translateCode2Name(one.getEncode());
                        String num2 = translateCode2Name(two.getEncode());
                        if (null == num1 || null == num2) {
                            if (null == num1) {
                                return 1; // 编码爲空，在最後
                            } else {
                                return -1;
                            }
                        } else {
                            int numComp = num1.compareTo(num2);
                            return numComp;
                        }
                    }
                });
            } catch (Exception e) {
                Toast.makeText(getContext(),
                        "結果排序失敗：" + translateCode2Name(code), Toast.LENGTH_LONG)
                        .show();
            }
        }
        return items;
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.countDBLikeCode(MbUtils.TYPE_CODE_PINYIN, code) > 0;
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusCnElseZyfh(this.getContext());
    }

    @Override
    public String getInputingCnValueForEnter() {
        String code = getInputingCnCode();
        return translateCode2Name(code);
    }

    @Override
    public String translateCode2Name(String str) {
        String result = super.translateCode2Name(str);
        String code = result;
        if (null != code && code.length() > 1
                && code.toLowerCase().endsWith("m")) {
            int start = code.toLowerCase().indexOf("m");
            if (start == 0) {
                start = 1;
            }
            String ms = code.substring(start);
            result = code.substring(0, start) + ms.length();
        }
        return result;
    }

}
