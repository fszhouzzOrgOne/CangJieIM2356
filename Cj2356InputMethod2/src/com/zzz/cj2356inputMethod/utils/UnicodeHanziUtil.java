package com.zzz.cj2356inputMethod.utils;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 統一碼漢字工具类<br/>
 * 參見http://yedict.com/zsts.htm<br/>
 * http://www.haomeili.net/code/unicode?wd=
 * 
 * @author fszhouzz@qq.com
 * @time 2017年12月11日上午9:37:37
 */
public class UnicodeHanziUtil {
    /** 基本區 */
    public static int[] baseRange = { 0x4E00, 0x9FA5 };
    /** 基本補充 */
    public static int[] base2Range = { 0x9FA6, 0x9FFF };
    /** 擴展A */
    public static int[] AextRange = { 0x3400, 0x4DBF };
    /** 擴展B */
    public static int[] BextRange = { 0x20000, 0x2A6DF };
    /** 擴展C */
    public static int[] CextRange = { 0x2A700, 0x2B734 };
    /** 擴展D */
    public static int[] DextRange = { 0x2B740, 0x2B81D };
    /** 擴展E */
    public static int[] EextRange = { 0x2B820, 0x2CEA1 };
    /** 擴展F */
    public static int[] FextRange = { 0x2CEB0, 0x2EBE0 };

    /** 兼容漢字 */
    public static int[] hanziCompt = { 0xF900, 0xFAFF };
    /** 兼容擴展 */
    public static int[] extCompt = { 0x2F800, 0x2FA1F };

    /** 諺文字母 */
    private static int[] hangulJamo = { 0x1100, 0x11FF };
    /** 標點補充 */
    private static int[] supplmtlPunctuation = { 0x2E00, 0x2E7F };
    /** 部首擴展 */
    private static int[] extPart = { 0x2E80, 0x2EFF };
    /** 康熙部首 */
    private static int[] kangxiPart = { 0x2F00, 0x2FDF };
    /** 漢字結構 */
    private static int[] hanziStruct = { 0x2FF0, 0x2FFF };
    /** 符號標點 */
    private static int[] symPunctuation = { 0x3000, 0x303F };
    /** 日文假名 */
    private static int[] japanKarina = { 0x3040, 0x30FF };
    /** 注音符號 */
    private static int[] zhuyin = { 0x3100, 0x312F };
    /** 諺文兼容字母 */
    private static int[] hangulCompatJomo = { 0x3130, 0x318F };
    /** 漢文標註 */
    private static int[] kanbun = { 0x3190, 0x319F };
    /** 注音擴展 */
    private static int[] extZhuyin = { 0x31A0, 0x31BF };
    /** 漢字筆畫 */
    private static int[] hanziStroke = { 0x31C0, 0x31EF };
    /** 片假名音標擴充 */
    private static int[] katakanaPhoneticExtensions = { 0x31F0, 0x31FF };
    /** 中日韓括號字母及月份 */
    private static int[] encloseLetterMonth = { 0x3200, 0x32FF };
    /** 中日韓兼容字符 */
    private static int[] cjkCompat = { 0x3300, 0x33FF };

    /** Private Use Area 私用區 */
    private static int[] privateUserArea = { 0xE000, 0xF8FF };

    private static Map<String, Object> nameRangeMap = new LinkedHashMap<String, Object>();
    /** 私用區鍵名 */
    private static String PRIVATEUSERAREA_KEYNAME = "私用區";
    /** 兼容漢字區鍵名 */
    private static String HANZICOMPT_KEYNAME = "兼容漢字";
    /** 兼容擴展鍵名 */
    private static String EXTCOMPT_KEYNAME = "兼容漢字擴展";

    static {
        nameRangeMap.put("漢字基本區", getStringSet(baseRange));
        nameRangeMap.put("漢字基本區補充", getStringSet(base2Range));
        nameRangeMap.put("漢字擴展A區", getStringSet(AextRange));
        nameRangeMap.put("漢字擴展B區", getStringSet(BextRange));
        nameRangeMap.put("漢字擴展C區", getStringSet(CextRange));
        nameRangeMap.put("漢字擴展D區", getStringSet(DextRange));
        nameRangeMap.put("漢字擴展E區", getStringSet(EextRange));
        nameRangeMap.put("漢字擴展F區", getStringSet(FextRange));

        nameRangeMap.put(HANZICOMPT_KEYNAME, getStringSet(hanziCompt));
        nameRangeMap.put(EXTCOMPT_KEYNAME, getStringSet(extCompt));

        nameRangeMap.put("諺文字母", getStringSet(hangulJamo));
        nameRangeMap.put("補充標點符號", getStringSet(supplmtlPunctuation));
        nameRangeMap.put("中日韓部首補充", getStringSet(extPart));
        nameRangeMap.put("康熙部首", getStringSet(kangxiPart));
        nameRangeMap.put("漢字結構描述字符", getStringSet(hanziStruct));
        nameRangeMap.put("中日韓符號和標點", getStringSet(symPunctuation));
        nameRangeMap.put("日文假名", getStringSet(japanKarina));
        nameRangeMap.put("注音符號", getStringSet(zhuyin));
        nameRangeMap.put("諺文兼容字母", getStringSet(hangulCompatJomo));
        nameRangeMap.put("漢文標註號", getStringSet(kanbun));
        nameRangeMap.put("注音符號擴充", getStringSet(extZhuyin));
        nameRangeMap.put("中日韓筆畫部件", getStringSet(hanziStroke));
        nameRangeMap.put("片假名音標擴充", getStringSet(katakanaPhoneticExtensions));
        nameRangeMap.put("中日韓括號字母及月份", getStringSet(encloseLetterMonth));
        nameRangeMap.put("中日韓兼容字符", getStringSet(cjkCompat));

        nameRangeMap.put(PRIVATEUSERAREA_KEYNAME, getStringSet(privateUserArea));
    }

    public static void main(String[] args) throws Exception {
//        String baseDir = "src" + File.separator + "java" + File.separator + "unicode" + File.separator;
//        // 印所有字符到文件
//        for (String name : nameRangeMap.keySet()) {
//            @SuppressWarnings("unchecked")
//            List<String> arr = new ArrayList<String>((Set<String>) nameRangeMap.get(name));
//            // IOUtils.writeFile(baseDir + name + ".txt", arr);
//        }
        
        System.out.println(getRangeNameByChar("龜"));
        
        System.out.println(isInPrivateUserArea(""));
        System.out.println(isInhanziCompt("兀"));
    }

    /**
     * 是否在私用區
     * 
     * @param charStr
     *            一個字符
     * @return
     */
    public static boolean isInPrivateUserArea(String charStr) {
        if (null == charStr || charStr.toCharArray().length > 2) {
            return false;
        }
        @SuppressWarnings("unchecked")
        Set<String> puaSet = (Set<String>) nameRangeMap.get(PRIVATEUSERAREA_KEYNAME);
        return puaSet.contains(charStr);
    }
    
    /**
     * 是否在兼容或兼容擴展區
     * 
     * @param charStr
     *            一個字符
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isInhanziCompt(String charStr) {
        if (null == charStr || charStr.toCharArray().length > 2) {
            return false;
        }
        Set<String> set = (Set<String>) nameRangeMap.get(HANZICOMPT_KEYNAME);
        set.addAll((Set<String>) nameRangeMap.get(EXTCOMPT_KEYNAME));
        return set.contains(charStr);
    }

    /**
     * 按字符，取得它在統一碼中所在區名字
     */
    public static String getRangeNameByChar(String charStr) {
        if (null == charStr || charStr.toCharArray().length > 2) {
            return "";
        }
        for (String name : nameRangeMap.keySet()) {
            @SuppressWarnings("unchecked")
            Set<String> range = (Set<String>) nameRangeMap.get(name);
            if (range.contains(charStr)) {
                return name;
            }
        }
        return "";
    }

    /** 按統一碼編號範圍，取到對应的字符集合 */
    public static Set<String> getStringSet(int[] range) {
        return getStringSet(range[0], range[1]);
    }

    /** 按統一碼編號起止，取到對应的字符集合 */
    public static Set<String> getStringSet(int start, int end) {
        Set<String> set = new LinkedHashSet<String>();
        for (int i = start; i <= end; i++) {
            String res = getStringByUnicode(i);
            if (!set.contains(res)) {
                set.add(res);
            }
        }
        return set;
    }

    /** 按統一碼編號，取到對应的字符 */
    public static String getStringByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
