package com.zzz.cj2356inputMethod.state.trans;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

/**
 * 日本假名
 * 
 * @author t
 * @time 2017-1-9下午10:10:15
 */
public class InputMethodStatusCnElseKarina extends InputMethodStatusCnElse {

    private static Set<String> karinaSet = null;

    private static void karinaSetInit() {
        karinaSet = new HashSet<String>();
        String karinas = "あいうゔえおアイウヴエオぁぃぅぇぉァィゥェォかゕきくけゖこカヵキクケヶコがぎぐげごガギグゲゴさしすせそサシスセソざじずぜぞザジズゼゾたちつってとタチツッテトだぢづでどダヂヅデドなにぬねのナニヌネノはひふへほハヒフヘホばびぶべぼバビブベボぱぴぷぺぽパピプペポまみむめもマミムメモやゆよヤユヨゃゅょャュョらりるれろラリルレロわゎゐゑをワヮヷヰヸヱヹヲヺんンー゠々ゝゞヽヾ〆乄ゟ゚゛゜ヿ・";
        char[] kachas = karinas.toCharArray();
        for (Character c : kachas) {
            karinaSet.add(c.toString());
        }
    }

    public InputMethodStatusCnElseKarina(Context con) {
        super(con);
        this.setSubType(MbUtils.TYPE_CODE_KARINA);
        this.setSubTypeName("日");
    }

    @Override
    public List<Item> getCandidatesInfo(String code) {
        List<Item> items = MbUtils.selectDbByCode(MbUtils.TYPE_CODE_KARINA,
                code, false, null);
        // 排序
        if (null != items && !items.isEmpty()) {
            try {
                karinaSetInit();
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
                            String chaOne = lhs.getCharacter().charAt(0) + "";
                            String chaTwo = rhs.getCharacter().charAt(0) + "";
                            if (karinaSet.contains(chaOne)
                                    || karinaSet.contains(chaTwo)) {
                                // 都是假名符號
                                if (karinaSet.contains(chaOne)
                                        && karinaSet.contains(chaTwo)) {
                                    return lhs.getEncode().compareTo(
                                            rhs.getEncode());
                                } else if (karinaSet.contains(chaOne)) {
                                    return -1;
                                } else if (karinaSet.contains(chaTwo)) {
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }
                            // 全是漢字的，默認
                        }
                        return 0;
                    }

                });
            } catch (Exception e) {

            }
        }
        return items;
    }

    @Override
    public boolean couldContinueInputing(String code) {
        return MbUtils.countDBLikeCode(MbUtils.TYPE_CODE_KARINA, code) > 0;
    }

    @Override
    public InputMethodStatus getNextStatus() {
        return new InputMethodStatusCnElseManju(this.getContext());
    }

    @Override
    public String getInputMethodName() {
        return MbUtils.getInputMethodName(MbUtils.TYPE_CODE_KARINA);
    }

}
