package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.Toast;

/**
 * 數字鍵盤的點擊事件，按下就輸出
 * 
 * @author t
 * @time 2017-1-10下午11:31:22
 */
public class OnKeyNumTouchListener implements OnTouchListener {

    private Context context;

    public OnKeyNumTouchListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            try {
                Button button = (Button) v;
                Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
                InputConnection inputConnection = ser.getCurrentInputConnection();
                if ("Tab".equals(button.getText()) || "\\t".equals(button.getText())) {
                    inputConnection.commitText("\t", 1);
                } else {
                    inputConnection.commitText(button.getText(), 1);
                }
                SendKeyEventUtil.handleInputParenthesis(context, button.getText());
            } catch (Exception e) {
                Toast.makeText(context, "點擊數字鍵盤出錯" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

}
