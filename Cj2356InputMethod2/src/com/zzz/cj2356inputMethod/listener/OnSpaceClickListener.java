package com.zzz.cj2356inputMethod.listener;

import java.util.List;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.StringUtils;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;

/** 空格鍵事件 */
public class OnSpaceClickListener implements OnClickListener {

    private Context context;

    public OnSpaceClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.keybtnSpace) {
            // 如果原來是中文狀態，而且正在打字，提交候選第一個字，返回
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputMethodStatus stat = ser.getInputMethodStatus();
            // 如果是中文輸入
            if (stat.isShouldTranslate()) {
                if (((InputMethodStatusCn) stat).isInputingCn()) {
                    String value = "";
                    List<Item> sugs = ser.getSuggestions();
                    if (null != sugs && !sugs.isEmpty()) {
                        value = sugs.get(0).getCharacter();
                    }
                    if (StringUtils.hasText(value)) {
                        // 获得InputConnection对象
                        InputConnection inputConnection = ser
                                .getCurrentInputConnection();
                        inputConnection.commitText(value, 1);
                    }
                    ((InputMethodStatusCn) stat).setInputingCn(false);
                    return;
                }
            }

            InputConnection inputConnection = ((InputMethodService) context)
                    .getCurrentInputConnection();
            String value = " ";
            inputConnection.commitText(" ", 1);
            if (!stat.isNewCursorPositionRight() && null != value) {
                SendKeyEventUtil.doPerformLeft(inputConnection, context,
                        value.length());
            }
        }
    }

}
