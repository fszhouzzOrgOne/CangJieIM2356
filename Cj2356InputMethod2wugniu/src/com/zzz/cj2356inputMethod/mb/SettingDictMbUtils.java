package com.zzz.cj2356inputMethod.mb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zzz.cj2356inputMethod.dto.Group;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj2;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj3;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj35;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj5;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj6;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCjMacOsX105;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCjMs;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCjYhqm;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseSghm;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnElseWugniuL;
import com.zzz.cj2356inputMethod.utils.Cangjie2356ConfigUtils;
import com.zzz.cj2356inputMethod.utils.Cangjie2356IMsUtils;
import com.zzz.cj2356inputMethod.utils.StringUtils;

import android.content.Context;

/**
 * 倉頡字典的相關查詢
 * 
 * 
 * @author 日月遞炤
 * @time 2017年9月26日 下午10:43:48
 */
public class SettingDictMbUtils {

    private static Context context;

    /**
     * 字典查询的輸入法
     */
    private static final List<InputMethodStatusCn> dictIms = new ArrayList<InputMethodStatusCn>();

    public static void init(Context con) {
        context = con;
        if (dictIms.isEmpty()) {
            try {
                Cangjie2356ConfigUtils.init(context);
                String cjConfig = Cangjie2356ConfigUtils.getConfig(Cangjie2356IMsUtils.ORDER_CJDICT_KEY);
                String[] cjConfigArr = cjConfig.split(",");
                List<String> cjConfigList = new ArrayList<String>();
                for (String conf : cjConfigArr) {
                    cjConfigList.add(conf);
                }

                Map<String, Object> allCjIMsMap = new LinkedHashMap<String, Object>();
                InputMethodStatus im = new InputMethodStatusCnCj6(context);
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
                allCjIMsMap.put(im.getSubType(), im);
                im = new InputMethodStatusCnElseWugniuL(context);
                allCjIMsMap.put(im.getSubType(), im);

                for (String key : allCjIMsMap.keySet()) {
                    if (cjConfigList.contains(key)) {
                        dictIms.add((InputMethodStatusCn) allCjIMsMap.get(key));
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 初始化字典查詢展示
     * 
     * @author 日月遞炤
     * @time 2017年9月26日 下午10:44:46
     * @return ArrayList<Group> 輸入法分組結果
     */
    public static List<Group> initGroupDatas() {
        List<Group> gData = new ArrayList<Group>();
        for (int i = 0; i < dictIms.size(); i++) {
            gData.add(new Group(i, dictIms.get(i).getSubType(), dictIms.get(i).getInputMethodName()));
        }
        return gData;
    }

    /**
     * 按編碼查詢
     * 
     * @author fsz
     * @time 2017年9月27日上午9:42:40
     * @param query
     * @return
     */
    public static List<Group> selectDbByCode(String query) {
        List<Group> gData = new ArrayList<Group>();
        for (int i = 0; i < dictIms.size(); i++) {
            Group g = new Group(i, dictIms.get(i).getSubType(), dictIms.get(i).getInputMethodName());
            List<Item> items = dictIms.get(i).getCandidatesInfo(query, false);
            // List<Item> items = null;
            // if (MbUtils.TYPE_CODE_CJGEN6.equals(dictIms.get(i).getSubType()))
            // {
            // items = dictIms.get(i).getCandidatesInfo(query, false);
            // }
            if (null != items && !items.isEmpty()) {
                for (Item it : items) {
                    if (StringUtils.hasText(it.getEncode())) {
                        it.setEncodeName(dictIms.get(i).translateCode2Name(it.getEncode()));
                    }
                }
                g.setItems(items);
            }
            gData.add(g);
        }
        return gData;
    }

    /**
     * 按字查詢
     * 
     * @author fsz
     * @time 2017年9月27日上午9:42:40
     * @param query
     * @return
     */
    public static List<Group> selectDbByChar(String query) {
        String[] chas = query.split("");
        List<Group> gData = new ArrayList<Group>();
        for (int i = 0; i < dictIms.size(); i++) {
            // 去重
            Set<String> queried = new HashSet<String>(); 
            Group g = new Group(i, dictIms.get(i).getSubType(), dictIms.get(i).getInputMethodName());
            List<Item> items = new ArrayList<Item>();
            // 本分組查詢所有的字符，放入items中
            InputMethodStatusCn im = dictIms.get(i);
            for (String cha : chas) {
                if (queried.contains(cha)) {
                    continue;
                }
                List<Item> items1 = im.getCandidatesInfoByChar(cha);
                if (null != items1 && !items1.isEmpty()) {
                    for (Item it : items1) {
                        if (StringUtils.hasText(it.getEncode())) {
                            it.setEncodeName(im.translateCode2Name(it.getEncode()));
                        }
                    }
                    items.addAll(items1);
                }
                queried.add(cha);
            }

            if (null != items && !items.isEmpty()) {
                g.setItems(items);
            }
            gData.add(g);
        }
        return gData;
    }

}
