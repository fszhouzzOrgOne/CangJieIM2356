package com.zzz.cj2356inputMethod.utils;

import java.util.HashMap;
import java.util.Map;

import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnAC;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnAb;
import com.zzz.cj2356inputMethod.state.en.InputMethodStatusEnaa;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj2;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj3;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj35;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj5;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj6;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCjMs;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCjYhqm;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseKarina;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseKorea;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseManju;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElsePy;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseSghm;
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
    private static InputMethodStatus currentIMStatus = null;

    private static final String ORDER_TYPE_EN = "en";
    private static final String ORDER_TYPE_CJ = "cj";
    private static final String ORDER_TYPE_ELSE = "else";
    private static final String ORDER_TYPE_KEY = "im.order.type";
    private static final String ORDER_EN_KEY = "im.order.en";
    private static final String ORDER_CJ_KEY = "im.order.cj";
    private static final String ORDER_ELSE_KEY = "im.order.else";

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

        initAllIms(context);

        currentIMStatus = getFirstIm();
    }

    /**
     * 當前輸入法
     * 
     * @return
     */
    public static InputMethodStatus currentIMStatus() {
        return currentIMStatus;
    }

    /**
     * 初始輸入法
     * 
     * @return
     */
    public static InputMethodStatus getFirstIm() {
        String conTypeOrder = Cangjie2356ConfigUtils.getConfig(ORDER_TYPE_KEY);
        String firstType = conTypeOrder.split(",")[0];
        String firstImConfig = Cangjie2356ConfigUtils.getConfig((String) allIMsMap.get(firstType).get(ORDER_KEY_KEY));
        String firstImCode = firstImConfig.split(",")[0];
        return (InputMethodStatus) allIMsMap.get(firstType).get(firstImCode);
    }

    private static void initAllIms(Context context) {
        InputMethodStatus im = new InputMethodStatusEnaa(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnAb(context);
        allEnIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusEnAC(context);
        allEnIMsMap.put(im.getSubType(), im);

        im = new InputMethodStatusCnCj6(context);
        allCjIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnCj5(context);
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
        im = new InputMethodStatusCnElseZyfh(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseKarina(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseManju(context);
        allElseIMsMap.put(im.getSubType(), im);
        im = new InputMethodStatusCnElseKorea(context);
        allElseIMsMap.put(im.getSubType(), im);

        allEnIMsMap.put(ORDER_KEY_KEY, ORDER_EN_KEY);
        allCjIMsMap.put(ORDER_KEY_KEY, ORDER_CJ_KEY);
        allElseIMsMap.put(ORDER_KEY_KEY, ORDER_ELSE_KEY);

        allIMsMap.put(ORDER_TYPE_EN, allEnIMsMap);
        allIMsMap.put(ORDER_TYPE_CJ, allCjIMsMap);
        allIMsMap.put(ORDER_TYPE_ELSE, allElseIMsMap);
        
        // 下一個狀態
        String conTypeOrder = Cangjie2356ConfigUtils.getConfig(ORDER_TYPE_KEY);
        String[] conTypes = conTypeOrder.split(",");
        String[] conTypeIms = new String[conTypes.length];
        for (int i = 0; i < conTypes.length; i++) {
            String firstImConfig = Cangjie2356ConfigUtils.getConfig((String) allIMsMap.get(conTypes[i]).get(ORDER_KEY_KEY));
            conTypeIms[i] = firstImConfig;
        }
    }
}
