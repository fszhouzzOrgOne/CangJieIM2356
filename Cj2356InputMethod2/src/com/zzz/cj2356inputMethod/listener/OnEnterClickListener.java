package com.zzz.cj2356inputMethod.listener;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.StringUtils;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/**
 * 回車鍵按下，發佈事件
 * 
 * @author t
 * @time 2017-1-7下午7:32:05
 */
public class OnEnterClickListener implements OnClickListener {

    private Context context;

    public OnEnterClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.keybtnEnter) {
            // 如果原來是中文狀態，而且正在打字，提交鍵名串，就返回
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputMethodStatus stat = ser.getInputMethodStatus();
            // 如果是中文輸入
            if (stat.isShouldTranslate()) {
                if (((InputMethodStatusCn) stat).isInputingCn()) {
                    String value = ((InputMethodStatusCn) stat).getInputingCnValueForEnter();
                    if (StringUtils.hasText(value)) {
                        String patternAbc123 = "^[a-zA-Z]+[0-9]?$";
                        if (value.matches(patternAbc123)) {
                            value = value.toLowerCase();
                        }
                        // 获得InputConnection对象
                        InputConnection inputConnection = ser.getCurrentInputConnection();
                        inputConnection.commitText(value, 1);
                    }
                    ((InputMethodStatusCn) stat).setInputingCn(false);
                    return;
                }
            }

            doEnterKey(context);
        } else if (v.getId() == R.id.keybtnNumEnter) {
            doEnterKey(context);
        }
    }

    public static void doEnterKey(Context context) {
        InputConnection inputConnection = (InputConnection) ((InputMethodService) context).getCurrentInputConnection();
        EditorInfo info = ((InputMethodService) context).getCurrentInputEditorInfo();

        int action = EditorInfo.IME_ACTION_UNSPECIFIED;
        // 防止有前進導航時，不搜索了
        // 防止有時該搜索時也不搜索了
        boolean isNavigateNext = (info.imeOptions
                & EditorInfo.IME_FLAG_NAVIGATE_NEXT) == EditorInfo.IME_FLAG_NAVIGATE_NEXT;
        boolean isSearch = (info.imeOptions & EditorInfo.IME_ACTION_SEARCH) == EditorInfo.IME_ACTION_SEARCH;
        if (isNavigateNext || isSearch) {
            action = info.imeOptions & EditorInfo.IME_MASK_ACTION;
        }

        // 是否不執行提交
        boolean isNoEnter = (info.imeOptions
                & EditorInfo.IME_FLAG_NO_ENTER_ACTION) == EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        if (isNoEnter) {
            SendKeyEventUtil.doPerformEnter(context);
        } else {
            inputConnection.performEditorAction(action);
        }
    }

}
