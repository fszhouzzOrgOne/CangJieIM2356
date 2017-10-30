package com.zzz.cj2356inputMethod.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import android.widget.Toast;

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
    private static InputMethodStatus firstIMStatus = null;

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

        try {
            initAllIms(context);

            firstIMStatus = getFirstIm();
        } catch (Exception e) {
            Toast.makeText(context, "initAllIms: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 初始輸入法
     * 
     * @return
     */
    public static InputMethodStatus getFirstIm() {
        if (null != firstIMStatus) {
            return firstIMStatus;
        }
        try {
            String conTypeOrder = Cangjie2356ConfigUtils.getConfig(ORDER_TYPE_KEY);
            String firstType = conTypeOrder.split(",")[0];
            String firstImConfig = Cangjie2356ConfigUtils
                    .getConfig((String) allIMsMap.get(firstType).get(ORDER_KEY_KEY));
            String firstImCode = firstImConfig.split(",")[0];

            return (InputMethodStatus) allIMsMap.get(firstType).get(firstImCode);
        } catch (Exception e) {
            Toast.makeText(context, "getFirstIm: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
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

        initNextIMStatuses();

        Toast.makeText(context, "initAllNextIms ok.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 下一個輸入狀態
     * 
     * @author fszhouzz
     * @time 2017年10月30日上午9:38:35
     */
    private static void initNextIMStatuses() {
        // 設置各個輸入法的，下一個狀態
        String conTypeOrder = Cangjie2356ConfigUtils.getConfig(ORDER_TYPE_KEY);
        String[] conTypes = conTypeOrder.split(","); // 種類數組
        List<String> conTypeList = new ArrayList<String>(); // 有效種類列表
        for (String con : conTypes) {
            if (null != con && con.length() > 0) {
                conTypeList.add(con);
            }
        }

        // 種類數組，設置種類下一狀態
        for (int i = 0; i < conTypeList.size(); i++) {
            InputMethodStatus ims = null;
            if (i == conTypeList.size() - 1) {
                String firstImConfig = conTypeList.get(0);
                Map<String, Object> msMap = allIMsMap.get(firstImConfig);
                String firstImCode = Cangjie2356ConfigUtils.getConfig(msMap.get(ORDER_KEY_KEY).toString())
                        .split(",")[0];
                ims = (InputMethodStatus) msMap.get(firstImCode);
            } else {
                String firstImConfig = conTypeList.get(i + 1);
                Map<String, Object> msMap = allIMsMap.get(firstImConfig);
                String firstImCode = Cangjie2356ConfigUtils.getConfig(msMap.get(ORDER_KEY_KEY).toString())
                        .split(",")[0];
                ims = (InputMethodStatus) msMap.get(firstImCode);
            }
            if (null != ims) {
                Map<String, Object> msMap = allIMsMap.get(conTypeList.get(i));
                for (String key : msMap.keySet()) {
                    if (msMap.get(key) instanceof InputMethodStatus) {
                        InputMethodStatus imOne = (InputMethodStatus) msMap.get(key);
                        imOne.setNextStatusType(ims);
                    }
                }
            }
        }

        // 輸入法數組，設置輸入法下一狀態
        for (int i = 0; i < conTypeList.size(); i++) {
            Map<String, Object> msMap = allIMsMap.get(conTypeList.get(i));
            String theImConfig = Cangjie2356ConfigUtils.getConfig(msMap.get(ORDER_KEY_KEY).toString());
            String[] theImConfigArr = theImConfig.split(",");
            
            Toast.makeText(context, "設置輸入法下一狀態theImConfigArr：" + theImConfigArr.length, 
                    Toast.LENGTH_SHORT).show();
            
            for (int j = 0; j < theImConfigArr.length; j++) {
                InputMethodStatus ims = null;
                if (j == theImConfigArr.length - 1) {
                    ims = (InputMethodStatus) msMap.get(theImConfigArr[0]);
                } else {
                    ims = (InputMethodStatus) msMap.get(theImConfigArr[j + 1]);
                }
                InputMethodStatus imOne = (InputMethodStatus) msMap.get(theImConfigArr[j]);
                imOne.setNextStatus(ims);
                
                Toast.makeText(context, "設置輸入法下一狀態：" + ims, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
