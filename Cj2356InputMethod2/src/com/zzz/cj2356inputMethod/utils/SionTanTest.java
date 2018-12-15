package com.zzz.cj2356inputMethod.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 輸入法工具類<br/>
 * 參見 siontan.SionTanMbConvertTest
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年12月15日 上午9:56:44
 */
public class SionTanTest {

    private static final List<String> toneList = new ArrayList<String>();
    private static final Map<String, String> map = new HashMap<String, String>();

    public static void main(String[] args) {
        String input = "henqhanqqqscqqɪnqdvqqdanqqlənqqqddqqqyuenqqqqynqqqqqqqqqionqqqqqqqq";

        List<String> res = getResultList(input);
        System.out.println(res);

        System.out.println(getKeyNameMap());
    }

    public static Map<String, Object> getKeyNameMap() {
        return new HashMap<String, Object>(map);
    }

    /**
     * 按輸入法，得到音標候選列表
     * 
     * @author fszhouzz@qq.com
     * @time 2018年12月15日 上午9:55:41
     * @param input
     * @return
     */
    public static List<String> getResultList(String input) {
        List<String> res = getPreliminaryResult(input);
        res = resolveNasalSounds(res);
        return res;
    }

    /** 鼻化: 曾版沒有on、ɪn，只en鼻化；張拼沒有ɪn，in、yn、ən不鼻化； */
    private static List<String> resolveNasalSounds(List<String> input) {
        if (null == input || input.isEmpty()) {
            return null;
        }
        Set<String> res = new LinkedHashSet<String>();
        for (String one : input) {
            if (!res.contains(one)) {
                res.add(one);
            }
            String tmp = one.replaceAll("en", "ẽ");
            tmp = tmp.replaceAll("an", "ã");
            tmp = tmp.replaceAll("ɔn", "ɔ̃");
            tmp = tmp.replaceAll("on", "õ");
            if (!res.contains(tmp)) {
                res.add(tmp);
            }
            tmp = tmp.replaceAll("in", "ĩ");
            tmp = tmp.replaceAll("yn", "ỹ");
            tmp = tmp.replaceAll("ən", "ə̃");
            tmp = tmp.replaceAll("ɪn", "ɪ̃");
            if (!res.contains(tmp)) {
                res.add(tmp);
            }
        }
        return new ArrayList<String>(res);
    }

    /** 初步解析結果 */
    private static List<String> getPreliminaryResult(String input) {
        if (null == input) {
            return null;
        }
        List<String> resList = new ArrayList<String>();
        while (input.contains("q")) {
            input = input.replaceAll("qqqqqq", toneList.get(5));
            input = input.replaceAll("qqqqq", toneList.get(4));
            input = input.replaceAll("qqqq", toneList.get(3));
            input = input.replaceAll("qqq", toneList.get(2));
            input = input.replaceAll("qq", toneList.get(1));
            input = input.replaceAll("q", toneList.get(0));
        }
        String res = "";
        for (int i = 0; i < input.length(); i++) {
            String one = input.substring(i, i + 1);
            if (map.keySet().contains(one)) {
                res += map.get(one);
            } else {
                res += one;
            }
        }
        resList.add(res);
        return resList;
    }

    static {
        toneList.add("³³");
        toneList.add("¹²");
        toneList.add("⁴²");
        toneList.add("⁴⁵");
        toneList.add("²¹");
        toneList.add("²⁴");
        List<String> lines = new ArrayList<String>();
        lines.add("w ɯ");
        lines.add("e e");
        lines.add("r ə");
        lines.add("t t");
        lines.add("y y");
        lines.add("u u");
        lines.add("i i");
        lines.add("o o");
        lines.add("p p");
        lines.add("a a");
        lines.add("s s");
        lines.add("d ʂ");
        lines.add("f ɸ");
        lines.add("g æ");
        lines.add("h h");
        lines.add("j ʰ");
        lines.add("k k");
        lines.add("l ɔ");
        lines.add("z ɒ");
        lines.add("x ɕ");
        lines.add("c ɿ");
        lines.add("b ŋ");
        lines.add("v ʅ");
        lines.add("n n");
        lines.add("m m");
        for (String line : lines) {
            String[] parts = line.split(" +");
            map.put(parts[0], parts[1]);
        }
    }

}
