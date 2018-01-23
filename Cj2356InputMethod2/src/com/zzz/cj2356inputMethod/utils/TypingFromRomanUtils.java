package com.zzz.cj2356inputMethod.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 按羅馬字打字，自動组詞工具<br/>
 * 用於韓文、滿文等
 * 
 * @author fszhouzz@qq.com
 * @time 2018年1月23日下午3:04:11
 */
public class TypingFromRomanUtils {

    /**
     * 按長度排序
     * 
     * @author fszhouzz@qq.com
     * @param res
     */
    public static void sortListByLengthAndSo(List<String> res) {
        Collections.sort(res, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o2.length() < o1.length()) {
                    return 1;
                } else if (o2.length() > o1.length()) {
                    return -1;
                } else {
                    return o1.compareTo(o2);
                }
            }
        });
    }

    /**
     * 得到編碼串可以分成的幾個部分<br/>
     * 可能有的幾種分法，及各部分的長度
     * 
     * @author fszhouzz@qq.com
     * @param namaja
     *            原編碼串
     * @param baseMbMap
     *            碼表
     * @param minCodeLen
     *            碼表中最短鍵長
     * @param maxCodeLen
     *            碼表中最長鍵長
     * @return List是分法的個數，裡面的數字位數表示可分成幾段，各位數字表示各段長度
     */
    public static List<Integer> getPartsLen(String namaja, Map<String, List<String>> baseMbMap, int minCodeLen,
            int maxCodeLen) {
        List<Integer> temp = new ArrayList<Integer>();
        for (Integer i = minCodeLen; i < maxCodeLen; i++) {
            if (i <= namaja.length()) {
                List<String> one = baseMbMap.get(namaja.substring(0, i));
                if (null == one) {
                    continue;
                }

                if (i == namaja.length()) {
                    temp.add(i);
                } else {
                    List<Integer> subLens = getPartsLen(namaja.substring(i), baseMbMap, minCodeLen, maxCodeLen);
                    if (null != subLens && !subLens.isEmpty()) {
                        for (Integer subInt : subLens) {
                            int tempI = i;
                            for (int ii = 1; ii <= subInt.toString().length(); ii++) {
                                tempI *= 10;
                            }
                            if (null != temp) {
                                temp.add(tempI + subInt);
                            }
                        }
                    }
                }
            }
        }
        return temp;
    }

    /**
     * 按可分成幾段及各段長度，得到拆分解析结果
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月23日下午2:53:14
     * @param roman
     * @param baseMbMap
     * @param lens
     * @return
     */
    public static List<String> getResByPartsLen(String roman, Map<String, List<String>> baseMbMap, List<Integer> lens) {
        List<String> res = new ArrayList<String>();
        if (null != lens && !lens.isEmpty()) {
            for (Integer len : lens) {
                List<String> parts = new ArrayList<String>();
                int start = 0;
                for (int index = 0; index < len.toString().length(); index++) {
                    int number = Integer.parseInt(len.toString().charAt(index) + "");
                    parts.add(roman.substring(start, start + number));

                    start += number;
                }

                List<String> res2 = new ArrayList<String>();
                for (String part : parts) {
                    List<String> partRes = baseMbMap.get(part);
                    if (res2.isEmpty()) {
                        res2.addAll(partRes);
                    } else {
                        List<String> res3 = new ArrayList<String>();
                        for (String old : res2) {
                            for (String news : partRes) {
                                res3.add(old + news);
                            }
                        }
                        res2 = res3;
                    }
                }
                res.addAll(res2);
            }
        }
        return res;
    }
}
