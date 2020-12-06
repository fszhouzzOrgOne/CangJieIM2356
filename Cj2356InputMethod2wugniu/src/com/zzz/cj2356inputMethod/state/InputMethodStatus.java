package com.zzz.cj2356inputMethod.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.listener.util.SendKeyEventUtil;
import com.zzz.cj2356inputMethod.task.LongDeleteTask;
import com.zzz.cj2356inputMethod.utils.StringUtils;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputConnection;
import android.widget.ImageButton;

/**
 * 當前輸入狀態
 * 
 * @author t
 * @time 2017-1-7下午9:54:20
 */
public abstract class InputMethodStatus {

    /** 本種類內的下個輸入法 */
    private InputMethodStatus nextStatus;
    /** 所在種類下一個輸入法，爲下一個種類的第一個輸入法 */
    private InputMethodStatus nextStatusType;

    protected Context context;

    private String type;
    private String typeName;
    private String subType;
    private String subTypeName;

    protected InputMethodStatus(Context con) {
        this.context = con;
    }

    /**
     * 輸入法是否要翻譯
     * 
     * @author fszhouzz@qq.com
     * @time 2017年10月31日下午12:58:12
     * @return
     */
    public abstract boolean isShouldTranslate();

    /**
     * 光標是否在輸入字符右邊
     * 
     * @author fszhouzz@qq.com
     * @time 2019年10月17日 上午12:22:09
     * @return
     */
    public boolean isNewCursorPositionRight() {
        return true;
    }

    /**
     * 得到輸入法名字
     */
    public abstract String getInputMethodName();

    /**
     * 得到下一個狀態
     */
    public final InputMethodStatus getNextStatus() {
        return this.nextStatus;
    }

    /**
     * 得到下一個輸入類別的默認輸入狀態
     */
    public final InputMethodStatus getNextStatusType() {
        return this.nextStatusType;
    }

    /**
     * 26個英文鍵，各鍵對應顯示的名字
     * 
     * @author fszhouzz@qq.com
     * @time 2018年7月19日 下午10:55:32
     * @return
     */
    protected Map<String, Object> getKeysNameMap() {
        String letters1 = "abcdefghijklmnopqrstuvwxyz";
        String letters2 = letters1.toUpperCase();
        Map<String, Object> mbTransMap = new HashMap<String, Object>();
        int index = 0;
        while (index <= letters1.length() - 1) {
            mbTransMap.put(letters1.substring(index, index + 1),
                    letters2.substring(index, index + 1));
            index++;
        }
        return mbTransMap;
    }

    /**
     * 得到鍵對應的鍵名，用於展示
     * 
     * @author fszhouzz@qq.com
     * @time 2018年11月10日 下午10:35:37
     * @param key
     * @return
     */
    public String getKeyName(String key) {
        return getKeyValue(key);
    }

    /**
     * 得到鍵對應的值
     * 
     * @author fszhouzz@qq.com
     * @time 2018年11月10日 下午10:35:37
     * @param key
     * @return
     */
    public String getKeyValue(String key) {
        Map<String, Object> map = getKeysNameMap();
        return (String) map.get(key);
    }

    /**
     * 根據鍵名找到英文鍵位
     * 
     * @author t
     * @time 2017-1-7下午10:54:41
     */
    public String getKeyByValue(String val) {
        Map<String, Object> map = getKeysNameMap();
        if (null != map) {
            for (String key : map.keySet()) {
                if (map.get(key).equals(val)) {
                    return key;
                }
            }
        }
        return null;
    }

    /**
     * 逗號鍵展示字符
     * 
     * @author fszhouzz@qq.com
     * @time 2018年11月10日 下午10:56:48
     * @return
     */
    public String getCommaBtnText() {
        return ",";
    }

    /**
     * 句號鍵展示字符
     * 
     * @author fszhouzz@qq.com
     * @time 2018年11月10日 下午10:57:03
     * @return
     */
    public String getPeriodBtnText() {
        return ".";
    }

    /**
     * 輸入法切換鍵展示字符
     * 
     * @author fszhouzz@qq.com
     * @time 2018年11月10日 下午10:57:03
     * @return
     */
    public abstract String getShiftBtnText();

    /**
     * 設置各鍵的背景
     * 
     * @author fszhouzz@qq.com
     * @time 2018年11月10日 下午11:28:23
     * @param letterViews
     *            鍵按鈕
     * @param letterViewsBgIds
     *            英文小寫背景id列表
     */
    public void setKeysBackground(List<View> letterViews,
            List<Integer> letterViewsBgIds) {
        for (View v : letterViews) {
            v.setBackgroundResource(R.drawable.keyboard_button_selector);
        }
    }

    /**
     * 設置主鍵盤上刪除鍵的背景
     * 
     * @author fszhouzz@qq.com
     * @time 2019年10月17日 下午11:24:44
     * @param findViewById
     */
    public void setMainDeleteBackground(View deleteBtn) {
        // 默認已有，不必再設
        if (deleteBtn instanceof ImageButton) {
            ImageButton ib = (ImageButton) deleteBtn;
            // ib.setBackgroundResource(R.drawable.keyboard_button_selector);
            ib.setImageDrawable(
                    context.getResources().getDrawable(R.drawable.icon_delete));
        }
    }

    /**
     * 主鍵盤按鍵動作，默認輸出btnText
     * 
     * @author fszhouzz@qq.com
     * @time 2019年10月17日 下午10:03:50
     * @param btnText
     *            按鈕上文字
     * @param keyPrsd
     *            當前是26英文字母的哪個
     * @return 如果有動作，就返回true
     */
    public boolean mainKeyTouchAction(String btnText, String keyPrsd) {
        if ((context instanceof InputMethodService)
                && (StringUtils.hasText(btnText))) {
            // commitText方法第2个参数值为1，表示在当前位置插入文本
            InputMethodService ser = ((InputMethodService) context);
            InputConnection inputConnection = ser.getCurrentInputConnection();
            inputConnection.commitText(btnText, 1);
            return true;
        }
        return false;
    }

    /**
     * 主鍵盤的空格鍵動作
     * 
     * @author fszhouzz@qq.com
     * @time 2019年10月17日 下午10:19:38
     * @return 如果有動作，就返回true
     */
    public boolean mainSpaceClickAction() {
        if (context instanceof InputMethodService) {
            InputConnection inputConnection = ((InputMethodService) context)
                    .getCurrentInputConnection();
            String value = " ";
            inputConnection.commitText(value, 1);
            return true;
        }
        return false;
    }

    /**
     * 主鍵盤的刪除鍵動作
     * 
     * @author fszhouzz@qq.com
     * @time 2019年10月17日 下午10:19:38
     * @return 如果有動作，就返回true
     */
    public boolean mainDeleteClickAction() {
        if (context instanceof InputMethodService) {
            SendKeyEventUtil.doPerformDelete(context);
            return true;
        }
        return false;
    }

    /**
     * 主鍵盤的長刪除鍵動作
     * 
     * @author fszhouzz@qq.com
     * @time 2019年10月17日 下午10:19:38
     * @return 如果有動作，就返回true
     */
    public boolean mainDeleteLongClickAction(View v) {
        if (context instanceof InputMethodService) {
            // 定時刪除
            final Timer timer = new Timer();
            timer.schedule(new LongDeleteTask(context), 500, 50);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public String toString() {
        return "InputMethodStatus [type=" + type + ", typeName=" + typeName
                + ", subType=" + subType + ", subTypeName=" + subTypeName + "]";
    }

    public void setNextStatus(InputMethodStatus nextStatus) {
        this.nextStatus = nextStatus;
    }

    public void setNextStatusType(InputMethodStatus nextStatusType) {
        this.nextStatusType = nextStatusType;
    }
}
