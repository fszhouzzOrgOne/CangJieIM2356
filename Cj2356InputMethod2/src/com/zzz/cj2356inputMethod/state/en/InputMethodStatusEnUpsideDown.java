package com.zzz.cj2356inputMethod.state.en;

import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;
import com.zzz.cj2356inputMethod.task.LongDeleteRightTask;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputConnection;
import android.widget.ImageButton;

public class InputMethodStatusEnUpsideDown extends InputMethodStatusEn {

    public static final String SUBTYPE_CODE = "ɐq";
    public static final String SUBTYPE_NAME = "英文反轉";

    public InputMethodStatusEnUpsideDown(Context con) {
        super(con);
        this.setSubType(SUBTYPE_CODE);
        this.setSubTypeName(SUBTYPE_NAME);
    }

    @Override
    public void setKeysBackground(List<View> letterViews,
            List<Integer> letterViewsBgIds) {
        for (int i = 0; i < letterViews.size(); i++) {
            View v = letterViews.get(i);
            v.setBackgroundResource(letterViewsBgIds.get(i));
        }
    }

    @Override
    public String getInputMethodName() {
        return SUBTYPE_NAME;
    }

    @Override
    public Map<String, Object> getKeysNameMap() {
        String letters1 = "abcdefghijklmnopqrstuvwxyz";
        String letters2 = " ɐ q ɔ p ə ɟ ɓ ɥ ᴉ ſ̣ ʞ ɿ ɯ u o d b ɹ s ʇ n ʌ ʍ x ʎ z ";
        Map<String, Object> mbTransMap = super.getKeysNameMap();
        int index = 0;
        for (String one : letters2.trim().split(" +")) {
            mbTransMap.put(letters1.substring(index, index + 1), one);
            index++;
        }
        return mbTransMap;
    }

    @Override
    public boolean isNewCursorPositionRight() {
        return false;
    }

    @Override
    public String getCommaBtnText() {
        return "‘";
    }

    @Override
    public String getPeriodBtnText() {
        return "˙";
    }

    @Override
    public boolean mainKeyTouchAction(String btnText, String keyPrsd) {
        if (super.mainKeyTouchAction(btnText, keyPrsd)) {
            if (!isNewCursorPositionRight() && null != btnText) {
                InputMethodService ser = ((InputMethodService) context);
                InputConnection inputConnection = ser
                        .getCurrentInputConnection();
                SendKeyEventUtil.doPerformLeft(inputConnection, context, 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mainSpaceClickAction() {
        if (super.mainSpaceClickAction()) {
            InputConnection inputConnection = ((InputMethodService) context)
                    .getCurrentInputConnection();
            if (!isNewCursorPositionRight()) {
                SendKeyEventUtil.doPerformLeft(inputConnection, context, 1);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setMainDeleteBackground(View deleteBtn) {
        if (deleteBtn instanceof ImageButton) {
            ImageButton ib = (ImageButton) deleteBtn;
            ib.setBackgroundDrawable(context.getResources()
                    .getDrawable(R.drawable.icon_delete_right));
        }
    }

    @Override
    public boolean mainDeleteClickAction() {
        if (context instanceof InputMethodService) {
            SendKeyEventUtil.doPerformDeleteRight(context);
            return true;
        }
        return false;
    }

    @Override
    public boolean mainDeleteLongClickAction(View v) {
        if (context instanceof InputMethodService) {
            // 定時刪除
            final Timer timer = new Timer();
            timer.schedule(new LongDeleteRightTask(context), 500, 50);
            // 擡手就不再刪除
            v.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 抬起操作
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        timer.cancel();
                    }
                    return false;
                }

            });
            return true;
        }
        return false;
    }
}
