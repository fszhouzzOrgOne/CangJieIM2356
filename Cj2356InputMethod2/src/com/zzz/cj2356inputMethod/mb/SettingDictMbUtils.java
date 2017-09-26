package com.zzz.cj2356inputMethod.mb;

import java.util.ArrayList;
import java.util.List;

import com.zzz.cj2356inputMethod.dto.Group;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj2;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj3;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj5;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCj6;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCjMs;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCnCjYhqm;

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
            dictIms.add(new InputMethodStatusCnCj6(context));
            dictIms.add(new InputMethodStatusCnCj5(context));
            dictIms.add(new InputMethodStatusCnCj3(context));
            dictIms.add(new InputMethodStatusCnCjYhqm(context));
            dictIms.add(new InputMethodStatusCnCjMs(context));
            dictIms.add(new InputMethodStatusCnCj2(context));
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

}
