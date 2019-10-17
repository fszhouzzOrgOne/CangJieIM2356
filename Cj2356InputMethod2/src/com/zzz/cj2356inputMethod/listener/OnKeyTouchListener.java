package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class OnKeyTouchListener implements OnTouchListener {

    private String letters = "abcdefghijklmnopqrstuvwxyz";

    private Context context;
    private String key;

    public OnKeyTouchListener(Context con) {
        super();
        this.context = con;
    }

    public OnKeyTouchListener(Context con, int index) {
        super();
        this.context = con;
        this.key = letters.charAt(index) + "";
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Button button = (Button) v;
                Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
                InputMethodStatus stat = ser.getInputMethodStatus();

                String text = null == button.getText() ? null
                        : button.getText().toString();
                stat.mainKeyTouchAction(text, key);
            }
        } catch (Exception e) {
            String msg = "查詢候選失敗：" + e.toString() + ".\n";
            for (StackTraceElement el : e.getStackTrace()) {
                msg += el + "\n";
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
