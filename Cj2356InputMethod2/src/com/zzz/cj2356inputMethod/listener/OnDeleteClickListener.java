package com.zzz.cj2356inputMethod.listener;

import java.util.ArrayList;
import java.util.List;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.StringUtils;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputConnection;

/**
 * 刪除左邊
 * 
 * 
 * @author fszhouzz@qq.com
 * @time 2018年9月21日 下午11:56:00
 */
public class OnDeleteClickListener implements OnClickListener {

    private Context context;

    public OnDeleteClickListener(Context con) {
        super();
        this.context = con;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.keybtnDelete) {
            // 如果原來是中文狀態，而且正在打字
            Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
            InputConnection inputConnection = ser.getCurrentInputConnection();
            InputMethodStatus stat = ser.getInputMethodStatus();
            // 如果是中文輸入
            if (stat.isShouldTranslate()) {
                if (((InputMethodStatusCn) stat).isInputingCn()) {
                    String getInputingCnCode = ((InputMethodStatusCn) stat).getInputingCnCode();
                    // 如果當前只打了一個鍵
                    if (getInputingCnCode.length() <= 1) {
                        inputConnection.commitText("", 1);
                        ((InputMethodStatusCn) stat).setInputingCn(false);
                    } else {
                        // 如果當前只打了两個以上的鍵
                        // 將臨時輸入置成第一至倒數第二個鍵
                        String code = "";
                        String value = "";
                        for (int index = 0; index < getInputingCnCode.length() - 1; index++) {
                            Character thecode = getInputingCnCode.charAt(index);
                            code += thecode.toString();
                            value += ((InputMethodStatusCn) stat).getKeysNameMap().get(thecode.toString());
                        }

                        // 先置空，再放進去
                        ((InputMethodStatusCn) stat).inputingCnCode(null, null);
                        ((InputMethodStatusCn) stat).inputingCnCode(code, value);

                        String composingText = ((InputMethodStatusCn) stat).getComposingTextForInputConn();
                        if (StringUtils.hasText(composingText)) {
                            // 提交正在編輯的內容
                            if (Cj2356InputMethodService.SHOW_COMPOSING_TEXT_FOR_INPUT_CONN) {
                                inputConnection.setComposingText(composingText, 1);
                            }
                        }

                        // 取當前輸入編碼的候選項
                        List<Item> items = ((InputMethodStatusCn) stat).getCandidatesInfo(code, true);
                        if (items == null) {
                            // 還可以繼續鍵入，所以生成一個空的，防止以後報錯
                            items = new ArrayList<Item>();
                        }
                        ser.setSuggestions(items);
                    }
                    return;
                }
            }

            SendKeyEventUtil.doPerformDelete(context);
        }
    }

}
