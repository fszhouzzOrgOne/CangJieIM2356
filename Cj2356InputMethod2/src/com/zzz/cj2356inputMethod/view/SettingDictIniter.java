package com.zzz.cj2356inputMethod.view;

import java.util.ArrayList;
import java.util.List;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.adapter.MyBaseExpandableListAdapter;
import com.zzz.cj2356inputMethod.dto.Group;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.font.FontManager;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.mb.SettingDictMbUtils;
import com.zzz.cj2356inputMethod.utils.UnicodeConvertUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 桌面圖標打開頁面：倉頡字典
 * 
 * @author fsz
 * @time 2017年9月26日下午5:38:10
 */
public class SettingDictIniter {
    private static Context context;

    private static LinearLayout setDictLayout;
    private static ExpandableListView expandableListView;
    private static EditText editText;
    private static Button editTextBtn;

    private static List<Group> gData;

    /**
     * 倉頡字典初始化
     * 
     * @author fsz
     * @time 2017年9月26日下午5:39:37
     * @param con
     */
    public static void initSettingDict(Context con) {
        context = con;
        // 初始化詞典數據
        // InputMethodService和Activity是不同的環境，這裡要褈新設置。不要管並發訪問數據庫文件。
        MbUtils.init(context);
        SettingDictMbUtils.init(context);

        setDictLayout = (LinearLayout) ((Activity) context).findViewById(R.id.setTabDictSearchLayout);
        expandableListView = (ExpandableListView) ((Activity) context).findViewById(R.id.setTabDictExpandableListView);

        editText = (EditText) ((Activity) context).findViewById(R.id.setTabDictEditText);
        editText.setTypeface(FontManager.getTypeface(context));
        editText.setOnKeyListener(new MyEditTextKeyListener(context));

        editTextBtn = (Button) ((Activity) context).findViewById(R.id.setTabDictEditTextBtn);
        editTextBtn.setTextColor(Color.DKGRAY);
        editTextBtn.setOnClickListener(new EditTextBtnOnClickListener(context));

        // 字典結果數據準備
        setgData(null);
        // 为列表设置点击事件
        expandableListView.setOnChildClickListener(new MyExpandableListViewOnChildClickListener(context));
        // 展開、關閉都重新算高度
        expandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int paramInt) {
                setListViewHeightBasedOnChildren(expandableListView);
            }
        });
        expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int paramInt) {
                setListViewHeightBasedOnChildren(expandableListView);
            }
        });
    }

    /** 隱藏倉頡字典 */
    public static void hideSettingVLog() {
        if (null != setDictLayout) {
            setDictLayout.setVisibility(View.GONE);
        }
    }

    /** 顯示倉頡字典 */
    public static void showSettingVLog() {
        if (null != setDictLayout) {
            setDictLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 設置查詢結果數據
     * 
     * @author fsz
     * @time 2017年9月27日上午9:44:04
     * @param gData
     */
    public static void setgData(List<Group> gData) {
        if (null == gData || gData.isEmpty()) {
            gData = SettingDictMbUtils.initGroupDatas();
        }
        tryInitUnicodeGroup(gData);
        SettingDictIniter.gData = gData;
        MyBaseExpandableListAdapter myAdapter = new MyBaseExpandableListAdapter(gData, context);
        expandableListView.setAdapter(myAdapter);
        setListViewHeightBasedOnChildren(expandableListView);
        // 隱藏輸入法
        expandableListView.requestFocus();
        // 默認展開
        for (int i = 0; i < myAdapter.getGroupCount(); i++) {
            if (gData.get(i).getItems().get(0).isEmpty() == false) {
                expandableListView.expandGroup(i);
            }
        }
    }

    /**
     * 構造一個統一碼分組
     * 
     * @author fszhouzz@qq.com
     * @time 2018年10月20日 上午11:40:29
     * @param groups
     *            肯定不爲Null
     */
    private static void tryInitUnicodeGroup(List<Group> groups) {
        Group gu = Group.unicodeGroup.clone();
        if (null != editText && null != editText.getText()) {
            String query = editText.getText().toString().trim();
            if (query.length() > 0) {
                try {
                    String pattern = "[0-9a-fA-F]+";
                    List<Item> items = new ArrayList<Item>();
                    if (query.matches(pattern)) {
                        Item it = Item.unicodeItem.clone();
                        String cha = UnicodeConvertUtil.getStringByUnicode(Integer.parseInt(query, 16));
                        it.setCharacter(cha);
                        it.setEncode(query);
                        items.add(it);
                    } else {
                        List<String> codes = UnicodeConvertUtil.getUnicodeStr4ListFromStr(query);
                        if (null != codes && !codes.isEmpty()) {
                            for (String code : codes) {
                                Item it = Item.unicodeItem.clone();
                                String cha = UnicodeConvertUtil.getStringByUnicode(Integer.parseInt(code, 16));
                                it.setCharacter(cha);
                                it.setEncode(code);
                                items.add(it);
                            }
                        }
                    }
                    if (!items.isEmpty()) {
                        gu.setItems(items);
                    }
                } catch (Exception e) {
                }
            } // end query.length()
        } // end editText
        groups.add(gu);
    }

    public static List<Group> getgData() {
        return gData;
    }

    public static void searchSth(String cont) {
        editText.setText(cont);
        editTextBtn.performClick();
    }

    /**
     * 查詢結果的展示問題：<br/>
     * ExpandableListView在ScrollView等可以上下滑動的組件中，就只會展示一行<br/>
     * 因爲是只按ExpandableListView最初指定的高度作的展示，不是按實際高度<br/>
     * 這裡在setAdapter(myAdapter)之後，都重新計算下，再展示就正常了。<br />
     * 
     * @see http://blog.csdn.net/yaya_soft/article/details/25796453
     * @author fsz
     * @time 2017年9月27日下午4:39:04
     * @param expandableListView
     */
    public static void setListViewHeightBasedOnChildren(ExpandableListView expandableListView) {
        BaseExpandableListAdapter adapter = (BaseExpandableListAdapter) expandableListView.getExpandableListAdapter();
        if (adapter == null) {
            return;
        }

        int listCount = 0; // 分組和子項底總和
        // 所有子項的總高
        int totalHeight = 0;
        int groupCnt = adapter.getGroupCount(); // 返回分組底數目
        listCount += groupCnt;
        for (int i = 0, len = groupCnt; i < len; i++) {
            View listItem = adapter.getGroupView(i, false, null, expandableListView);
            listItem.measure(0, 0); // 計算子項的寬高
            totalHeight += listItem.getMeasuredHeight();

            if (expandableListView.isGroupExpanded(i)) {
                int childCount = adapter.getChildrenCount(i);
                listCount += childCount;
                for (int j = 0; j < childCount; j++) {
                    View childView = adapter.getChildView(i, j, false, null, expandableListView);
                    childView.measure(0, 0); // 計算子項的寬高
                    totalHeight += childView.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = expandableListView.getLayoutParams();
        // listView.getDividerHeight()獲取子項間分隔符佔的高度
        // expandableListView完整顯示需求的高度
        params.height = totalHeight + (expandableListView.getDividerHeight() * (listCount - 1));
        expandableListView.setLayoutParams(params);
    }

    /**
     * 查詢文本框中輸入值
     * 
     * @author fszhouzz@qq.com
     * @time 2017年10月31日下午5:01:15
     */
    private static class EditTextBtnOnClickListener implements View.OnClickListener {
        private Context context;

        public EditTextBtnOnClickListener(Context con) {
            this.context = con;
        }

        @Override
        public void onClick(View v) {
            List<Group> gData = null;
            if (null != editText && null != editText.getText()) {
                String query = editText.getText().toString().trim();
                if (query.length() > 0) {
                    String pattern = "[a-zA-Z]{1,}";
                    Toast.makeText(context, "查詢“" + query + "”", Toast.LENGTH_SHORT).show();
                    try {
                        if (query.matches(pattern)) {
                            query = query.toLowerCase();
                            gData = SettingDictMbUtils.selectDbByCode(query);
                        } else {
                            gData = SettingDictMbUtils.selectDbByChar(query);
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "查詢失敗：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    editText.setText("");
                    editText.requestFocus();
                    Toast.makeText(context, "請輸入查詢", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "請輸入查詢", Toast.LENGTH_SHORT).show();
            }
            SettingDictIniter.setgData(gData);
        }
    }

    /**
     * 輸入後回車
     * 
     * @author 日月遞炤
     * @time 2017年11月1日 下午9:23:33
     */
    private static class MyEditTextKeyListener implements View.OnKeyListener {
        @SuppressWarnings("unused")
        private Context context;

        public MyEditTextKeyListener(Context con) {
            this.context = con;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                // 㸃下查詢按鈕
                editTextBtn.performClick();
                return true;
            }
            return false;
        }
    }
}

/**
 * 點擊了列表項
 * 
 * @author t
 * @time 2016-12-18上午10:11:24
 */
class MyExpandableListViewOnChildClickListener implements ExpandableListView.OnChildClickListener {

    private Context mContext;

    public MyExpandableListViewOnChildClickListener(Context c) {
        this.mContext = c;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        final Item item = SettingDictIniter.getgData().get(groupPosition).getItems().get(childPosition);
        if (item.isEmpty()) {
            return false;
        }
        // 統一碼
        List<String> strUnics = UnicodeConvertUtil.getUnicodeStr4ListFromStr(item.getCharacter());
        final String unicode = (null == strUnics || strUnics.isEmpty()) ? null : strUnics.get(0);

        final String item1 = "查詢文字“" + item.getCharacter() + "”";
        final String item2 = "查詢編碼“" + item.getEncode() + "”";
        final String item3 = "複製文字“" + item.getCharacter() + "”";
        final String item4 = "複製編碼“" + item.getEncode() + "”";
        final String item5 = "複製文字和編碼“" + item.getCharacter() + " " + item.getEncode() + "”";
        final String item6 = "複製統一碼“" + unicode + "”";
        String itemCncl = "取消";
        AlertDialog.Builder builder = new Builder(mContext).setTitle("繼續查詢？");
        CharSequence[] itemsTemp = new String[] { item1, item2, item3, item4, item5, itemCncl };
        if (!item.isUnicodeItem() && null != unicode) {
            itemsTemp = new String[] { item1, item2, item3, item4, item5, item6, itemCncl };
        }
        final CharSequence[] items = itemsTemp;
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int index) {
                if (index == 0) {
                    SettingDictIniter.searchSth(item.getCharacter());
                } else if (index == 1) {
                    if (item.isUnicodeItem()) {
                        Toast.makeText(mContext, "不支持操作。", Toast.LENGTH_LONG).show();
                        return;
                    }
                    SettingDictIniter.searchSth(item.getEncode());
                } else if (index == 2) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(item.getCharacter());
                    Toast.makeText(mContext, "複製文字成功。", Toast.LENGTH_LONG).show();
                } else if (index == 3) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(item.getEncode());
                    Toast.makeText(mContext, "複製編碼成功。", Toast.LENGTH_LONG).show();
                } else if (index == 4) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(item.getCharacter() + " " + item.getEncode());
                    Toast.makeText(mContext, "複製成功。", Toast.LENGTH_LONG).show();
                } else if (index == 5 && items.length > 6) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(unicode);
                    Toast.makeText(mContext, "複製成功。", Toast.LENGTH_LONG).show();
                }
                arg0.dismiss();
            }
        });
        builder.show();
        return true;
    }

}