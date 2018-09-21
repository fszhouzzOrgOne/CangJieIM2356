package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;

/**
 * 數字鍵盤中的滑動符號點擊輸出
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年9月14日 下午11:51:47
 */
public class OnKeyNumSimItemClickListener implements OnClickListener {

    private Context context;

    public OnKeyNumSimItemClickListener(Context context2) {
        this.context = context2;
    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) v;
        Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
        InputConnection inputConnection = ser.getCurrentInputConnection();

        CharSequence text = tv.getText();
        inputConnection.commitText(text, 1);
        SendKeyEventUtil.handleInputParenthesis(context, text);
    }

}
