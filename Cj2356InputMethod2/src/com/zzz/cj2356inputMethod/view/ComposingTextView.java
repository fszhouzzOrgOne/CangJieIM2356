package com.zzz.cj2356inputMethod.view;

import com.zzz.cj2356inputMethod.R;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 當前正在編輯的內容提示
 * 
 * @author 日月遞炤
 */
public class ComposingTextView extends RelativeLayout {
    private Context context = null;
    TextView tv;

    public ComposingTextView(Context context) {
        super(context);
        this.context = context;
    }

    public void setComposingText(String text) {
        this.removeAllViews();
        boolean hasContent = true;
        if (null == text || "".equals(text.trim())) {
            text = "";
            hasContent = false;
        }
        tv = new TextView(context);
        tv.setPadding(3, 0, 0, 0);
        tv.setTextColor(Color.RED);
        tv.setTextSize(15); // sp
        tv.setText(text);
        if (hasContent) {
            tv.setBackgroundColor(context.getResources().getColor(R.color.whiteccc));
        }
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_LEFT);
        addView(tv, lps);
    }

    public CharSequence getText() {
        return tv.getText();
    }
}
