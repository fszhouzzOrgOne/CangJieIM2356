package com.zzz.cj2356inputMethod.listener;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;

/** 候選欄㸃擊 */
public class OnCandidateItemClickListener implements OnClickListener {

    private Context context;

    public OnCandidateItemClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) v;
        tv.setBackgroundColor(Color.parseColor("#666666"));
        String text = tv.getText().toString();
        Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
        InputConnection inputConnection = ser.getCurrentInputConnection();
        InputMethodStatus stat = ser.getInputMethodStatus();
        // 不再輸入
        ((InputMethodStatusCn) stat).setInputingCn(false);
        // 提交
        inputConnection.commitText(text, 1);
    }

}
