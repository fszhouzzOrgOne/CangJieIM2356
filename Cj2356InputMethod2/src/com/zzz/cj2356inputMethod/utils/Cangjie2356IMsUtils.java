package com.zzz.cj2356inputMethod.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnAC;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnAb;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnCircledAC;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnCircledAb;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnCircledaa;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnParenthesizedaa;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnScriptAC;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnScriptAb;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnScriptaa;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnShort;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnUpsideDown;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnaa;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj2;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj3;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj35;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj5;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj6;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCjMacOsX105;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCjMs;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCjYhqm;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseBraille;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseIpa;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseJyutp;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseKarina;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseKorea;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseKoxhanh;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseManju;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElsePy;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseSghm;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseSionTanTseng;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseUnicode;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseWugniuL;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseWugniuOYRA;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseZyfh;

import android.content.Context;

/**
 * 輸入法配置
 * 
 */
public class Cangjie2356IMsUtils {
    private static boolean inited = false;
    private static Context context;
    /**
     * 當前輸入法
     */
    private static Map<String, InputMethodStatus> currentIMsMap = null;

    public static final String ORDER_TYPE_EN = "en";
    public static final String ORDER_TYPE_CJ = "cj";
    public static final String ORDER_TYPE_ELSE = "else";
    private static final String ORDER_TYPE_KEY = "im.order.type";
    private static final String ORDER_EN_KEY = "im.order.en";
    public static final String ORDER_CJ_KEY = "im.order.cj";
    private static final String ORDER_ELSE_KEY = "im.order.else";

    public static final String ORDER_CJDICT_KEY = "im.order.cjdict";

    // 用於在下面的映射中，放入上面的ORDER_EN_KEY\ORDER_CJ_KEY\ORDER_ELSE_KEY等
    private static final String ORDER_KEY_KEY = "im_type_key";

    private static final Map<String, Object> allEnIMsMap = new HashMap<String, Object>();
    private static final Map<String, Object> allCjIMsMap = new HashMap<String, Object>();
    private static final Map<String, Object> allElseIMsMap = new HashMap<String, Object>();
    private static final Map<String, Map<String, Object>> allIMsMap = new HashMap<String, Map<String, Object>>();

    public static void init(Context con) {
        if (inited) {
            return;
        }
        inited = true;
        context = con;

        try {
            initAllIms(context);

            initAllCurrentIms();
        } catch (Exception e) {
        }
    }

    /**
     * 得到輸入法類型的配置
     */
    public static String getImOrderTypeCfg() {
        try {
            return Cangjie2356ConfigUtils.getConfig(ORDER_TYPE_KEY);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 第一個輸入法
     * 
     * @author fszhouzz@qq.com
     * @time 2018年7月19日 下午11:43:21
     * @param conType
     *            類別，如果未傳入，返回所有種類的第一個輸入法
     * @return
     */
    public static InputMethodStatus getFirstIm(String conType) {
        InputMethodStatus firstIMStatus = null;
        // 第一個方法
        String firstType = null;
        if (null == conType || "".equals(conType)) {
            String conTypeOrder = getImOrderTypeCfg();
            String[] conTypeArr = conTypeOrder.split(",");
            firstType = conTypeArr[0];
        } else {
            firstType = conType;
        }
        String firstImConfig = Cangjie2356ConfigUtils.getConfig(
                (String) allIMsMap.get(firstType).get(ORDER_KEY_KEY));
        String firstImCode = firstImConfig.split(",")[0];
        firstIMStatus = (InputMethodStatus) allIMsMap.get(firstType)
                .get(firstImCode);
        return firstIMStatus;
    }

    /**
     * 初始所有當前輸入法
     * 
     * @return
     */
    public static void initAllCurrentIms() {
        InputMethodStatus firstIMStatus = null;
        try {
            currentIMsMap = new HashMap<String, InputMethodStatus>();

            String conTypeOrder = getImOrderTypeCfg();
            String[] conTypeArr = conTypeOrder.split(",");
            // 初始化爲各自第一個方法
            for (String conType : conTypeArr) {
                String firstImConfig = Cangjie2356ConfigUtils.getConfig(
                        (String) allIMsMap.get(conType).get(ORDER_KEY_KEY));
                String firstImCode = firstImConfig.split(",")[0];
                firstIMStatus = (InputMethodStatus) allIMsMap.get(conType)
                        .get(firstImCode);

                currentIMsMap.put(conType, firstIMStatus);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 得到種類的當前輸入法
     * 
     * @author fszhouzz@qq.com
     * @time 2017年10月31日上午9:46:19
     * @param orderType
     *            類別
     * @return
     */
    public static InputMethodStatus getCurrentIm(String orderType) {
        if (null != orderType && !"".equals(orderType)) {
            return currentIMsMap.get(orderType);
        }
        return null;
    }

    /**
     * 設置種類的當前輸入法
     * 
     * @author fszhouzz@qq.com
     * @time 2017年10月31日上午9:46:19
     * @param orderType
     * @return
     */
    public static void setCurrentIm(String orderType, InputMethodStatus stat) {
        if (currentIMsMap.keySet().contains(orderType)) {
            currentIMsMap.put(orderType, stat);
        }
    }

    /**
     * 初始化所有輸入法
     * 
     * @author fszhouzz@qq.com
     * @time 2017年10月31日上午9:47:12
     * @param context
     */
    private static void initAllIms(Context context) {
        InputMethodStatus im = new InputMethodStatusEnaa(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnAb(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnAC(context);
        allEnIMsMap.put(im.getSubType(), im);

        im = new InputMethodStatusEnUpsideDown(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnShort(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnScriptaa(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnScriptAb(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnScriptAC(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnCircledaa(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnCircledAb(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnCircledAC(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnParenthesizedaa(context);
        allEnIMsMap.put(im.getSubType(), im);

        im = new InputMethodStatusCnCj6(context);
        allCjIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnCj5(context);
        allCjIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnCjMacOsX105(context);
        allCjIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnCj35(context);
        allCjIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnCj3(context);
        allCjIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnCjMs(context);
        allCjIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnCjYhqm(context);
        allCjIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnCj2(context);
        allCjIMsMap.put(im.getSubType(), im);

        im = new InputMethodStatusCnElseSghm(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElsePy(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseJyutp(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseZyfh(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseKarina(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseManju(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseKorea(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseIpa(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseKoxhanh(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseUnicode(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseBraille(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseSionTanTseng(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseWugniuL(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseWugniuOYRA(context);
        allElseIMsMap.put(im.getSubType(), im);

        allEnIMsMap.put(ORDER_KEY_KEY, ORDER_EN_KEY);
        allCjIMsMap.put(ORDER_KEY_KEY, ORDER_CJ_KEY);
        allElseIMsMap.put(ORDER_KEY_KEY, ORDER_ELSE_KEY);

        allIMsMap.put(ORDER_TYPE_EN, allEnIMsMap);
        allIMsMap.put(ORDER_TYPE_CJ, allCjIMsMap);
        allIMsMap.put(ORDER_TYPE_ELSE, allElseIMsMap);

        initNextIMStatuses();
    }

    /**
     * 下一個輸入狀態
     * 
     * @author fszhouzz
     * @time 2017年10月30日上午9:38:35
     */
    private static void initNextIMStatuses() {
        // 設置各個輸入法的，下一個狀態
        String conTypeOrder = getImOrderTypeCfg();
        String[] conTypes = conTypeOrder.split(","); // 種類數組
        List<String> conTypeList = new ArrayList<String>(); // 有效種類列表
        for (String con : conTypes) {
            if (null != con && con.length() > 0) {
                conTypeList.add(con);
            }
        }

        // 種類數組，設置種類下一狀態
        // 所在種類下一個輸入法，爲下一個種類的第一個輸入法
        for (int i = 0; i < conTypeList.size(); i++) {
            InputMethodStatus ims = null;
            if (i == conTypeList.size() - 1) {
                String firstImConfig = conTypeList.get(0);
                Map<String, Object> msMap = allIMsMap.get(firstImConfig);
                String firstImCode = Cangjie2356ConfigUtils
                        .getConfig(msMap.get(ORDER_KEY_KEY).toString())
                        .split(",")[0];
                ims = (InputMethodStatus) msMap.get(firstImCode);
            } else {
                String firstImConfig = conTypeList.get(i + 1);
                Map<String, Object> msMap = allIMsMap.get(firstImConfig);
                String firstImCode = Cangjie2356ConfigUtils
                        .getConfig(msMap.get(ORDER_KEY_KEY).toString())
                        .split(",")[0];
                ims = (InputMethodStatus) msMap.get(firstImCode);
            }
            if (null != ims) {
                Map<String, Object> msMap = allIMsMap.get(conTypeList.get(i));
                for (String key : msMap.keySet()) {
                    if (msMap.get(key) instanceof InputMethodStatus) {
                        InputMethodStatus imOne = (InputMethodStatus) msMap
                                .get(key);
                        imOne.setNextStatusType(ims);
                    }
                }
            }
        }

        // 輸入法數組，設置輸入法下一狀態
        // 本種類內的下個輸入法
        for (int i = 0; i < conTypeList.size(); i++) {
            Map<String, Object> msMap = allIMsMap.get(conTypeList.get(i));
            String theImConfig = Cangjie2356ConfigUtils
                    .getConfig(msMap.get(ORDER_KEY_KEY).toString());
            String[] theImConfigArr = theImConfig.split(",");

            for (int j = 0; j < theImConfigArr.length; j++) {
                InputMethodStatus ims = null;
                if (j == theImConfigArr.length - 1) {
                    ims = (InputMethodStatus) msMap.get(theImConfigArr[0]);
                } else {
                    ims = (InputMethodStatus) msMap.get(theImConfigArr[j + 1]);
                }
                InputMethodStatus imOne = (InputMethodStatus) msMap
                        .get(theImConfigArr[j]);
                imOne.setNextStatus(ims);

            }
        }
    }
}
