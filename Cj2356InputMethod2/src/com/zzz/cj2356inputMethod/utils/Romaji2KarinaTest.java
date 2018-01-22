package com.zzz.cj2356inputMethod.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 日語羅馬字轉換成假名
 * 
 * @author fszhouzz@qq.com
 * @time 2018年1月22日上午10:59:55
 */
public class Romaji2KarinaTest {

    public static void main(String[] args) {
        System.out.println(getKarinaFromRomaji("raamen"));
    }
    
    /** 片假名的長音 */
    public static String changyin = "ー";

    /** 假名和羅馬字的映射，排除了變化形式，所以能一對二 */
    public static Map<String, String> cleanKarinaRomaMap = new HashMap<String, String>();

    // 基本的假名
    public static String normalKarina = null;
    public static String normalKarinaPtn = null;

    static {
        cleanKarinaRomaMap.put("あ", "a");
        cleanKarinaRomaMap.put("ア", "a");
        cleanKarinaRomaMap.put("い", "i");
        cleanKarinaRomaMap.put("イ", "i");
        cleanKarinaRomaMap.put("う", "u");
        cleanKarinaRomaMap.put("ウ", "u");
        cleanKarinaRomaMap.put("え", "e");
        cleanKarinaRomaMap.put("エ", "e");
        cleanKarinaRomaMap.put("お", "o");
        cleanKarinaRomaMap.put("オ", "o");
        cleanKarinaRomaMap.put("か", "ka");
        cleanKarinaRomaMap.put("カ", "ka");
        cleanKarinaRomaMap.put("が", "ga");
        cleanKarinaRomaMap.put("ガ", "ga");
        cleanKarinaRomaMap.put("き", "ki");
        cleanKarinaRomaMap.put("キ", "ki");
        cleanKarinaRomaMap.put("ぎ", "gi");
        cleanKarinaRomaMap.put("ギ", "gi");
        cleanKarinaRomaMap.put("く", "ku");
        cleanKarinaRomaMap.put("ク", "ku");
        cleanKarinaRomaMap.put("ぐ", "gu");
        cleanKarinaRomaMap.put("グ", "gu");
        cleanKarinaRomaMap.put("け", "ke");
        cleanKarinaRomaMap.put("ケ", "ke");
        cleanKarinaRomaMap.put("げ", "ge");
        cleanKarinaRomaMap.put("ゲ", "ge");
        cleanKarinaRomaMap.put("こ", "ko");
        cleanKarinaRomaMap.put("コ", "ko");
        cleanKarinaRomaMap.put("ご", "go");
        cleanKarinaRomaMap.put("ゴ", "go");
        cleanKarinaRomaMap.put("さ", "sa");
        cleanKarinaRomaMap.put("サ", "sa");
        cleanKarinaRomaMap.put("ざ", "za");
        cleanKarinaRomaMap.put("ザ", "za");
        cleanKarinaRomaMap.put("し", "si");
        cleanKarinaRomaMap.put("シ", "si");
        cleanKarinaRomaMap.put("じ", "zi");
        cleanKarinaRomaMap.put("ジ", "zi");
        cleanKarinaRomaMap.put("す", "su");
        cleanKarinaRomaMap.put("ス", "su");
        cleanKarinaRomaMap.put("ず", "zu");
        cleanKarinaRomaMap.put("ズ", "zu");
        cleanKarinaRomaMap.put("せ", "se");
        cleanKarinaRomaMap.put("セ", "se");
        cleanKarinaRomaMap.put("ぜ", "ze");
        cleanKarinaRomaMap.put("ゼ", "ze");
        cleanKarinaRomaMap.put("そ", "so");
        cleanKarinaRomaMap.put("ソ", "so");
        cleanKarinaRomaMap.put("ぞ", "zo");
        cleanKarinaRomaMap.put("ゾ", "zo");
        cleanKarinaRomaMap.put("た", "ta");
        cleanKarinaRomaMap.put("タ", "ta");
        cleanKarinaRomaMap.put("だ", "da");
        cleanKarinaRomaMap.put("ダ", "da");
        cleanKarinaRomaMap.put("ち", "ti");
        cleanKarinaRomaMap.put("チ", "ti");
        cleanKarinaRomaMap.put("ぢ", "di");
        cleanKarinaRomaMap.put("ヂ", "di");
        cleanKarinaRomaMap.put("つ", "tu");
        cleanKarinaRomaMap.put("ツ", "tu");
        cleanKarinaRomaMap.put("づ", "du");
        cleanKarinaRomaMap.put("ヅ", "du");
        cleanKarinaRomaMap.put("て", "te");
        cleanKarinaRomaMap.put("テ", "te");
        cleanKarinaRomaMap.put("で", "de");
        cleanKarinaRomaMap.put("デ", "de");
        cleanKarinaRomaMap.put("と", "to");
        cleanKarinaRomaMap.put("ト", "to");
        cleanKarinaRomaMap.put("ど", "do");
        cleanKarinaRomaMap.put("ド", "do");
        cleanKarinaRomaMap.put("な", "na");
        cleanKarinaRomaMap.put("ナ", "na");
        cleanKarinaRomaMap.put("に", "ni");
        cleanKarinaRomaMap.put("ニ", "ni");
        cleanKarinaRomaMap.put("ぬ", "nu");
        cleanKarinaRomaMap.put("ヌ", "nu");
        cleanKarinaRomaMap.put("ね", "ne");
        cleanKarinaRomaMap.put("ネ", "ne");
        cleanKarinaRomaMap.put("の", "no");
        cleanKarinaRomaMap.put("ノ", "no");
        cleanKarinaRomaMap.put("は", "ha");
        cleanKarinaRomaMap.put("ハ", "ha");
        cleanKarinaRomaMap.put("ば", "ba");
        cleanKarinaRomaMap.put("バ", "ba");
        cleanKarinaRomaMap.put("ぱ", "pa");
        cleanKarinaRomaMap.put("パ", "pa");
        cleanKarinaRomaMap.put("ひ", "hi");
        cleanKarinaRomaMap.put("ヒ", "hi");
        cleanKarinaRomaMap.put("び", "bi");
        cleanKarinaRomaMap.put("ビ", "bi");
        cleanKarinaRomaMap.put("ぴ", "pi");
        cleanKarinaRomaMap.put("ピ", "pi");
        cleanKarinaRomaMap.put("ふ", "hu");
        cleanKarinaRomaMap.put("フ", "hu");
        cleanKarinaRomaMap.put("ぶ", "bu");
        cleanKarinaRomaMap.put("ブ", "bu");
        cleanKarinaRomaMap.put("ぷ", "pu");
        cleanKarinaRomaMap.put("プ", "pu");
        cleanKarinaRomaMap.put("へ", "he");
        cleanKarinaRomaMap.put("ヘ", "he");
        cleanKarinaRomaMap.put("べ", "be");
        cleanKarinaRomaMap.put("ベ", "be");
        cleanKarinaRomaMap.put("ぺ", "pe");
        cleanKarinaRomaMap.put("ペ", "pe");
        cleanKarinaRomaMap.put("ほ", "ho");
        cleanKarinaRomaMap.put("ホ", "ho");
        cleanKarinaRomaMap.put("ぼ", "bo");
        cleanKarinaRomaMap.put("ボ", "bo");
        cleanKarinaRomaMap.put("ぽ", "po");
        cleanKarinaRomaMap.put("ポ", "po");
        cleanKarinaRomaMap.put("ま", "ma");
        cleanKarinaRomaMap.put("マ", "ma");
        cleanKarinaRomaMap.put("み", "mi");
        cleanKarinaRomaMap.put("ミ", "mi");
        cleanKarinaRomaMap.put("む", "mu");
        cleanKarinaRomaMap.put("ム", "mu");
        cleanKarinaRomaMap.put("め", "me");
        cleanKarinaRomaMap.put("メ", "me");
        cleanKarinaRomaMap.put("も", "mo");
        cleanKarinaRomaMap.put("モ", "mo");
        cleanKarinaRomaMap.put("や", "ya");
        cleanKarinaRomaMap.put("ヤ", "ya");
        cleanKarinaRomaMap.put("ゆ", "yu");
        cleanKarinaRomaMap.put("ユ", "yu");
        cleanKarinaRomaMap.put("よ", "yo");
        cleanKarinaRomaMap.put("ヨ", "yo");
        cleanKarinaRomaMap.put("ら", "ra");
        cleanKarinaRomaMap.put("ラ", "ra");
        cleanKarinaRomaMap.put("り", "ri");
        cleanKarinaRomaMap.put("リ", "ri");
        cleanKarinaRomaMap.put("る", "ru");
        cleanKarinaRomaMap.put("ル", "ru");
        cleanKarinaRomaMap.put("れ", "re");
        cleanKarinaRomaMap.put("レ", "re");
        cleanKarinaRomaMap.put("ろ", "ro");
        cleanKarinaRomaMap.put("ロ", "ro");
        cleanKarinaRomaMap.put("わ", "wa");
        cleanKarinaRomaMap.put("ワ", "wa");
        cleanKarinaRomaMap.put("ゐ", "wi");
        cleanKarinaRomaMap.put("ヰ", "wi");
        cleanKarinaRomaMap.put("ゑ", "we");
        cleanKarinaRomaMap.put("ヱ", "we");
        cleanKarinaRomaMap.put("を", "wo");
        cleanKarinaRomaMap.put("ヲ", "wo");
        cleanKarinaRomaMap.put("ん", "n");
        cleanKarinaRomaMap.put("ン", "n");

        // 基本的假名
        for (String key : Romaji2KarinaTest.cleanKarinaRomaMap.keySet()) {
            normalKarina += key;
        }
        normalKarinaPtn = "^[" + normalKarina + "ゃゅょャュョっッー]+$";
    }

    /**
     * 羅馬字轉換成假名列表
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日上午11:02:17
     * @param romaParam
     *            羅馬字串
     * @return 假名列表
     */
    public static List<String> getKarinaFromRomaji(String romaParam) {
        String romaStr = romaParam;
        if (null == romaStr || romaStr.trim().length() == 0) {
            return null;
        }
        romaStr = romaStr.trim().toLowerCase();

        List<String> res = new ArrayList<String>();
        List<String> hiras = getHiraganaFromRomaji(romaStr);
        List<String> katas = getKatakanaFromRomaji(romaStr);
        if (null != hiras && !hiras.isEmpty()) {
            res.addAll(hiras);
        }
        if (null != katas && !katas.isEmpty()) {
            res.addAll(katas);
        }
        // 還餘有羅馬字，排除
        if (!res.isEmpty()) {
            List<String> res2 = new ArrayList<String>();
            for (String str : res) {
                if (!str.matches("^.*[a-z].*$")) {
                    res2.add(str);
                }
            }
            res = res2;
        }
        return res;
    }

    /**
     * 羅馬字轉換成片假名列表
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日上午11:02:17
     * @param romaParam
     *            羅馬字串
     * @return 片假名列表
     */
    private static List<String> getKatakanaFromRomaji(String romaParam) {
        String romaStr = replaceCompatSpell(romaParam);
        List<String> res = new ArrayList<String>();
        List<String> resTmp = new ArrayList<String>();
        // 促音
        romaStr = checkAndTranslateSokuOn(romaStr, false);
        resTmp.add(romaStr);

        // 長音 aa ii ei uu ou
        // 都加倍，用或不用changyin
        List<String> chouon = new ArrayList<String>();
        chouon.add("aa");
        chouon.add("ii");
        chouon.add("ei");
        chouon.add("uu");
        chouon.add("ou");
        boolean hasChouon = false;
        for (String co : chouon) {
            if (romaStr.contains(co)) {
                hasChouon = true;
                break;
            }
        }
        if (hasChouon) {
            List<String> resTmp2 = new ArrayList<String>();
            resTmp2.addAll(resTmp);
            for (String tmp : resTmp) {
                for (String co : chouon) {
                    tmp = tmp.replaceAll(co, co.substring(0, co.length() - 1) + changyin);
                }
                resTmp2.add(tmp);
            }
            resTmp = resTmp2;
        }

        // 拗音
        // ky sy shy sh ty chy ch ny hy my ry
        // gy j zy dy by py
        // 只有j jy要加倍
        resTmp = checkAndDoubleByJaJuJo(resTmp);
        List<String> resTmp2 = new ArrayList<String>();
        for (int i = 0; i < resTmp.size(); i++) {
            romaStr = resTmp.get(i);
            romaStr = romaStr.replaceAll("kya", "キャ").replaceAll("kyu", "キュ").replaceAll("kyo", "キョ");
            romaStr = romaStr.replaceAll("sya|shya|sha", "シャ");
            romaStr = romaStr.replaceAll("syu|shyu|shu", "シュ");
            romaStr = romaStr.replaceAll("syo|shyo|sho", "ショ");
            romaStr = romaStr.replaceAll("tya|chya|cha", "チャ");
            romaStr = romaStr.replaceAll("tyu|chyu|chu", "チュ");
            romaStr = romaStr.replaceAll("tyo|chyo|cho", "チョ");
            romaStr = romaStr.replaceAll("nya", "ニャ").replaceAll("nyu", "ニュ").replaceAll("nyo", "ニョ");
            romaStr = romaStr.replaceAll("hya", "ヒャ").replaceAll("hyu", "ヒュ").replaceAll("hyo", "ヒョ");
            romaStr = romaStr.replaceAll("mya", "ミャ").replaceAll("myu", "ミュ").replaceAll("myo", "ミョ");
            romaStr = romaStr.replaceAll("rya", "リャ").replaceAll("ryu", "リュ").replaceAll("ryo", "リョ");
            romaStr = romaStr.replaceAll("gya", "ギャ").replaceAll("gyu", "ギュ").replaceAll("gyo", "ギョ");
            romaStr = romaStr.replaceAll("zya", "ジャ").replaceAll("zyu", "ジュ").replaceAll("zyo", "ジョ");
            romaStr = romaStr.replaceAll("dya", "ヂャ").replaceAll("dyu", "ヂュ").replaceAll("dyo", "ヂョ");
            romaStr = romaStr.replaceAll("bya", "ビャ").replaceAll("byu", "ビュ").replaceAll("byo", "ビョ");
            romaStr = romaStr.replaceAll("pya", "ピャ").replaceAll("pyu", "ピュ").replaceAll("pyo", "ピョ");
            resTmp2.add(romaStr);
        }
        resTmp = resTmp2;

        // 其他音
        // ji zu，加倍
        resTmp = checkAndDoubleByJiZu(resTmp);
        resTmp = checkAndTranslateHuTsuuOn(resTmp, false);

        res.addAll(resTmp);
        return res;
    }

    /**
     * 羅馬字轉換成平假名列表
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日上午11:02:17
     * @param romaParam
     *            羅馬字串
     * @return 平假名列表
     */
    private static List<String> getHiraganaFromRomaji(String romaParam) {
        String romaStr = replaceCompatSpell(romaParam);
        List<String> res = new ArrayList<String>();
        List<String> resTmp = new ArrayList<String>();
        // 促音
        romaStr = checkAndTranslateSokuOn(romaStr, true);
        resTmp.add(romaStr);

        // 長音可以不管

        // 拗音
        // ky sy shy sh ty chy ch ny hy my ry
        // gy j zy dy by py
        // 只有j jy要加倍
        resTmp = checkAndDoubleByJaJuJo(resTmp);
        List<String> resTmp2 = new ArrayList<String>();
        for (int i = 0; i < resTmp.size(); i++) {
            romaStr = resTmp.get(i);
            romaStr = romaStr.replaceAll("kya", "きゃ").replaceAll("kyu", "きゅ").replaceAll("kyo", "きょ");
            romaStr = romaStr.replaceAll("sya|shya|sha", "しゃ");
            romaStr = romaStr.replaceAll("syu|shyu|shu", "しゅ");
            romaStr = romaStr.replaceAll("syo|shyo|sho", "しょ");
            romaStr = romaStr.replaceAll("tya|chya|cha", "ちゃ");
            romaStr = romaStr.replaceAll("tyu|chyu|chu", "ちゅ");
            romaStr = romaStr.replaceAll("tyo|chyo|cho", "ちょ");
            romaStr = romaStr.replaceAll("nya", "にゃ").replaceAll("nyu", "にゅ").replaceAll("nyo", "にょ");
            romaStr = romaStr.replaceAll("hya", "ひゃ").replaceAll("hyu", "ひゅ").replaceAll("hyo", "ひょ");
            romaStr = romaStr.replaceAll("mya", "みゃ").replaceAll("myu", "みゅ").replaceAll("myo", "みょ");
            romaStr = romaStr.replaceAll("rya", "りゃ").replaceAll("ryu", "りゅ").replaceAll("ryo", "りょ");
            romaStr = romaStr.replaceAll("gya", "ぎゃ").replaceAll("gyu", "ぎゅ").replaceAll("gyo", "ぎょ");
            romaStr = romaStr.replaceAll("zya", "じゃ").replaceAll("zyu", "じゅ").replaceAll("zyo", "じょ");
            romaStr = romaStr.replaceAll("dya", "ぢゃ").replaceAll("dyu", "ぢゅ").replaceAll("dyo", "ぢょ");
            romaStr = romaStr.replaceAll("bya", "びゃ").replaceAll("byu", "びゅ").replaceAll("byo", "びょ");
            romaStr = romaStr.replaceAll("pya", "ぴゃ").replaceAll("pyu", "ぴゅ").replaceAll("pyo", "ぴょ");
            resTmp2.add(romaStr);
        }
        resTmp = resTmp2;

        // 其他音
        // ji zu，加倍
        resTmp = checkAndDoubleByJiZu(resTmp);
        resTmp = checkAndTranslateHuTsuuOn(resTmp, true);

        res.addAll(resTmp);
        return res;
    }

    /**
     * 傳入編碼列表，如果含ja、ju、jo，按行加倍
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日下午2:05:08
     * @param listCode
     * @return
     */
    private static List<String> checkAndDoubleByJaJuJo(List<String> listCode) {
        List<String> resTmp = new ArrayList<String>();
        for (String code : listCode) {
            resTmp.add(code.replaceAll("jy", "j"));
        }
        if (resTmp.get(0).contains("ja") || resTmp.get(0).contains("ju") || resTmp.get(0).contains("jo")) {
            List<String> resTmp3 = new ArrayList<String>();
            for (String str : resTmp) {
                resTmp3.add(str.replaceAll("ja", "zya").replaceAll("ju", "zyu").replaceAll("jo", "zyo"));
                resTmp3.add(str.replaceAll("ja", "dya").replaceAll("ju", "dyu").replaceAll("jo", "dyo"));
            }
            resTmp = resTmp3;
        }
        return resTmp;
    }

    /**
     * 傳入編碼列表，如果含ji、zu，按行加倍
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日下午2:07:13
     * @param listCode
     * @return
     */
    private static List<String> checkAndDoubleByJiZu(List<String> listCode) {
        List<String> resTmp = listCode;
        if (resTmp.get(0).contains("ji") || resTmp.get(0).contains("zu")) {
            List<String> resTmp3 = new ArrayList<String>();
            for (String str : resTmp) {
                resTmp3.add(str.replaceAll("ji", "zi"));
                resTmp3.add(str.replaceAll("ji", "di").replaceAll("zu", "du"));
            }
            resTmp = resTmp3;
        }
        return resTmp;
    }

    /**
     * 日文的兼容拼法換成標準拼寫
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日下午2:33:05
     * @param code
     * @return
     */
    private static String replaceCompatSpell(String code) {
        // 兼容拼法。注意順序。
        String romaStr = code;
        romaStr = romaStr.replaceAll("jy", "j");
        romaStr = romaStr.replaceAll("shi", "si");
        romaStr = romaStr.replaceAll("chi", "ti");
        romaStr = romaStr.replaceAll("tsu", "tu");
        romaStr = romaStr.replaceAll("shy", "sy");
        romaStr = romaStr.replaceAll("chy", "ty");
        romaStr = romaStr.replaceAll("sh", "sy");
        romaStr = romaStr.replaceAll("ch", "ty");
        return romaStr;
    }

    /**
     * 所有促音轉假名<br />
     * 促音，か行、さ行、た行、だ行、ぱ行前，如kk ss ssh tt tch dd pp
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日下午2:15:20
     * @param listCode
     *            編碼列表
     * @param isHiragana
     *            是否平假名
     * @return
     */
    private static String checkAndTranslateSokuOn(String romaStr, boolean isHiragana) {
        String sokuon = (isHiragana) ? "っ" : "ッ";
        romaStr = romaStr.replaceAll("kk", sokuon + "k").replaceAll("ss", sokuon + "s");
        romaStr = romaStr.replaceAll("tt", sokuon + "t").replaceAll("tch", sokuon + "ch").replaceAll("dd", sokuon + "d");
        romaStr = romaStr.replaceAll("pp", sokuon + "p");
        return romaStr;
    }

    /**
     * 其他的音轉假名
     * 
     * @author fszhouzz@qq.com
     * @time 2018年1月22日下午2:15:20
     * @param listCode
     *            編碼列表
     * @param isHiragana
     *            是否平假名
     * @return
     */
    private static List<String> checkAndTranslateHuTsuuOn(List<String> listCode, boolean isHiragana) {
        List<String> resTmp = listCode;
        // 先看長音爲2的，再看爲1的
        for (int len = 2; len > 0; len--) {
            for (Entry<String, String> en : cleanKarinaRomaMap.entrySet()) {
                // 是平假名
                boolean isTarget = (isHiragana == en.getKey().matches("[ぁ-ゞ]+"));
                if (isTarget && en.getValue().length() == len) {
                    List<String> resTmp3 = new ArrayList<String>();
                    for (String str : resTmp) {
                        resTmp3.add(str.replaceAll(en.getValue(), en.getKey()));
                    }
                    resTmp = resTmp3;
                }
            }
        }
        return resTmp;
    }
}
