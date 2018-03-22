package com.zzz.cj2356inputMethod.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.adapter.KeyBoardNumAdapter;
import com.zzz.cj2356inputMethod.listener.OnDeleteNumClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteNumLongClickListener;
import com.zzz.cj2356inputMethod.listener.OnSpaceNumClickListener;
import com.zzz.cj2356inputMethod.utils.StringUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class KeyboardSimIniter {
    // 一行幾個，下面有設置到@+id/keyboardBodySimGrid
    private static Integer SIM_ROW_SIZE = 5;
    private static Integer SIM_PAGE_ROW = 4;

    private static String currentSimMapKey = null;
    private static Context context;
    private static View keyboardView;

    private static LinearLayout keyboardBodySimScrollContent;
    private static List<TextView> keyboardBodySimScrollViews = null;

    private static GridView keyboardBodySimGrid; // 符號鍵盤

    private static Button prePageButton;
    private static Button nextPageButton;

    private static Map<String, List<String>> simMap = new HashMap<String, List<String>>();
    private static Map<String, String> typeNameKeyMap = new LinkedHashMap<String, String>();

    private static String PAGE_CN_KEY = "keyboardBodySimCn"; // 中文
    private static String PAGE_CNPART_KEY = "keyboardBodySimCnPart"; // 中文部首
    private static String PAGE_EN_KEY = "keyboardBodySimEn"; // 英文
    private static String PAGE_WH_KEY = "keyboardBodySimWenh"; // 文化
    private static String PAGE_JP_KEY = "keyboardBodySimJp"; // 日文
    private static String PAGE_KR_KEY = "keyboardBodySimKr"; // 韓文
    private static String PAGE_SP_KEY = "keyboardBodySimSp"; // 特殊
    private static String PAGE_MATH_KEY = "keyboardBodySimMath"; // 數學
    private static String PAGE_ORDER_KEY = "keyboardBodySimOrder"; // 序號
    private static String PAGE_PINYIN_KEY = "keyboardBodySimPy"; // 注音
    private static String PAGE_TAB_KEY = "keyboardBodySimTab"; // 製表
    private static String PAGE_LATIN_KEY = "keyboardBodySimLat"; // 拉丁
    private static String PAGE_GREERUSSIA_KEY = "keyboardBodySimGr"; // 希臘、俄文
    private static String PAGE_TIMEEVENT_KEY = "keyboardBodySimTimeEvent"; // 時間、節日
    private static String PAGE_FACES_KEY1 = "keyboardBodySimFaces1"; // 表情1
    private static String PAGE_FACES_KEY2 = "keyboardBodySimFaces2"; // 表情2
    private static String PAGE_FACES_KEY3 = "keyboardBodySimFaces3"; // 表情1
    private static String PAGE_IPA_KEY = "keyboardBodySimIpa"; // 國際音標

    static {
        // 中文
        simMap.put(PAGE_CN_KEY,
                getListByString("，、。？！：∶；…‘’“”＇＂〃（）〔〕〈〉《》［］｛｝「」『』〖〗【】～—＋－×÷＝＊＜＞｀Ұ¥￥＄ˇ︿ˉ＿¨．·•｜‖々／＆＼＃％⿰⿱⿲⿳⿴⿵⿶⿷⿸⿹⿺⿻"));
        simMap.put(PAGE_CNPART_KEY, getListByString(
                "丨亅丿乛一乙乚丶八勹匕冫刂儿二匚阝丷卩冂冖凵亻厶亠匸讠廴又艹屮彳巛辶廾彐彑口宀犭彡饣扌氵纟囗忄幺弋尢夂灬歹卝旡耂肀牜爿攴攵礻殳尣爻曰爫癶歺钅疒罒衤疋业艸虍覀糸糹镸辵豸釒靣飠髟鬲黽黹夊禸舛襾釆〇α乁乀巜乂丄丆丅龴丩刄亇丌丬乇卂孒乊卄夨乆龶丰冄兂冘龷丯円龵毌卬卅罓朩匁予戋龸甴氺冎丗仺氶叏丱戉両乑龹朿帇亙丣囪乕幷戼丳栆単眔埀宻豙睂臦"));
        // 英文
        simMap.put(PAGE_EN_KEY, getEnListString());
        // 文化
        simMap.put(PAGE_WH_KEY, getWenhuaListString());
        // 時間、節日
        simMap.put(PAGE_TIMEEVENT_KEY, getTimeEventListString());

        // 表情1 2 3
        simMap.put(PAGE_FACES_KEY1, getFacesListString1());
        simMap.put(PAGE_FACES_KEY2, getFacesListString2());
        simMap.put(PAGE_FACES_KEY3, getFacesListString3());

        // 特殊
        simMap.put(PAGE_SP_KEY, getListByString(
                "/\\╳︵︶︹︺︿﹀︴﹌﹉﹊﹍﹎╭╮╰╯︽︾﹁﹂﹃﹄﹏ˇ‥︷︸«»︻︼℡™ŠÕ©®‡†♂♀§№☆★♡♥●Θ○◎⊙◆◇▲▼△▽□■※▪〓¤°Ψ∮⊕卍卐囍㈱＿￣―￡↖↑↗←↹→↙↓↘҉̶⃢⏎⇧⇪⌂⌘☢☣⌥⎋⌫⌦⌨"));
        // 數學
        simMap.put(PAGE_MATH_KEY, getListByString(
                "＋－×÷≈≡≠＝±✘✔√≤≥＜＞≮≯∷╱╲∫∮∝∞∧∨∑∏∪∩∈∵∴⊥∥∠⌒⊙≌∽≒≦≧⅟½↉⅓⅔¼¾⅕⅖⅗⅘⅙⅚⅐⅛⅜⅝⅞⅑⅒％‰‱Ｆ′″º℃Å￠￡＄Ұ¥￥¤℉ℓΩ⁰¹²³⁴⁵⁶⁷⁸⁹⁺⁻⁼⁽⁾ⁿ₀₁₂₃₄₅₆₇₈₉₊₋₌₍₎ₐₑₒₓₔ㉐㋌㋍㋎㋏㍱㍲㍳㍴㍵㍶㍷㍸㍹㍺㎀㎁㎂㎃㎄㎅㎆㎇㎈㎉㎊㎋㎌㎍㎎㎏㎐㎑㎒㎓㎔㎕㎖㎗㎘㎙㎚㎛㎜㎝㎞㎟㎠㎡㎢㎣㎤㎥㎦㎧㎨㎩㎪㎫㎬㎭㎮㎯㎰㎱㎲㎳㎴㎵㎶㎷㎸㎹㎺㎻㎼㎽㎾㎿㏀㏁㏂㏃㏄㏅㏆㏇㏈㏉㏊㏋㏌㏍㏎㏏㏐㏑㏒㏓㏔㏕㏖㏗㏘㏙㏚㏛㏜㏝㏞㏟㏿"));
        // 序號
        simMap.put(PAGE_ORDER_KEY, getListByString(
                "⓪①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳㉑㉒㉓㉔㉕㉖㉗㉘㉙㉚㉛㉜㉝㉞㉟㊱㊲㊳㊴㊵㊶㊷㊸㊹㊺㊻㊼㊽㊾㊿❶❷❸❹❺❻❼❽❾❿⓫⓬⓭⓮⓯⓰⓱⓲⓳⓴㉈㉉㉊㉋㉌㉍㉎㉏１２３４５６７８９０⒈⒉⒊⒋⒌⒍⒎⒏⒐⒑⒒⒓⒔⒕⒖⒗⒘⒙⒚⒛⑴⑵⑶⑷⑸⑹⑺⑻⑼⑽⑾⑿⒀⒁⒂⒃⒄⒅⒆⒇〡〢〣〤〥〦〧〨〩十㈠㈡㈢㈣㈤㈥㈦㈧㈨㈩㊀㊁㊂㊃㊄㊅㊆㊇㊈㊉㊣〇一二三四五六七八九十零壹贰貳叁叄肆伍陆陸柒捌玖拾佰仟万萬亿億吉太拍艾ⅰⅱⅲⅳⅴⅵⅶⅷⅸⅹⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩⅪⅫ"));
        // 注音
        simMap.put(PAGE_PINYIN_KEY, getListByString(
                "āáǎàōóǒòêēéěèīíǐìūúǔùǖǘǚǜüˉˊˇˋ˙˪˫◌〪〭〫〬ㄅㄆㄇㄈㄉㄊㄋㄌㄍㄎㄏㄐㄑㄒㄓㄔㄕㄖㄗㄘㄙㄚㄛㄜㄝㄞㄟㄠㄡㄢㄣㄤㄥㄦㄧㄨㄩㄪㄫㄬㄭㄮㄯㆠㆡㆢㆣㆤㆥㆦㆧㆨㆩㆪㆫㆬㆭㆮㆯㆰㆱㆲㆳㆴㆵㆶㆷㆸㆹㆺ"));
        // 製表
        simMap.put(PAGE_TAB_KEY,
                getListByString("┌┍┎┏┐┑┒┓─┄┈├┝┞┟┠┡┢┣│┆┊┬┭┮┯┰┱┲┳┼┽┾┿╀╁╂└┕┖┗┘┙┚┛━┅┉┤┥┦┧┨┩┪┫┴┵┶┷┸┹┺┻╄╅╆╇╈╉╊╋"));
        // 拉丁
        simMap.put(PAGE_LATIN_KEY, getListByString("ÄÆÅÀÁÂÃÇĒĚÈÉÊËÐÌÍÎÏÖØÒÓÔÕÑÙÚÛÜÝÞäæåàáâãçēěèéêëðìíîïöøòóôõñùúûüýþ"));
        // 希俄
        simMap.put(PAGE_GREERUSSIA_KEY, getListByString(
                "αβγδεζηθικλμνξοπρστυφχψωΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩабвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"));
        // 日文
        simMap.put(PAGE_JP_KEY, getJapanListString());
        // 韓文
        simMap.put(PAGE_KR_KEY, getListByString(
                "ㅏㅓㅗㅜㅡㅣㅐㅔㅚㅟㅑㅕㅛㅠㅒㅖㅘㅙㅝㅞㅢㄱㄲㅋㄷㄸㅌㅂㅃㅍㅈㅉㅊㅅㅆㅎㄴㅁㅇㄹᅀᄼᄽᄾᄿᅎᅏᅐᅑᅔᅕᅌᄐᆞᆝᆟᆠᆡᆢ㈀㈁㈂㈃㈄㈅㈆㈇㈈㈉㈊㈋㈌㈍㈎㈏㈐㈑㈒㈓㈔㈕㈖㈗㈘㈙㈚㈛㈜㈝㈞㉠㉡㉢㉣㉤㉥㉦㉧㉨㉩㉪㉫㉬㉭㉮㉯㉰㉱㉲㉳㉴㉵㉶㉷㉸㉹㉺㉻㉼㉽㉾㉿"));

        // 國際音標
        simMap.put(PAGE_IPA_KEY, getIpaListString());
        
        typeNameKeyMap.put("中文", PAGE_CN_KEY);
        typeNameKeyMap.put("英文", PAGE_EN_KEY);
        typeNameKeyMap.put("部首", PAGE_CNPART_KEY);
        typeNameKeyMap.put("特殊", PAGE_SP_KEY);
        typeNameKeyMap.put("數學", PAGE_MATH_KEY);
        typeNameKeyMap.put("序號", PAGE_ORDER_KEY);
        typeNameKeyMap.put("文化", PAGE_WH_KEY);
        typeNameKeyMap.put("時節", PAGE_TIMEEVENT_KEY);
        typeNameKeyMap.put("表情1", PAGE_FACES_KEY1);
        typeNameKeyMap.put("表情2", PAGE_FACES_KEY2);
        typeNameKeyMap.put("表情3", PAGE_FACES_KEY3);
        typeNameKeyMap.put("音標", PAGE_IPA_KEY);
        typeNameKeyMap.put("注音", PAGE_PINYIN_KEY);
        typeNameKeyMap.put("日文", PAGE_JP_KEY);
        typeNameKeyMap.put("韓文", PAGE_KR_KEY);
        typeNameKeyMap.put("拉丁", PAGE_LATIN_KEY);
        typeNameKeyMap.put("希俄", PAGE_GREERUSSIA_KEY);
        typeNameKeyMap.put("製表", PAGE_TAB_KEY);
    }

    /**
     * 初始化符號鍵盤
     */
    public static void initKeyboardSim(Context con, View kbView) {
        currentSimMapKey = null;
        context = con;
        keyboardView = kbView;

        keyboardBodySimGrid = (GridView) keyboardView.findViewById(R.id.keyboardBodySimGrid);

        // keyboardBodySimScroll =
        // keyboardView.findViewById(R.id.keyboardBodySimScroll);
        keyboardBodySimScrollContent = (LinearLayout) keyboardView.findViewById(R.id.keyboardBodySimScrollContent);

        keyboardBodySimScrollViews = new ArrayList<TextView>();
        Iterator<String> itr = typeNameKeyMap.keySet().iterator();
        while (itr.hasNext()) {
            String keyName = itr.next();
            TextView textView = new TextView(context);
            textView.setText(keyName);
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
            textView.setPadding(50, 0, 50, 0);
            textView.setSingleLine();
            RelativeLayout.LayoutParams lpParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(lpParams);
            textView.setBackgroundResource(R.drawable.num_button_selector);

            textView.setOnClickListener(new MyKeyboardBodySimScrollClickListener(context));

            keyboardBodySimScrollContent.addView(textView);
            keyboardBodySimScrollViews.add(textView);
        }

        prePageButton = (Button) keyboardView.findViewById(R.id.keybtnSimPreviousPage);
        prePageButton.setOnClickListener(new OnPrePageButtonClickListener(context));
        nextPageButton = (Button) keyboardView.findViewById(R.id.keybtnSimNextPage);
        nextPageButton.setOnClickListener(new OnNextPageButtonClickListener(context));

        // 刪除鍵
        keyboardView.findViewById(R.id.keybtnSimDelete).setOnClickListener(new OnDeleteNumClickListener(context));
        keyboardView.findViewById(R.id.keybtnSimDelete)
                .setOnLongClickListener(new OnDeleteNumLongClickListener(context));
        // 空格鍵
        keyboardView.findViewById(R.id.keybtnSimSpace).setOnClickListener(new OnSpaceNumClickListener(context));
        // 符號鍵盤返回
        keyboardView.findViewById(R.id.keybtnSimBack).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 界面切換
                ViewFlipper viewFlipper = (ViewFlipper) keyboardView.findViewById(R.id.keyboardBodyFlipper);
                viewFlipper.showPrevious();
                viewFlipper.showPrevious();
            }
        });

        // 中文第一頁
        resetKeyboardSimPage();
    }

    /**
     * 國際音標符號
     * 
     * @author fszhouzz@qq.com
     * @time 2018年3月20日下午3:22:15
     * @return
     */
    private static List<String> getIpaListString() {
        String faceStr = "a ɐ ɑ ɒ æ ɑ̃ ʌ b ɓ ʙ β c ç ɕ ɔ ɔ̃ d ɗ ɖ ð d͡z d͡ʒ d̠͡ʑ ɖ͡ʐ e ə ɘ ɛ ɛ̃ ɜ ɚ f ɸ g ɡ ɠ ɢ ʛ ɣ ɤ h ħ ɦ ɧ ʜ ɥ i ĩ ɨ ɪ j ʝ ɟ ʄ k l ɫ ɬ ɭ ʟ ɮ ʎ m ɱ ɯ ɰ n ɲ ŋ ɳ ɴ o õ ɵ ø ɞ œ œ̃ ɶ ɔ ɔ̃ ʊ ʘ p ɸ p͡f q r ɾ ɼ ɺ ɽ ɹ ɻ ʀ ʁ s ʂ ʃ t ʈ θ t͡ɕ ʈ͡ʂ t͡s t͡ʃ u ũ ʉ ʊ v ʋ ѵ ⱱ ʌ w w̃ ʍ ɰ x χ y ʏ ɥ ʎ z ʑ ʐ ʒ ʔ ʡ ʕ ʢ ʘ ǀ ǂ ǁ ǃ ˈ ˌ ː ˑ ̆ || ‖ x͡y x͡ ͡ . ̋ ˥ ́ ˦ ̄ ˧ ̀ ˨ ̏ ˩ ̌ ̂ ꜜ ꜛ ↗ ↘ ̥ ̬ ʰ ʱ ʲ ʷ ˠ ˁ ̹ ̜ ̟ ̠ ̈ ̽ ̩ ̯ ˞ ̃ ̰ ̝ ̞ ̘ ̙ ̪ ̺ ̻ ⁿ ˡ ̚";
        String[] facesArr = faceStr.split(" ");
        List<String> list = new ArrayList<String>();
        for (String str : facesArr) {
            list.add(str);
        }
        return list;
    }

    /**
     * 獲取日文符號
     */
    private static List<String> getJapanListString() {
        // 先按日文符號區內順序
        // 與下面的有褈複
        String str1 = "ぁあぃいぅうぇえぉおかがきぎくぐけげこごさざしじすずせぜそぞただちぢっつづてでとどなにぬねのはばぱひびぴふぶぷへべぺほぼぽまみむめもゃやゅゆょよらりるれろゎわゐゑをんゔゕゖ゙゚゛゜ゝゞゟ゠ァアィイゥウェエォオカガキギクグケゲコゴサザシジスズセゼソゾタダチヂッツヅテデトドナニヌネノハバパヒビピフブプヘベペホボポマミムメモャヤュユョヨラリルレロヮワヰヱヲンヴヵヶヷヸヹヺ・ーヽヾ";

        str1 += "あいうゔえおアイウヴエオぁぃぅぇぉァィゥェォかゕきくけゖこカヵキクケヶコがぎぐげごガギグゲゴさしすせそサシスセソざじずぜぞザジズゼゾたちつってとタチツッテトだぢづでどダヂヅデドなにぬねのナニヌネノはひふへほハヒフヘホばびぶべぼバビブベボぱぴぷぺぽパピプペポまみむめもマミムメモやゆよヤユヨゃゅょャュョらりるれろラリルレロわゎゐゑをワヮヷヰヸヱヹヲヺんンー゠々ゝゞヽヾ〆乄ゟ゚゛゜ヿ・";
        str1 += "ㇰㇱㇲㇳㇴㇵㇶㇷㇸㇹㇺㇻㇼㇽㇾㇿ㈠㈡㈢㈣㈤㈥㈦㈧㈨㈩㈪㈫㈬㈭㈮㈯㈰㈱㈲㈳㈴㈵㈶㈷㈸㈹㈺㈻㈼㈽㈾㈿㉀㉁㉂㉃㉄㉅㉆㉇㊀㊁㊂㊃㊄㊅㊆㊇㊈㊉㊊㊋㊌㊍㊎㊏㊐㊑㊒㊓㊔㊕㊖㊗㊘㊙㊚㊛㊜㊝㊞㊟㊠㊡㊢㊣㊤㊥㊦㊧㊨㊩㊪㊫㊬㊭㊮㊯㊰㋐㋑㋒㋓㋔㋕㋖㋗㋘㋙㋚㋛㋜㋝㋞㋟㋠㋡㋢㋣㋤㋥㋦㋧㋨㋩㋪㋫㋬㋭㋮㋯㋰㋱㋲㋳㋴㋵㋶㋷㋸㋹㋺㋻㋼㋽㋾㌀㌁㌂㌃㌄㌅㌆㌇㌈㌉㌊㌋㌌㌍㌎㌏㌐㌑㌒㌓㌔㌕㌖㌗㌘㌙㌚㌛㌜㌝㌞㌟㌠㌡㌢㌣㌤㌥㌦㌧㌨㌩㌪㌫㌬㌭㌮㌯㌰㌱㌲㌳㌴㌵㌶㌷㌸㌹㌺㌻㌼㌽㌾㌿㍀㍁㍂㍃㍄㍅㍆㍇㍈㍉㍊㍋㍌㍍㍎㍏㍐㍑㍒㍓㍔㍕㍖㍗㍻㍼㍽㍾㍿";
        List<String> list = getListByString(str1);
        String faceStr = "🈁 🈂 🈷 🈶 🈯 🈹 🈚 🈲 🈸 🈴 🈳 🈺 🈵 🀄 🉐 🉑 ㊗ ㊙ ㊣ ㊀ ㊁ ㊂ ㊃ ㊄ ㊅ ㊆ ㊇ ㊈ ㊉ ㊎ ㊍ ㊌ ㊋ ㊏ ㊐ ㊊ ㊚ ㊛ ㊤ ㊥ ㊦ ㊧ ㊨ ㊞ ㊑ ㊒ ㊓ ㊓ ㊔ ㊕ ㊖ ㊗ ㊗ ㊘ ㊜ ㊝ ㊟ ㊠ ㊡ ㊢ ㊩ ㊪ ㊫ ㊬ ㊬ ㊭ ㊮ ㊮ ㊯ ㊰ ㊙ 🎌 🗾 👘 🏣 🏯 🎎 🎏";
        list = mergeFaceString2List(list, faceStr);
        return list;
    }

    /**
     * 獲取文化符號
     */
    private static List<String> getWenhuaListString() {
        String str1 = "☯⚋⚊☰☷☳☶☲☵☱☴卍卐❂☭☠☤☥☦☧☨☩☪☫☬☮☸☽☾♕♚♛✙✚✛✜✝✞✟✠✡✢";
        List<String> list = getListByString(str1);
        String faceStr = "🛐 ⚛ 🕉 ✡ ☸ ☯ ✝ ☦ ☪ ☮ 🕎 🔯 ♈ ♉ ♊ ♋ ♌ ♍ ♎ ♏ ♐ ♑ ♒ ♓ ⛎ ⛪ 🕌 🕍 ⛩ 🕋";
        list = mergeFaceString2List(list, faceStr);
        return list;
    }

    /**
     * 获取英文符號
     */
    private static List<String> getEnListString() {
        String str1 = ",.?!:;'\"~`@#$%‰^&+-*=_\\/|<>()[]{}¿	ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ⒜⒝⒞⒟⒠⒡⒢⒣⒤⒥⒦⒧⒨⒩⒪⒫⒬⒭⒮⒯⒰⒱⒲⒳⒴⒵";
        List<String> list = getListByString(str1);
        String faceStr = "🇦 🇧 🇨 🇩 🇪 🇫 🇬 🇭 🇮 🇯 🇰 🇱 🇲 🇳 🇴 🇵 🇶 🇷 🇸 🇹 🇺 🇻 🇼 🇽 🇾 🇿 © ® ™ 🔠 🔡 🔢 🔣 🔤 🅰 🆎 🅱 🆑 🆒 🆓 ℹ 🆔 Ⓜ 🆕 🆖 🅾 🆗 🅿 🆘 🆙 🆚 🔙 🔚 🔛 🔜 🔝 📴 🏧 🚾";
        list = mergeFaceString2List(list, faceStr);
        return list;
    }

    /**
     * 獲取時節符號
     */
    private static List<String> getTimeEventListString() {
        String str1 = "㋀㋁㋂㋃㋄㋅㋆㋇㋈㋉㋊㋋㏠㏡㏢㏣㏤㏥㏦㏧㏨㏩㏪㏫㏬㏭㏮㏯㏰㏱㏲㏳㏴㏵㏶㏷㏸㏹㏺㏻㏼㏽㏾㍙㍚㍛㍜㍝㍞㍟㍠㍡㍢㍣㍤㍥㍦㍧㍨㍩㍪㍫㍬㍭㍮㍯㍰㍘";
        List<String> list = getListByString(str1);
        String faceStr = "⌛ ⏳ ⌚ ⏰ ⏱ ⏲ 🕰 🕛 🕧 🕐 🕜 🕑 🕝 🕒 🕞 🕓 🕟 🕔 🕠 🕕 🕡 🕖 🕢 🕗 🕣 🕘 🕤 🕙 🕥 🕚 🕦 🌑 🌒 🌓 🌔 🌕 🌖 🌗 🌘 🌙 🌚 🌛 🌜 🌡 ☀ 🌝 🌞 ⭐ 🌟 🌠 🌁 🌃 🌄 🌅 🌆 🌇 🌉 ♨ 🌌 ☁ ⛅ ⛈ 🌤 🌥 🌦 🌧 🌨 🌩 🌪 🌫 🌬 🌀 🌈 🌂 ☂ ☔ ⛱ ⚡ ❄ ☃ ⛄ ☄ 🔥 💧 🌊 🎃 🎄 🎆 🎇 ✨ 🎈 🎉 🎊 🎋 🎍 🎐 🎑 🎀 🎁 🎗 🎟 🎫";
        list = mergeFaceString2List(list, faceStr);
        return list;
    }

    /**
     * 表情列表1 2 3
     */
    private static List<String> getFacesListString1() {
        String faces = "😀 😁 😂 😃 😄 😅 😆 😉 😊 😋 😎 😍 😘 😗 😙 😚 ☺ 🙂 🤗 🤔 😐 😑 😶 🙄 😏 😣 😥 😮 🤐 😯 😪 😫 😴 😌 😛 😜 😝 😒 😓 😔 😕 🙃 🤑 😲 ☹ 🙁 😖 😞 😟 😤 😢 😭 😦 😧 😨 😩 😬 😰 😱 😳 😵 😡 😠 😷 🤒 🤕 😇 🤓 😈 👿 👹 👺 💀 ☠ 👻 👽 👾 🤖 💩 😺 😸 😹 😻 😼 😽 🙀 😿 😾 🙈 🙉 🙊 👶 👦 👧 👨 👩 👴 👵 💪 👈 👉 ☝ 👆 🖕 👇 ✌ 🖖 🤘 🖐 ✋ 👌 👍 👎 ✊ 👊 👋 ✍ 👏 👐 🙌 🙏 💅 👂 👃 👣 👀 👁 🗨 👅 👄 💋 💘 ❤ 💓 💔 💕 💖 💗 💙 💚 💛 💜 💝 💞 💟 ❣ 💌 💤 💢 💣 💥 💦 💨 💫 💬 🗯 💭 🕳 👓 🕶 👔 👕 👖 👗 👙 👚 👛 👜 👝 🛍 🎒 👞 👟 👠 👡 👢 👑 👒 🎩 🎓 ⛑ 📿 💄 💍 💎 🐵 🐒 🐶 🐕 🐩 🐺 🐱 🐈 🦁 🐯 🐅 🐆 🐴 🐎 🦄 🐮 🐂 🐃 🐄 🐷 🐖 🐗 🐽 🐏 🐑 🐐 🐪 🐫 🐘 🐭 🐁 🐀 🐹 🐰 🐇 🐿 🐻 🐨 🐼 🐾 🦃 🐔 🐓 🐣 🐤 🐥 🐦 🐧 🕊 🐸 🐊 🐢 🐍 🐲 🐉 🐳 🐋 🐬 🐟 🐠 🐡 🐙 🐚 🦀 🐌 🐛 🐜 🐝 🐞 🕷 🕸 🦂 💐 🌸 💮 🏵 🌹 🌺 🌻 🌼 🌷 🌱 🌲 🌳 🌴 🌵 🌾 🌿 ☘ 🍀 🍁 🍂 🍃 🍇 🍈 🍉 🍊 🍋 🍌 🍍 🍎 🍏 🍐 🍑 🍒 🍓 🍅 🍆 🌽 🌶 🍄 🌰 🍞 🧀 🍖 🍗 🍔 🍟 🍕 🌭 🌮 🌯 🍳 🍲 🍿 🍱 🍘 🍙 🍚 🍛 🍜 🍝 🍠 🍢 🍣 🍤 🍥 🍡 🍦 🍧 🍨 🍩 🍪 🎂 🍰 🍫 🍬 🍭 🍮 🍯 🍼 ☕ 🍵 🍶 🍾 🍷 🍸 🍹 🍺 🍻 🍽 🍴 🔪 🏺";
        List<String> list = mergeFaceString2List(null, faces);
        return list;
    }

    private static List<String> getFacesListString2() {
        String faces = "🌍 🌎 🌏 🌐 🗺 🏔 ⛰ 🌋 🗻 🏕 🏖 🏜 🏝 🏞 🏟 🏛 🏗 🏘 🏙 🏚 🏠 🏡 🏢 🏣 🏤 🏥 🏦 🏨 🏩 🏪 🏫 🏬 🏭 🏰 💒 🗼 🗽 ⛲ ⛺ 🎠 🎡 🎢 💈 🎪 🎭 🖼 🎨 🎰 🚂 🚃 🚄 🚅 🚆 🚇 🚈 🚉 🚊 🚝 🚞 🚋 🚌 🚍 🚎 🚐 🚑 🚒 🚓 🚔 🚕 🚖 🚗 🚘 🚙 🚚 🚛 🚜 🚲 🚏 🛣 🛤 ⛽ 🚨 🚥 🚦 🚧 ⚓ ⛵ 🚤 🛳 ⛴ 🛥 🚢 ✈ 🛩 🛫 🛬 💺 🚁 🚟 🚠 🚡 🛰 🚀 🛎 🚪 🛏 🛋 🚽 🚿 🛁 🎖 🏆 🏅 ⚽ ⚾ 🏀 🏐 🏈 🏉 🎾 🎱 🎳 🏏 🏑 🏒 🏓 🏸 🎯 ⛳ ⛸ 🎣 🎽 🎿 🎮 🕹 🎲 ♠ ♥ ♦ ♣ 🃏 🀄 🎴 🔇 🔈 🔉 🔊 📢 📣 📯 🔔 🔕 🎼 🎵 🎶 🎙 🎚 🎛 🎤 🎧 📻 🎷 🎸 🎹 🎺 🎻 📱 📲 ☎ 📞 📟 📠 🔋 🔌 💻 🖥 🖨 ⌨ 🖱 🖲 💽 💾 💿 📀 🎥 🎞 📽 🎬 📺 📷 📸 📹 📼 🔍 🔎 🔬 🔭 📡 🕯 💡 🔦 🏮 📔 📕 📖 📗 📘 📙 📚 📓 📒 📃 📜 📄 📰 🗞 📑 🔖 🏷 💰 💴 💵 💶 💷 💸 💳 💹 💱 💲 ✉ 📧 📨 📩 📤 📥 📦 📫 📪 📬 📭 📮 🗳 ✏ ✒ 🖋 🖊 🖌 🖍 📝 💼 📁 📂 🗂 📅 📆 🗒 🗓 📇 📈 📉 📊 📋 📌 📍 📎 🖇 📏 📐 ✂ 🗃 🗄 🗑 🔒 🔓 🔏 🔐 🔑 🗝 🔨 ⛏ ⚒ 🛠 🗡 ⚔ 🔫 🏹 🛡 🔧 🔩 ⚙ 🗜 ⚗ ⚖ 🔗 ⛓ 💉 💊 🚬 ⚰ ⚱ 🗿 🛢 🔮 🏧 🚮 🚰 ♿ 🚹 🚺 🚻 🚼 🚾 🛂 🛃 🛄 🛅 ⚠ 🚸 ⛔ 🚫 🚳 🚭 🚯 🚱 🚷 📵 🔞 ☢ ☣";
        List<String> list = mergeFaceString2List(null, faces);
        return list;
    }

    private static List<String> getFacesListString3() {
        String faces = "⬆ ↗ ➡ ↘ ⬇ ↙ ⬅ ↖ ↕ ↔ ↩ ↪ ⤴ ⤵ 🔃 🔄 🔀 🔁 🔂 ▶ ⏩ ⏭ ⏯ ◀ ⏪ ⏮ 🔼 ⏫ 🔽 ⏬ ⏸ ⏹ ⏺ ⏏ 🎦 🔅 🔆 📶 📳 📴 ♀ ♂ ⚕ ♻ ⚜ 🔱 📛 🔰 ⭕ ✅ ☑ ✔ ✖ ❌ ❎ ➕ ➖ ➗ ➰ ➿ 〽 ✳ ✴ ❇ ‼ ⁉ ❓ ❔ ❕ ❗ 〰 💯 ▪ ▫ ◻ ◼ ◽ ◾ ⬛ ⬜ 🔶 🔷 🔸 🔹 🔺 🔻 💠 🔘 🔲 🔳 ⚪ ⚫ 🔴 🔵 🏁 🚩 🏴 🏳";
        List<String> list = mergeFaceString2List(null, faces);
        return list;
    }

    /**
     * 表情字符串合并入列表 faceStr 各表情字符用空格分隔
     */
    private static List<String> mergeFaceString2List(List<String> list, String faceStr) {
        String[] facesArr = faceStr.split(" ");
        Set<String> set = new LinkedHashSet<String>();
        if (null != list && !list.isEmpty()) {
            set.addAll(list);
        }
        for (String face : facesArr) {
            if (StringUtils.hasText(face) && !set.contains(face)) {
                set.add(face);
            }
        }
        return new ArrayList<String>(set);
    }

    /**
     * 把字符串转成字符串的列表
     * 
     * @author fszhouzz@qq.com
     * @time 2017年12月12日上午10:14:57
     * @param string
     * @return
     */
    private static List<String> getListByString(String keys) {
        Set<String> set = new LinkedHashSet<String>();
        for (int i = 0; i < keys.length(); i++) {
            Character cha = keys.charAt(i);
            if (!set.contains(cha.toString())) {
                set.add(cha.toString());
            }
        }
        return new ArrayList<String>(set);
    }

    /**
     * 回到中文第一頁
     */
    public static void resetKeyboardSimPage() {
        String pagekey = PAGE_CN_KEY;
        if (null != context) {
            setkeyboardBodySimGridKeys(context, pagekey + "_" + 1);
        }
        int index = 0;
        for (; index < keyboardBodySimScrollViews.size(); index++) {
            TextView tv = keyboardBodySimScrollViews.get(index);
            if (pagekey.equals(typeNameKeyMap.get(tv.getText()))) {
                break;
            }
        }
        setTabBiggerTextSiz(index);
        if (null != keyboardView) {
            // 左移焦點
            HorizontalScrollView sv = (HorizontalScrollView) keyboardView.findViewById(R.id.keyboardBodySimScroll);
            sv.scrollTo(0, 0);
        }
    }

    /**
     * 一共有幾頁
     * 
     * @return
     */
    public static Integer getKeyboardSimLastPage() {
        String simMapKey = currentSimMapKey;
        String simMapKeyPrefix = simMapKey.split("_")[0];
        List<String> content = simMap.get(simMapKeyPrefix);
        if (null != content && !content.isEmpty()) {
            int rows = content.size() / KeyboardSimIniter.SIM_ROW_SIZE;
            if (content.size() % KeyboardSimIniter.SIM_ROW_SIZE != 0) {
                rows++;
            }
            int pages = rows / KeyboardSimIniter.SIM_PAGE_ROW;
            if (rows % KeyboardSimIniter.SIM_PAGE_ROW != 0) {
                pages++;
            }
            return pages;
        }
        return 0;
    }

    /**
     * 獲取第幾頁符號
     * 
     * @param page
     * @return
     */
    public static List<String> getKeyboardSimByPage(int page) {
        String simMapKey = currentSimMapKey;
        String simMapKeyPrefix = simMapKey.split("_")[0];
        List<String> content = simMap.get(simMapKeyPrefix);
        int start = (page - 1) * KeyboardSimIniter.SIM_PAGE_ROW * KeyboardSimIniter.SIM_ROW_SIZE;
        int end = page * KeyboardSimIniter.SIM_PAGE_ROW * KeyboardSimIniter.SIM_ROW_SIZE - 1;
        if (end >= content.size() - 1) {
            // 結束位置，等於或超過了一行個數
            if (page > 1 && ((end - (content.size() - 1)) >= KeyboardSimIniter.SIM_ROW_SIZE)) {
                int emptyRow = (end - (content.size() - 1)) / KeyboardSimIniter.SIM_ROW_SIZE;
                for (int emp = emptyRow; emp > 0; emp--) {
                    start -= KeyboardSimIniter.SIM_ROW_SIZE;
                }
            }
            end = content.size() - 1;
        }
        return content.subList(start, end + 1);
    }

    private static void setkeyboardBodySimGridKeys(Context context, String simMapKey) {
        try {
            currentSimMapKey = simMapKey;

            int page = Integer.parseInt(simMapKey.split("_")[1]);
            List<String> keys = getKeyboardSimByPage(page);
            ArrayList<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
            for (int i = 0; i < keys.size(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                String cha = keys.get(i);
                map.put(KeyBoardNumAdapter.ITEM_KEY_NAME, cha);
                valueList.add(map);
            }
            KeyBoardNumAdapter keyBoardSimAdapter = new KeyBoardNumAdapter(context, valueList,
                    R.layout.keyboardsimitem);
            keyboardBodySimGrid.setNumColumns(SIM_ROW_SIZE);
            keyboardBodySimGrid.setAdapter(keyBoardSimAdapter);

            int totalPage = getKeyboardSimLastPage();
            prePageButton.setEnabled(!(page == 1));
            nextPageButton.setEnabled(!(page == totalPage));
        } catch (Exception e) {
        }
    }

    /**
     * 字體调大些
     * 
     * @author fsz
     * @time 2017年9月26日 下午9:30:02
     * @param curIndex
     */
    private static void setTabBiggerTextSiz(int curIndex) {
        for (int i = 0; i < keyboardBodySimScrollViews.size(); i++) {
            TextView textView = keyboardBodySimScrollViews.get(i);
            if (curIndex == i) {
                textView.setTextSize(17);
                textView.setTextColor(Color.BLACK);
            } else {
                textView.setTextSize(16);
                textView.setTextColor(Color.GRAY);
            }
        }
    }

    /**
     * 符號類別的點擊事件
     * 
     * @author fszhouzz@qq.com
     * @time 2017年11月1日上午9:15:09
     */
    private static class MyKeyboardBodySimScrollClickListener implements View.OnClickListener {

        private Context context;

        public MyKeyboardBodySimScrollClickListener(Context con) {
            super();
            this.context = con;
        }

        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            int index = keyboardBodySimScrollViews.indexOf(tv);
            setTabBiggerTextSiz(index);

            setkeyboardBodySimGridKeys(context, typeNameKeyMap.get(tv.getText()) + "_" + 1);
        }
    }

    private static class OnPrePageButtonClickListener implements OnClickListener {

        private Context context;

        public OnPrePageButtonClickListener(Context con) {
            super();
            this.context = con;
        }

        @Override
        public void onClick(View v) {
            if (StringUtils.hasText(currentSimMapKey)) {
                int page = Integer.parseInt(currentSimMapKey.split("_")[1]);
                if (page == 1) {
                    return;
                } else {
                    int topage = page - 1;
                    String simMapKey = currentSimMapKey.split("_")[0] + "_" + topage;
                    setkeyboardBodySimGridKeys(context, simMapKey);
                }
            }
        }
    }

    private static class OnNextPageButtonClickListener implements OnClickListener {

        private Context context;

        public OnNextPageButtonClickListener(Context con) {
            super();
            this.context = con;
        }

        @Override
        public void onClick(View v) {
            if (StringUtils.hasText(currentSimMapKey)) {
                int page = Integer.parseInt(currentSimMapKey.split("_")[1]);
                if (page == getKeyboardSimLastPage().intValue()) {
                    return;
                } else {
                    int topage = page + 1;
                    String simMapKey = currentSimMapKey.split("_")[0] + "_" + topage;
                    setkeyboardBodySimGridKeys(context, simMapKey);
                }
            }
        }
    }
}
