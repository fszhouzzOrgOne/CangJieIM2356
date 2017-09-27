package com.zzz.cj2356inputMethod.view;

import java.util.List;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.adapter.MyBaseExpandableListAdapter;
import com.zzz.cj2356inputMethod.dto.Group;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.font.FontManager;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.mb.SettingDictMbUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.TextView;
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
    private static SearchView searView;
    private static ExpandableListView expandableListView;
    
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
        searView = (SearchView) ((Activity) context).findViewById(R.id.setTabDictSearchView);
        expandableListView = (ExpandableListView) ((Activity) context).findViewById(R.id.setTabDictExpandableListView);

        // 查詢框底字體只能這樣設置
        int id = searView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);  
        TextView textView = (TextView) searView.findViewById(id);  
        textView.setTypeface(FontManager.getTypeface(context));
        
        searView.setIconifiedByDefault(false);
        searView.setSubmitButtonEnabled(false);
        searView.setQueryHint("請輸入漢字或編碼...");
        // 查詢框事件
        searView.setOnQueryTextListener(new MySearchViewOnQueryTextListener(context));

        // 字典結果數據準備
        setgData(null);
        // 为列表设置点击事件
        expandableListView.setOnChildClickListener(new MyExpandableListViewOnChildClickListener(context));
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
    
    public static List<Group> getgData() {
        return gData;
    }
    
    public static void searchSth(String cont) {
        if (null != searView) {
            searView.setQuery(cont, true);
        }
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
        ListAdapter listAdapter = expandableListView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        // 所有子項的總高
        int totalHeight = 0;
        // listAdapter.getCount()返回數據項的數目
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, expandableListView);
            listItem.measure(0, 0); // 計算子項的寬高
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = expandableListView.getLayoutParams();
        // listView.getDividerHeight()獲取子項間分隔符佔的高度
        // expandableListView完整顯示需求的高度
        params.height = totalHeight + (expandableListView.getDividerHeight() * listAdapter.getCount());
        expandableListView.setLayoutParams(params);
    }
}

/**
 * 提交查詢
 * 
 * @author t
 * @time 2016-12-18下午12:17:49
 */
class MySearchViewOnQueryTextListener implements SearchView.OnQueryTextListener {

    private Context mContext;

    public MySearchViewOnQueryTextListener(Context c) {
        this.mContext = c;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        List<Group> gData = null;

        String pattern = "[a-z]{1,}";
        if (null != query && query.trim().length() > 0) {
            query = query.trim();
            Toast.makeText(mContext, "查詢“" + query + "”", Toast.LENGTH_SHORT).show();
            if (query.matches(pattern)) {
                gData = SettingDictMbUtils.selectDbByCode(query);
            } else {
                gData = SettingDictMbUtils.selectDbByChar(query);
            }
        } else {
            Toast.makeText(mContext, "請輸入查詢", Toast.LENGTH_SHORT).show();
        }
        SettingDictIniter.setgData(gData);
        return false;
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
        final Item item = SettingDictIniter.getgData()
                .get(groupPosition).getItems().get(childPosition);
        if (item.isEmpty()) {
            return false;
        }
        final String item1 = "查詢文字“" + item.getCharacter() + "”";
        final String item2 = "查詢編碼“" + item.getEncode() + "”";
        String item3 = "取消";
        AlertDialog.Builder builder = new Builder(mContext).setTitle("繼續查詢？");
        builder.setItems(new String[] { item1, item2, item3 }, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int index) {
                if (index == 0) {
                    SettingDictIniter.searchSth(item.getCharacter());
                } else if (index == 1) {
                    SettingDictIniter.searchSth(item.getEncode());
                } else {

                }
                arg0.dismiss();
            }
        });
        builder.show();
        return true;
    }

}