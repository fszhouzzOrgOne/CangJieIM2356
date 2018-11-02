package com.zzz.cj2356inputMethod.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 盲文
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年11月2日 下午7:43:04
 */
public class BrailleTest {

    private static Map<String, Integer> keyMap1 = new HashMap<String, Integer>();
    private static Map<String, String> keyMap2 = new HashMap<String, String>();

    private static List<String> lines = new ArrayList<String>();
    private static List<String> linesEn = new ArrayList<String>();
    private static Map<String, String> codeCharMap = new HashMap<String, String>();
    private static Map<String, List<String>> codeCharMapEn = new HashMap<String, List<String>>();

    public static void main(String[] args) {
        String input = "wqwep";
        System.out.println(input);
        List<String> cands = getCandidatesByInput(input);
        System.out.println(cands);
    }

    public static List<String> getCandidatesByInput(String input) {
        List<String> resList = new ArrayList<String>();
        List<String> parts = resolveInputParts(input);
        String fullCode = "";
        String reses = "";
        for (String part : parts) {
            String one = part.charAt(0) + "";
            String code = null;
            if (keyMap1.keySet().contains(one)) {
                code = resolveNumTrueCode(part);
            } else if (keyMap2.keySet().contains(one)) {
                code = resolveImageTrueCode(part);
            }
            fullCode += code;
            reses += codeCharMap.get(code);
        }
        resList.add(reses);
        List<String> resEn = codeCharMapEn.get(fullCode);
        if (null != resEn) {
            resList.addAll(resEn);
        }
        return resList;
    }

    public static String resolveImageTrueCode(String part) {
        String code1 = keyMap2.get(part.charAt(0) + "");
        if (code1.length() == 8) {
            return code1;
        }
        String code2 = keyMap2.get(part.charAt(1) + "");
        List<String> codes = new ArrayList<String>(
                Arrays.asList("0", "0", "0", "0", "0", "0", "0", "0"));
        codes.set(0, code1.charAt(0) + "");
        codes.set(1, code1.charAt(2) + "");
        codes.set(2, code2.charAt(0) + "");
        codes.set(3, code1.charAt(1) + "");
        codes.set(4, code1.charAt(3) + "");
        codes.set(5, code2.charAt(1) + "");
        codes.set(6, code2.charAt(2) + "");
        codes.set(7, code2.charAt(3) + "");
        String codex = "";
        for (String one : codes) {
            codex += one;
        }
        return codex;
    }

    public static String resolveNumTrueCode(String part) {
        List<String> codes = new ArrayList<String>(
                Arrays.asList("0", "0", "0", "0", "0", "0", "0", "0"));
        for (int i = 0; i < part.length(); i++) {
            int one = keyMap1.get(part.charAt(i) + "");
            codes.set(one - 1, "1");
        }
        String codex = "";
        for (String one : codes) {
            codex += one;
        }
        return codex;
    }

    public static List<String> resolveInputParts(String input) {
        boolean isNum = false;
        List<String> parts = new ArrayList<String>();
        String part = "";
        for (int i = 0; i < input.length(); i++) {
            String one = input.charAt(i) + "";
            if (keyMap1.keySet().contains(one)) {
                if (!isNum && part.length() > 0) {
                    parts.add(part + (part.length() > 1 ? "" : "a"));
                    part = "";
                }
                isNum = true;
                int oneInt = keyMap1.get(one);
                if ("q".equals(one)) {
                    if (part.length() > 0) {
                        parts.add(part);
                        part = "";
                    }
                } else {
                    if (part.length() > 0) {
                        String two = part.charAt(part.length() - 1) + "";
                        int twoInt = keyMap1.get(two);
                        if (twoInt >= oneInt) {
                            parts.add(part);
                            part = one;
                        } else {
                            part += one;
                        }
                    } else {
                        part = one;
                    }
                }
            } else if (keyMap2.keySet().contains(one)) {
                if (isNum && part.length() > 0) {
                    parts.add(part);
                    part = "";
                }
                isNum = false;
                if ("p".equals(one)) {
                    if (part.length() > 0) {
                        parts.add(part + (part.length() > 1 ? "" : "a"));
                        part = "";
                    }
                    parts.add("p");
                } else {
                    if (part.length() > 0) {
                        parts.add(part + one);
                        part = "";
                    } else if (i == input.length() - 1) {
                        parts.add(one + "a");
                        part = "";
                    } else {
                        part = one;
                    }
                }
            }
        }
        if (part.length() > 0) {
            parts.add(part);
            part = "";
        }
        return parts;
    }

//    public static List<String> getBraillePatterns() {
//        int start = 0x2800;
//        int end = 0x28FF;
//        List<String> lines = new ArrayList<String>();
//        for (int i = start; i <= end; i++) {
//            String str = UnicodeConvertUtil.getStringByUnicode(i);
//            String name = Character.getName(i);
//            String code = name.contains("-")
//                    ? name.substring(name.indexOf("-") + 1) : "00000000";
//            String line = toFullCode(code) + " " + str;
//            lines.add(line);
//        }
//        return lines;
//    }

    public static String toFullCode(String code) {
        int len = 8;
        if (code.length() == len) {
            return code;
        }
        String res = "";
        for (int i = 0; i < code.length(); i++) {
            String one = code.charAt(i) + "";
            int oneInt = Integer.parseInt(one);
            if ((i + 1) != oneInt) {
                int start = (i == 0) ? 1
                        : Integer.parseInt(code.charAt(i - 1) + "") + 1;
                int end = oneInt - 1;
                for (int j = start; j <= end; j++) {
                    res += "0";
                }
            }
            res += one;
        }
        while (res.length() < len) {
            res += "0";
        }
        return res;
    }

    static {
        keyMap1.put("q", 0);
        keyMap1.put("w", 1);
        keyMap1.put("e", 2);
        keyMap1.put("r", 3);
        keyMap1.put("t", 4);
        keyMap1.put("y", 5);
        keyMap1.put("u", 6);
        keyMap1.put("i", 7);
        keyMap1.put("o", 8);

        keyMap2.put("p", "00000000");
        keyMap2.put("a", "0000");
        keyMap2.put("s", "0010");
        keyMap2.put("d", "0001");
        keyMap2.put("f", "0011");
        keyMap2.put("g", "1000");
        keyMap2.put("h", "1010");
        keyMap2.put("j", "1001");
        keyMap2.put("k", "1011");
        keyMap2.put("l", "0100");
        keyMap2.put("z", "0110");
        keyMap2.put("x", "0101");
        keyMap2.put("c", "0111");
        keyMap2.put("v", "1100");
        keyMap2.put("b", "1110");
        keyMap2.put("n", "1101");
        keyMap2.put("m", "1111");

        lines.add("00000000 ⠀");
        lines.add("10000000 ⠁");
        lines.add("02000000 ⠂");
        lines.add("12000000 ⠃");
        lines.add("00300000 ⠄");
        lines.add("10300000 ⠅");
        lines.add("02300000 ⠆");
        lines.add("12300000 ⠇");
        lines.add("00040000 ⠈");
        lines.add("10040000 ⠉");
        lines.add("02040000 ⠊");
        lines.add("12040000 ⠋");
        lines.add("00340000 ⠌");
        lines.add("10340000 ⠍");
        lines.add("02340000 ⠎");
        lines.add("12340000 ⠏");
        lines.add("00005000 ⠐");
        lines.add("10005000 ⠑");
        lines.add("02005000 ⠒");
        lines.add("12005000 ⠓");
        lines.add("00305000 ⠔");
        lines.add("10305000 ⠕");
        lines.add("02305000 ⠖");
        lines.add("12305000 ⠗");
        lines.add("00045000 ⠘");
        lines.add("10045000 ⠙");
        lines.add("02045000 ⠚");
        lines.add("12045000 ⠛");
        lines.add("00345000 ⠜");
        lines.add("10345000 ⠝");
        lines.add("02345000 ⠞");
        lines.add("12345000 ⠟");
        lines.add("00000600 ⠠");
        lines.add("10000600 ⠡");
        lines.add("02000600 ⠢");
        lines.add("12000600 ⠣");
        lines.add("00300600 ⠤");
        lines.add("10300600 ⠥");
        lines.add("02300600 ⠦");
        lines.add("12300600 ⠧");
        lines.add("00040600 ⠨");
        lines.add("10040600 ⠩");
        lines.add("02040600 ⠪");
        lines.add("12040600 ⠫");
        lines.add("00340600 ⠬");
        lines.add("10340600 ⠭");
        lines.add("02340600 ⠮");
        lines.add("12340600 ⠯");
        lines.add("00005600 ⠰");
        lines.add("10005600 ⠱");
        lines.add("02005600 ⠲");
        lines.add("12005600 ⠳");
        lines.add("00305600 ⠴");
        lines.add("10305600 ⠵");
        lines.add("02305600 ⠶");
        lines.add("12305600 ⠷");
        lines.add("00045600 ⠸");
        lines.add("10045600 ⠹");
        lines.add("02045600 ⠺");
        lines.add("12045600 ⠻");
        lines.add("00345600 ⠼");
        lines.add("10345600 ⠽");
        lines.add("02345600 ⠾");
        lines.add("12345600 ⠿");
        lines.add("00000070 ⡀");
        lines.add("10000070 ⡁");
        lines.add("02000070 ⡂");
        lines.add("12000070 ⡃");
        lines.add("00300070 ⡄");
        lines.add("10300070 ⡅");
        lines.add("02300070 ⡆");
        lines.add("12300070 ⡇");
        lines.add("00040070 ⡈");
        lines.add("10040070 ⡉");
        lines.add("02040070 ⡊");
        lines.add("12040070 ⡋");
        lines.add("00340070 ⡌");
        lines.add("10340070 ⡍");
        lines.add("02340070 ⡎");
        lines.add("12340070 ⡏");
        lines.add("00005070 ⡐");
        lines.add("10005070 ⡑");
        lines.add("02005070 ⡒");
        lines.add("12005070 ⡓");
        lines.add("00305070 ⡔");
        lines.add("10305070 ⡕");
        lines.add("02305070 ⡖");
        lines.add("12305070 ⡗");
        lines.add("00045070 ⡘");
        lines.add("10045070 ⡙");
        lines.add("02045070 ⡚");
        lines.add("12045070 ⡛");
        lines.add("00345070 ⡜");
        lines.add("10345070 ⡝");
        lines.add("02345070 ⡞");
        lines.add("12345070 ⡟");
        lines.add("00000670 ⡠");
        lines.add("10000670 ⡡");
        lines.add("02000670 ⡢");
        lines.add("12000670 ⡣");
        lines.add("00300670 ⡤");
        lines.add("10300670 ⡥");
        lines.add("02300670 ⡦");
        lines.add("12300670 ⡧");
        lines.add("00040670 ⡨");
        lines.add("10040670 ⡩");
        lines.add("02040670 ⡪");
        lines.add("12040670 ⡫");
        lines.add("00340670 ⡬");
        lines.add("10340670 ⡭");
        lines.add("02340670 ⡮");
        lines.add("12340670 ⡯");
        lines.add("00005670 ⡰");
        lines.add("10005670 ⡱");
        lines.add("02005670 ⡲");
        lines.add("12005670 ⡳");
        lines.add("00305670 ⡴");
        lines.add("10305670 ⡵");
        lines.add("02305670 ⡶");
        lines.add("12305670 ⡷");
        lines.add("00045670 ⡸");
        lines.add("10045670 ⡹");
        lines.add("02045670 ⡺");
        lines.add("12045670 ⡻");
        lines.add("00345670 ⡼");
        lines.add("10345670 ⡽");
        lines.add("02345670 ⡾");
        lines.add("12345670 ⡿");
        lines.add("00000008 ⢀");
        lines.add("10000008 ⢁");
        lines.add("02000008 ⢂");
        lines.add("12000008 ⢃");
        lines.add("00300008 ⢄");
        lines.add("10300008 ⢅");
        lines.add("02300008 ⢆");
        lines.add("12300008 ⢇");
        lines.add("00040008 ⢈");
        lines.add("10040008 ⢉");
        lines.add("02040008 ⢊");
        lines.add("12040008 ⢋");
        lines.add("00340008 ⢌");
        lines.add("10340008 ⢍");
        lines.add("02340008 ⢎");
        lines.add("12340008 ⢏");
        lines.add("00005008 ⢐");
        lines.add("10005008 ⢑");
        lines.add("02005008 ⢒");
        lines.add("12005008 ⢓");
        lines.add("00305008 ⢔");
        lines.add("10305008 ⢕");
        lines.add("02305008 ⢖");
        lines.add("12305008 ⢗");
        lines.add("00045008 ⢘");
        lines.add("10045008 ⢙");
        lines.add("02045008 ⢚");
        lines.add("12045008 ⢛");
        lines.add("00345008 ⢜");
        lines.add("10345008 ⢝");
        lines.add("02345008 ⢞");
        lines.add("12345008 ⢟");
        lines.add("00000608 ⢠");
        lines.add("10000608 ⢡");
        lines.add("02000608 ⢢");
        lines.add("12000608 ⢣");
        lines.add("00300608 ⢤");
        lines.add("10300608 ⢥");
        lines.add("02300608 ⢦");
        lines.add("12300608 ⢧");
        lines.add("00040608 ⢨");
        lines.add("10040608 ⢩");
        lines.add("02040608 ⢪");
        lines.add("12040608 ⢫");
        lines.add("00340608 ⢬");
        lines.add("10340608 ⢭");
        lines.add("02340608 ⢮");
        lines.add("12340608 ⢯");
        lines.add("00005608 ⢰");
        lines.add("10005608 ⢱");
        lines.add("02005608 ⢲");
        lines.add("12005608 ⢳");
        lines.add("00305608 ⢴");
        lines.add("10305608 ⢵");
        lines.add("02305608 ⢶");
        lines.add("12305608 ⢷");
        lines.add("00045608 ⢸");
        lines.add("10045608 ⢹");
        lines.add("02045608 ⢺");
        lines.add("12045608 ⢻");
        lines.add("00345608 ⢼");
        lines.add("10345608 ⢽");
        lines.add("02345608 ⢾");
        lines.add("12345608 ⢿");
        lines.add("00000078 ⣀");
        lines.add("10000078 ⣁");
        lines.add("02000078 ⣂");
        lines.add("12000078 ⣃");
        lines.add("00300078 ⣄");
        lines.add("10300078 ⣅");
        lines.add("02300078 ⣆");
        lines.add("12300078 ⣇");
        lines.add("00040078 ⣈");
        lines.add("10040078 ⣉");
        lines.add("02040078 ⣊");
        lines.add("12040078 ⣋");
        lines.add("00340078 ⣌");
        lines.add("10340078 ⣍");
        lines.add("02340078 ⣎");
        lines.add("12340078 ⣏");
        lines.add("00005078 ⣐");
        lines.add("10005078 ⣑");
        lines.add("02005078 ⣒");
        lines.add("12005078 ⣓");
        lines.add("00305078 ⣔");
        lines.add("10305078 ⣕");
        lines.add("02305078 ⣖");
        lines.add("12305078 ⣗");
        lines.add("00045078 ⣘");
        lines.add("10045078 ⣙");
        lines.add("02045078 ⣚");
        lines.add("12045078 ⣛");
        lines.add("00345078 ⣜");
        lines.add("10345078 ⣝");
        lines.add("02345078 ⣞");
        lines.add("12345078 ⣟");
        lines.add("00000678 ⣠");
        lines.add("10000678 ⣡");
        lines.add("02000678 ⣢");
        lines.add("12000678 ⣣");
        lines.add("00300678 ⣤");
        lines.add("10300678 ⣥");
        lines.add("02300678 ⣦");
        lines.add("12300678 ⣧");
        lines.add("00040678 ⣨");
        lines.add("10040678 ⣩");
        lines.add("02040678 ⣪");
        lines.add("12040678 ⣫");
        lines.add("00340678 ⣬");
        lines.add("10340678 ⣭");
        lines.add("02340678 ⣮");
        lines.add("12340678 ⣯");
        lines.add("00005678 ⣰");
        lines.add("10005678 ⣱");
        lines.add("02005678 ⣲");
        lines.add("12005678 ⣳");
        lines.add("00305678 ⣴");
        lines.add("10305678 ⣵");
        lines.add("02305678 ⣶");
        lines.add("12305678 ⣷");
        lines.add("00045678 ⣸");
        lines.add("10045678 ⣹");
        lines.add("02045678 ⣺");
        lines.add("12045678 ⣻");
        lines.add("00345678 ⣼");
        lines.add("10345678 ⣽");
        lines.add("02345678 ⣾");
        lines.add("12345678 ⣿");

        linesEn.add("10000000 a");
        linesEn.add("12000000 b");
        linesEn.add("10040000 c");
        linesEn.add("10045000 d");
        linesEn.add("10005000 e");
        linesEn.add("12040000 f");
        linesEn.add("12045000 g");
        linesEn.add("12005000 h");
        linesEn.add("02040000 i");
        linesEn.add("02045000 j");
        linesEn.add("10300000 k");
        linesEn.add("12300000 l");
        linesEn.add("10340000 m");
        linesEn.add("10345000 n");
        linesEn.add("10305000 o");
        linesEn.add("12340000 p");
        linesEn.add("12345000 q");
        linesEn.add("12305000 r");
        linesEn.add("02340000 s");
        linesEn.add("02345000 t");
        linesEn.add("10300600 u");
        linesEn.add("12300600 v");
        linesEn.add("02045600 w");
        linesEn.add("10340600 x");
        linesEn.add("10345600 y");
        linesEn.add("10305600 z");
        linesEn.add("12340600 and");
        linesEn.add("12345600 for");
        linesEn.add("12305600 of");
        linesEn.add("02340600 the");
        linesEn.add("02345600 with");
        linesEn.add("10000600 ch");
        linesEn.add("12000600 gh");
        linesEn.add("10040600 sh");
        linesEn.add("10045600 th");
        linesEn.add("10005600 wh");
        linesEn.add("12040600 ed");
        linesEn.add("12045600 er");
        linesEn.add("12005600 ou");
        linesEn.add("02040600 ow");
        linesEn.add("02000000 ea");
        linesEn.add("02300000 bb");
        linesEn.add("02005000 cc");
        linesEn.add("02000600 en");
        linesEn.add("02305000 ff");
        linesEn.add("02305600 gg");
        linesEn.add("00305000 in");
        linesEn.add("00040000 (accent)");
        linesEn.add("00005000 (i-abbreviation)");
        linesEn.add("00045000 (i-abbreviation)");
        linesEn.add("00045600 (i-abbreviation)");
        linesEn.add("00040600 (f-abbreviation)");
        linesEn.add("00005600 (f-abbreviation)");
        linesEn.add("00000600 (f-abbreviation)");
        linesEn.add("00340000 st");
        linesEn.add("00345000 ar");
        linesEn.add("00340600 ing");
        linesEn.add("00345600 (number)");
        linesEn.add("00040600 .");
        linesEn.add("00040600 (decimal)");
        linesEn.add("00040600 (emphasis)");
        linesEn.add("00000600 (capital)");
        linesEn.add("00005600 (letter)");
        linesEn.add("10000000 1");
        linesEn.add("12000000 2");
        linesEn.add("10040000 3");
        linesEn.add("10045000 4");
        linesEn.add("10005000 5");
        linesEn.add("12040000 6");
        linesEn.add("12045000 7");
        linesEn.add("12005000 8");
        linesEn.add("02040000 9");
        linesEn.add("02045000 0");
        linesEn.add("02000000 ,");
        linesEn.add("02300000 ;");
        linesEn.add("02005000 :");
        linesEn.add("02005600 .");
        linesEn.add("02305000 !");
        linesEn.add("02305600 (");
        linesEn.add("02305600 )");
        linesEn.add("02300600 ?");
        linesEn.add("02300600 “");
        linesEn.add("00305600 ”");
        linesEn.add("00305600 \"");
        linesEn.add("0030500000305000 *");
        linesEn.add("0030500000305000 †");
        linesEn.add("0030500000305000 ‡");
        linesEn.add("0030500000305000 ¶");
        linesEn.add("00340000 /");
        linesEn.add("0000500002000000 〃");
        linesEn.add("0000060002305600 [");
        linesEn.add("0230560000300000 ]");
        linesEn.add("0000060002300600 ‘");
        linesEn.add("0030560000300000 ’");
        linesEn.add("00300000 '");
        linesEn.add("00300600 -");
        linesEn.add("0030060000300600 –");
        linesEn.add("003000000030000000300000 ...");
        linesEn.add("003006000030060000300600 —");
        linesEn.add("00000000 (space)");
        linesEn.add("00300600 (stop)");
        linesEn.add("0000060000300000 (termination)");
        linesEn.add("02000000 (non-latin)");
        linesEn.add("10000000 a");
        linesEn.add("12000000 but");
        linesEn.add("10040000 can");
        linesEn.add("10045000 do");
        linesEn.add("10005000 every");
        linesEn.add("12040000 from");
        linesEn.add("12040000 self");
        linesEn.add("12045000 go");
        linesEn.add("12005000 have");
        linesEn.add("02040000 I");
        linesEn.add("02045000 just");
        linesEn.add("10300000 knowledge");
        linesEn.add("12300000 like");
        linesEn.add("10340000 more");
        linesEn.add("10345000 not");
        linesEn.add("12340000 people");
        linesEn.add("12345000 quite");
        linesEn.add("12305000 rather");
        linesEn.add("02340000 so");
        linesEn.add("02345000 that");
        linesEn.add("10300600 us");
        linesEn.add("12300600 very");
        linesEn.add("02045600 will");
        linesEn.add("10340600 it");
        linesEn.add("10345600 you");
        linesEn.add("10305600 as");
        linesEn.add("10000600 child");
        linesEn.add("10040600 shall");
        linesEn.add("10045600 this");
        linesEn.add("10005600 which");
        linesEn.add("12005600 out");
        linesEn.add("02300000 be");
        linesEn.add("02005000 con");
        linesEn.add("02005600 dis");
        linesEn.add("02000600 enough");
        linesEn.add("02305600 were");
        linesEn.add("02300600 his");
        linesEn.add("00305600 was");
        linesEn.add("00340000 still");

        for (String line : lines) {
            String[] parts = line.split(" +");
            codeCharMap.put(parts[0].replaceAll("[12345678]", "1"), parts[1]);
        }
        for (String line : linesEn) {
            String[] parts = line.split(" +");
            String key = parts[0].replaceAll("[12345678]", "1");
            List<String> val = codeCharMapEn.get(key);
            if (null == val) {
                val = new ArrayList<String>();
            }
            if (!val.contains(parts[1])) {
                val.add(parts[1]);
            }
            codeCharMapEn.put(key, val);
        }
    }
}
