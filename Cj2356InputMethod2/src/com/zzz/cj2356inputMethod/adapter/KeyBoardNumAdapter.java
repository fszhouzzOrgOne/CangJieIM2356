package com.zzz.cj2356inputMethod.adapter;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.listener.OnKeyNumTouchListener;

/**
 * 數字鍵盤Adapter
 */
public class KeyBoardNumAdapter extends BaseAdapter {
    
    public static final String ITEM_KEY_NAME = "name";

    private Context context;
    private ArrayList<Map<String, String>> valueList;

    public KeyBoardNumAdapter(Context con, ArrayList<Map<String, String>> list) {
        this.context = con;
        this.valueList = list;
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
            convertView = View.inflate(context, R.layout.keyboardnumsimitem, null);
            viewHolder = new ViewHolderNum();
            viewHolder.btnKey = (Button) convertView
                    .findViewById(R.id.keyboardnumbtn);
            viewHolder.btnKey.setOnTouchListener(new OnKeyNumTouchListener(context));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderNum) convertView.getTag();
        }

        viewHolder.btnKey.setVisibility(View.VISIBLE);
        if ("t".equals(valueList.get(position).get("name"))) {
            viewHolder.btnKey.setText("\\t");
        } else {
            viewHolder.btnKey.setText(valueList.get(position).get("name"));
        }
        return convertView;
    }

    private static class ViewHolderNum {
        private Button btnKey;
    }

}
