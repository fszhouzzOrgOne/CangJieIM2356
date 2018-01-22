package com.zzz.cj2356inputMethod.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zzz.cj2356inputMethod.mb.IOUtils;

import android.content.Context;

/**
 * 羅馬字轉韓文方法
 * 
 * @author fszhouzz@qq.com
 * @time 2018年1月22日下午6:04:52
 */
public class Namaja2HangeulTest {

    private static boolean inited = false;
    private static Context context;

    private static Map<String, List<String>> baseMbMap = null;
    private static int maxCodeLen = 0; // 7
    private static int minCodeLen = 3; // 1

    public static void init(Context con) {
        if (inited) {
            return;
        }
        try {
            context = con;
            InputStream is = context.getResources().getAssets().open("mb" + File.separator + "korea-12000.txt");
            // InputStream is = new
            // FileInputStream(Cj00AllInOneTest.mbkorea10000);
            List<String> res = getBaseMbByIS(is);
            baseMbMap = new HashMap<String, List<String>>();
            for (String line : res) {
                if (line.contains(" ")) {
                    String[] parts = line.split(" ");
                    List<String> chars = baseMbMap.get(parts[0]);
                    if (null == chars) {
                        chars = new ArrayList<String>();
                    }
                    chars.add(parts[1]);
                    baseMbMap.put(parts[0], chars);

                    if (parts[0].length() > maxCodeLen) {
                        maxCodeLen = parts[0].length();
                    }
                    if (parts[0].length() < minCodeLen) {
                        minCodeLen = parts[0].length();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 取基本碼表
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日 下午8:02:57
     * @param is
     * @return
     */
    private static List<String> getBaseMbByIS(InputStream is) {
        String patn = "[가-힣]{1}";
        List<String> res = IOUtils.readLines(is);
        if (null != res && !res.isEmpty()) {
            List<String> res2 = IOUtils.readLines(is);
            for (String line : res) {
                if (line.contains(" ")) {
                    String[] parts = line.split(" ");
                    if (parts[1].matches(patn)) {
                        res2.add(line);
                    }
                }
            }
            res = res2;
        }
        return res;
    }

    /**
     * 羅馬字轉韓文列表
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日 下午8:18:05
     * @param namaja
     *            羅馬字
     * @return 韓文列表
     */
    public static List<String> getHangeulFromNamaja(String namaja) {
        if (null == namaja || namaja.trim().length() == 0) {
            return null;
        }
        namaja = namaja.trim().toLowerCase();

        if (namaja.length() == 1) {
            return baseMbMap.get(namaja);
        }
        List<String> res = new ArrayList<String>();
        List<Integer> lens = getPartsLen(namaja);
        if (null != lens && !lens.isEmpty()) {
            for (Integer len : lens) {
                List<String> parts = new ArrayList<String>();
                int start = 0;
                for (int index = 0; index < len.toString().length(); index++) {
                    int number = Integer.parseInt(len.toString().charAt(index) + "");
                    parts.add(namaja.substring(start, start + number));

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

        // 排序
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
        return res;
    }

    /**
     * 看看韓文可以分成幾段
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日 下午9:09:20
     * @param namaja
     * @return
     */
    private static List<Integer> getPartsLen(String namaja) {
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
                    List<Integer> subLens = getPartsLen(namaja.substring(i));
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
}
