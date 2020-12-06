package com.zzz.cj2356inputMethod.adapter;

import java.util.ArrayList;
import java.util.Map;

import com.zzz.cj2356inputMethodWugniu.R;
import com.zzz.cj2356inputMethod.listener.OnKeyNumTouchListener;
import com.zzz.cj2356inputMethod.view.KeyBoardNumItemButton;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 數字鍵盤Adapter
 */
public class KeyBoardNumAdapter extends BaseAdapter {

    public static final String ITEM_KEY_NAME = "name";

    private Context context;
    private ArrayList<Map<String, String>> valueList;
    private int itemViewId; // 表格中按钮的主鍵
    private boolean showUnicode; // 是否展示統一碼

    /**
     * 
     * @param con
     * @param list
     * @param itemViewId
     *            表格中按钮在xml中的主鍵
     * @param showUnicode
     *            是否展示統一碼
     */
    public KeyBoardNumAdapter(Context con, ArrayList<Map<String, String>> list, int itemViewId, boolean showUnicode) {
        this.context = con;
        this.valueList = list;
        this.itemViewId = itemViewId;
        this.showUnicode = showUnicode;
    }

    @Override
    public int getCount() {
        return valueList.size();
    }

    @Override
    public Object getItem(int position) {
        return valueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderNum viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, itemViewId, null);
            viewHolder = new ViewHolderNum();
            viewHolder.btnKey = (KeyBoardNumItemButton) convertView.findViewById(R.id.keyboardgridbtn);
            viewHolder.btnKey.setOnTouchListener(new OnKeyNumTouchListener(context));
            viewHolder.btnKey.setShowUnicode(showUnicode);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderNum) convertView.getTag();
        }

        viewHolder.btnKey.setVisibility(View.VISIBLE);
        if ("	".equals(valueList.get(position).get(ITEM_KEY_NAME))) {
            viewHolder.btnKey.setText("\\t");
        } else {
            viewHolder.btnKey.setText(valueList.get(position).get(ITEM_KEY_NAME));
        }
        return convertView;
    }

    private static class ViewHolderNum {
        private KeyBoardNumItemButton btnKey;
    }

}
