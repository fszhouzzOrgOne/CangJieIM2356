package com.zzz.cj2356inputMethod.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.adapter.KeyBoardNumAdapter;
import com.zzz.cj2356inputMethod.listener.OnDeleteNumClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteNumLongClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteRightClickListener;
import com.zzz.cj2356inputMethod.listener.OnDeleteRightLongClickListener;
import com.zzz.cj2356inputMethod.utils.DipPxUtil;
import com.zzz.cj2356inputMethod.utils.StringUtils;
import com.zzz.cj2356inputMethod.utils.UnicodeSimUtil;

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
    private static String PAGE_SP1_KEY = "keyboardBodySimSp1"; // 特殊1
    private static String PAGE_SP2_KEY = "keyboardBodySimSp2"; // 特殊2
    private static String PAGE_SP3_KEY = "keyboardBodySimSp3"; // 特殊3
    private static String PAGE_Biaoqing_KEY = "keyboardBodySimBiaoqing"; // 表情
    private static String PAGE_ARROW_KEY = "keyboardBodySimArrow"; // 箭頭
    private static String PAGE_SSSCRPT_KEY = "keyboardBodySimSupSubScript"; // 上下標
    private static String PAGE_MATH_KEY = "keyboardBodySimMath"; // 數學
    private static String PAGE_ORDER_KEY = "keyboardBodySimOrder"; // 序號
    private static String PAGE_MONEY_KEY = "keyboardBodySimMoney"; // 貨幣
    private static String PAGE_WH_KEY = "keyboardBodySimWenh"; // 文化
    private static String PAGE_TIMEEVENT_KEY = "keyboardBodySimTimeEvent"; // 時間、節日
    private static String PAGE_IPA_KEY = "keyboardBodySimIpa"; // 國際音標
    private static String PAGE_PINYIN_KEY = "keyboardBodySimPy"; // 注音
    private static String PAGE_JP_KEY = "keyboardBodySimJp"; // 日文
    private static String PAGE_KR_KEY = "keyboardBodySimKr"; // 韓文
    private static String PAGE_LATIN_KEY = "keyboardBodySimLat"; // 拉丁
    private static String PAGE_GREERUSSIA_KEY = "keyboardBodySimGr"; // 希臘、俄文
    private static String PAGE_TAB_KEY = "keyboardBodySimTab"; // 製表

    static {
        // 中文
        simMap.put(PAGE_CN_KEY, UnicodeSimUtil.getCnListString());
        // 中文部首
        simMap.put(PAGE_CNPART_KEY, UnicodeSimUtil.getCnPartListString());
        // 英文
        simMap.put(PAGE_EN_KEY, UnicodeSimUtil.getEnListString());
        // 特殊1
        simMap.put(PAGE_SP1_KEY, UnicodeSimUtil.getSpecial1ListString());
        // 特殊1
        simMap.put(PAGE_SP2_KEY, UnicodeSimUtil.getSpecial2ListString());
        // 特殊1
        simMap.put(PAGE_SP3_KEY, UnicodeSimUtil.getSpecial3ListString());
        // 表情
        simMap.put(PAGE_Biaoqing_KEY, UnicodeSimUtil.getBiaoqingListString());
        // 箭頭
        simMap.put(PAGE_ARROW_KEY, UnicodeSimUtil.getArrowListString());
        // 上下標
        simMap.put(PAGE_SSSCRPT_KEY,
                UnicodeSimUtil.getSuperSubScriptListString());
        // 數學
        simMap.put(PAGE_MATH_KEY, UnicodeSimUtil.getMathListByString());
        // 序號
        simMap.put(PAGE_ORDER_KEY, UnicodeSimUtil.getOrderListByString());
        // 文化
        simMap.put(PAGE_WH_KEY, UnicodeSimUtil.getWenhuaListString());
        // 貨幣
        simMap.put(PAGE_MONEY_KEY, UnicodeSimUtil.getMoneyListString());
        // 時間、節日
        simMap.put(PAGE_TIMEEVENT_KEY, UnicodeSimUtil.getTimeEventListString());
        // 國際音標
        simMap.put(PAGE_IPA_KEY, UnicodeSimUtil.getIpaListString());
        // 注音
        simMap.put(PAGE_PINYIN_KEY, UnicodeSimUtil.getPinyinListString());
        // 日文
        simMap.put(PAGE_JP_KEY, UnicodeSimUtil.getJapanListString());
        // 韓文
        simMap.put(PAGE_KR_KEY, UnicodeSimUtil.getKoreaListString());
        // 拉丁
        simMap.put(PAGE_LATIN_KEY, UnicodeSimUtil.getLatinListString());
        // 希俄
        simMap.put(PAGE_GREERUSSIA_KEY,
                UnicodeSimUtil.getGreerussiaListString());
        // 製表
        simMap.put(PAGE_TAB_KEY, UnicodeSimUtil.getTableListString());

        typeNameKeyMap.put("中文", PAGE_CN_KEY);
        typeNameKeyMap.put("英文", PAGE_EN_KEY);
        typeNameKeyMap.put("部首", PAGE_CNPART_KEY);
        typeNameKeyMap.put("符號1", PAGE_SP1_KEY);
        typeNameKeyMap.put("符號2", PAGE_SP2_KEY);
        typeNameKeyMap.put("符號3", PAGE_SP3_KEY);
        typeNameKeyMap.put("表情", PAGE_Biaoqing_KEY);
        typeNameKeyMap.put("箭頭", PAGE_ARROW_KEY);
        typeNameKeyMap.put("上下標", PAGE_SSSCRPT_KEY);
        typeNameKeyMap.put("數學", PAGE_MATH_KEY);
        typeNameKeyMap.put("序號", PAGE_ORDER_KEY);
        typeNameKeyMap.put("貨幣", PAGE_MONEY_KEY);
        typeNameKeyMap.put("文化", PAGE_WH_KEY);
        typeNameKeyMap.put("時節", PAGE_TIMEEVENT_KEY);
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

        keyboardBodySimGrid = (GridView) keyboardView
                .findViewById(R.id.keyboardBodySimGrid);

        // keyboardBodySimScroll =
        // keyboardView.findViewById(R.id.keyboardBodySimScroll);
        keyboardBodySimScrollContent = (LinearLayout) keyboardView
                .findViewById(R.id.keyboardBodySimScrollContent);

        keyboardBodySimScrollViews = new ArrayList<TextView>();
        Iterator<String> itr = typeNameKeyMap.keySet().iterator();
        while (itr.hasNext()) {
            String keyName = itr.next();
            TextView textView = new TextView(context);
            textView.setText(keyName);
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
            textView.setSingleLine();
            RelativeLayout.LayoutParams lpParams = new RelativeLayout.LayoutParams(
                    DipPxUtil.dip(context, 80),
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            textView.setLayoutParams(lpParams);
            textView.setBackgroundResource(R.drawable.num_button_selector);

            textView.setOnClickListener(
                    new MyKeyboardBodySimScrollClickListener(context));

            keyboardBodySimScrollContent.addView(textView);
            keyboardBodySimScrollViews.add(textView);
        }

        prePageButton = (Button) keyboardView
                .findViewById(R.id.keybtnSimPreviousPage);
        prePageButton.setTextColor(Color.DKGRAY);
        prePageButton
                .setOnClickListener(new OnPrePageButtonClickListener(context));
        nextPageButton = (Button) keyboardView
                .findViewById(R.id.keybtnSimNextPage);
        nextPageButton.setTextColor(Color.DKGRAY);
        nextPageButton
                .setOnClickListener(new OnNextPageButtonClickListener(context));

        // 刪除鍵
        keyboardView.findViewById(R.id.keybtnSimDelete)
                .setOnClickListener(new OnDeleteNumClickListener(context));
        keyboardView.findViewById(R.id.keybtnSimDelete).setOnLongClickListener(
                new OnDeleteNumLongClickListener(context));
        // 刪除右邊
        keyboardView.findViewById(R.id.keybtnSimDeleteRight)
                .setOnClickListener(new OnDeleteRightClickListener(context));
        keyboardView.findViewById(R.id.keybtnSimDeleteRight)
                .setOnLongClickListener(
                        new OnDeleteRightLongClickListener(context));
        // 符號鍵盤返回
        Button btnSimBack = (Button) keyboardView
                .findViewById(R.id.keybtnSimBack);
        btnSimBack.setTextColor(Color.DKGRAY);
        btnSimBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 界面切換
                ViewFlipper viewFlipper = (ViewFlipper) keyboardView
                        .findViewById(R.id.keyboardBodyFlipper);
                viewFlipper.showPrevious();
                viewFlipper.showPrevious();
            }
        });

        // 中文第一頁
        resetKeyboardSimPage();
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
            HorizontalScrollView sv = (HorizontalScrollView) keyboardView
                    .findViewById(R.id.keyboardBodySimScroll);
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
        Integer rowSize = getSimRowSize(simMapKeyPrefix);
        List<String> content = simMap.get(simMapKeyPrefix);
        if (null != content && !content.isEmpty()) {
            int rows = content.size() / rowSize;
            if (content.size() % rowSize != 0) {
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
        Integer rowSize = getSimRowSize(simMapKeyPrefix);

        List<String> content = simMap.get(simMapKeyPrefix);
        int start = (page - 1) * KeyboardSimIniter.SIM_PAGE_ROW * rowSize;
        int end = page * KeyboardSimIniter.SIM_PAGE_ROW * rowSize - 1;
        if (end >= content.size() - 1) {
            // 結束位置，等於或超過了一行個數
            if (page > 1 && ((end - (content.size() - 1)) >= rowSize)) {
                int emptyRow = (end - (content.size() - 1)) / rowSize;
                for (int emp = emptyRow; emp > 0; emp--) {
                    start -= rowSize;
                }
            }
            end = content.size() - 1;
        }
        return content.subList(start, end + 1);
    }

    private static void setkeyboardBodySimGridKeys(Context context,
            String simMapKey) {
        try {
            currentSimMapKey = simMapKey;
            Integer rowSize = getSimRowSize(simMapKey.split("_")[0]);
            int page = Integer.parseInt(simMapKey.split("_")[1]);
            List<String> keys = getKeyboardSimByPage(page);
            ArrayList<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
            for (int i = 0; i < keys.size(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                String cha = keys.get(i);
                map.put(KeyBoardNumAdapter.ITEM_KEY_NAME, cha);
                valueList.add(map);
            }
            KeyBoardNumAdapter keyBoardSimAdapter = new KeyBoardNumAdapter(
                    context, valueList, R.layout.keyboardsimitem, true);
            keyboardBodySimGrid.setNumColumns(rowSize);
            keyboardBodySimGrid.setAdapter(keyBoardSimAdapter);

            int totalPage = getKeyboardSimLastPage();
            prePageButton.setEnabled(!(page == 1));
            nextPageButton.setEnabled(!(page == totalPage));
        } catch (Exception e) {
        }
    }

    /** 一行幾個，下面有設置到@+id/keyboardBodySimGrid */
    private static Integer getSimRowSize(String currentSimMapKey) {
        if (PAGE_Biaoqing_KEY.equals(currentSimMapKey)) {
            return 4;
        } else {
            return 5;
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
    private static class MyKeyboardBodySimScrollClickListener
            implements View.OnClickListener {

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

            setkeyboardBodySimGridKeys(context,
                    typeNameKeyMap.get(tv.getText()) + "_" + 1);
        }
    }

    private static class OnPrePageButtonClickListener
            implements OnClickListener {

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
                    String simMapKey = currentSimMapKey.split("_")[0] + "_"
                            + topage;
                    setkeyboardBodySimGridKeys(context, simMapKey);
                }
            }
        }
    }

    private static class OnNextPageButtonClickListener
            implements OnClickListener {

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
                    String simMapKey = currentSimMapKey.split("_")[0] + "_"
                            + topage;
                    setkeyboardBodySimGridKeys(context, simMapKey);
                }
            }
        }
    }
}
