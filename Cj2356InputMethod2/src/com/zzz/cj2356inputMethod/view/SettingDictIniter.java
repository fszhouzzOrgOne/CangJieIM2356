package com.zzz.cj2356inputMethod.view;

import java.util.ArrayList;
import java.util.List;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.adapter.MyBaseExpandableListAdapter;
import com.zzz.cj2356inputMethod.dto.Group;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.mb.SettingDictMbUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
        SearchView searView = (SearchView) ((Activity) context).findViewById(R.id.setTabDictSearchView);
        ExpandableListView expandableListView = (ExpandableListView) ((Activity) context)
                .findViewById(R.id.setTabDictExpandableListView);

        searView.setIconifiedByDefault(false);
        searView.setSubmitButtonEnabled(false);
        searView.setQueryHint("請輸入漢字或編碼...");
        // 查詢框事件
        searView.setOnQueryTextListener(new MySearchViewOnQueryTextListener(context));

        // 數據準備
        List<Group> gData = SettingDictMbUtils.initGroupDatas();
        MyBaseExpandableListAdapter myAdapter = new MyBaseExpandableListAdapter(gData, context);
        expandableListView.setAdapter(myAdapter);
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
        ArrayList<Group> gData = null;

        String pattern = "[a-z]{1,}";
        if (null != query && query.trim().length() > 0) {
            Toast.makeText(mContext, "輸入查詢：" + query, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "請輸入查詢", Toast.LENGTH_SHORT).show();
        }
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
        final Item item = Item.emptyItem; // TODO
        if (item.isEmpty()) {
            return false;
        }
        final String item1 = "查詢“" + item.getCharacter() + "”";
        final String item2 = "查詢“" + item.getEncode() + "”";
        String item3 = "取消";
        AlertDialog.Builder builder = new Builder(mContext).setTitle("繼續查詢？");
        builder.setItems(new String[] { item1, item2, item3 }, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int index) {
                if (index == 0) {
                } else if (index == 1) {
                } else {

                }
                arg0.dismiss();
            }
        });
        builder.show();
        return true;
    }

}