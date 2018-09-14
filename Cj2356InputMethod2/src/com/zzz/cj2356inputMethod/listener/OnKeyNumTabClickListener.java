package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;

/**
 * 數字鍵盤中的製表符點擊輸出
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年9月14日 下午11:51:47
 */
public class OnKeyNumTabClickListener implements OnClickListener {

    private Context context;

    public OnKeyNumTabClickListener(Context context2) {
        this.context = context2;
    }

    @Override
    public void onClick(View v) {
        Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
        InputConnection inputConnection = ser.getCurrentInputConnection();
        inputConnection.commitText("\t", 1);
    }

}
