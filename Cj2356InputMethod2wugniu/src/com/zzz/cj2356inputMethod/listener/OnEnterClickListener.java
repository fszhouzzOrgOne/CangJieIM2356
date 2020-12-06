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
        } else if (v.getId() == R.id.keybtnNum40Enter) {
            doEnterKey(context);
        }
    }

    public static void doEnterKey(Context context) {
        if (!sendDefaultEditorAction(true, context)) {
            SendKeyEventUtil.doPerformEnter(context);
        }
    }

    /**
     * https://blog.csdn.net/jianguo_liao19840726/article/details/25282259<br/>
     * 解決交通銀行，忘記密碼頁輸入了號碼後，按回車不隱藏輸入法的問題<br/>
     * 各軟件搜索欄也使用正常
     * 
     * @author fszhouzz@qq.com
     * @time 2018年10月24日 下午11:45:28
     * @param fromEnterKey
     * @param context
     * @return
     */
    public static boolean sendDefaultEditorAction(boolean fromEnterKey, Context context) {
        EditorInfo ei = ((InputMethodService) context).getCurrentInputEditorInfo();
        if (ei != null && (!fromEnterKey || (ei.imeOptions & EditorInfo.IME_FLAG_NO_ENTER_ACTION) == 0)
                && (ei.imeOptions & EditorInfo.IME_MASK_ACTION) != EditorInfo.IME_ACTION_NONE) {
            // If the enter key was pressed, and the editor has a default
            // action associated with pressing enter, then send it that
            // explicit action instead of the key event.
            InputConnection ic = (InputConnection) ((InputMethodService) context).getCurrentInputConnection();
            if (ic != null) {
                ic.performEditorAction(ei.imeOptions & EditorInfo.IME_MASK_ACTION);
            }
            return true;
        }
        return false;
    }

}
