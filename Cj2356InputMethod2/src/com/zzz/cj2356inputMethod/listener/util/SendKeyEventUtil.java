package com.zzz.cj2356inputMethod.listener.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;

public class SendKeyEventUtil {

    private static List<String> parenthesisList = new ArrayList<String>();

    static {
        parenthesisList.add("¿?");
        parenthesisList.add("¡!");
        parenthesisList.add("''");
        parenthesisList.add("\"\"");
        parenthesisList.add("()");
        parenthesisList.add("[]");
        parenthesisList.add("{}");
        parenthesisList.add("‘’");
        parenthesisList.add("＇＇");
        parenthesisList.add("“”");
        parenthesisList.add("＂＂");
        parenthesisList.add("〃〃");
        parenthesisList.add("❝❞");
        parenthesisList.add("（）");
        parenthesisList.add("〔〕");
        parenthesisList.add("〈〉");
        parenthesisList.add("《》");
        parenthesisList.add("［］");
        parenthesisList.add("｛｝");
        parenthesisList.add("「」");
        parenthesisList.add("『』");
        parenthesisList.add("〖〗");
        parenthesisList.add("【】");
        parenthesisList.add("«»");
        parenthesisList.add("⁽⁾");
        parenthesisList.add("₍₎");
    }

    /**
     * 如果是輸入括號，光標移入括號之間
     * 
     * @author fszhouzz@qq.com
     * @time 2018年9月21日 下午11:06:57
     * @param context
     * @param text
     */
    public static void handleInputParenthesis(Context context, CharSequence text) {
        if (null == text) {
            return;
        }
        String textStr = text.toString().replaceAll(" +", "");
        InputConnection inputConnection = (InputConnection) ((InputMethodService) context).getCurrentInputConnection();
        if (parenthesisList.contains(textStr)) {
            inputConnection.commitText(textStr, 1);
            // 加這句只是爲了，讓光標能成功進入括號中間
            ExtractedText et = inputConnection.getExtractedText(new ExtractedTextRequest(), 0);
            int s = et.selectionStart;
            int e = et.selectionEnd;
            doPerformLeft(context);
        } else {
            inputConnection.commitText(textStr, 1);
        }
    }

    /**
     * 按回車鍵
     * 
     * @author fszhouzz@qq.com
     * @time 2018年9月21日 下午10:21:43
     * @param context
     */
    public static void doPerformEnter(Context context) {
        InputConnection inputConnection = (InputConnection) ((InputMethodService) context).getCurrentInputConnection();

        long eventTime = SystemClock.uptimeMillis();
        inputConnection.sendKeyEvent(new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 0,
                0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
        inputConnection.sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), eventTime, KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_ENTER, 0, 0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
    }

    /**
     * 按刪除
     * 
     * @author fszhouzz@qq.com
     * @time 2018年9月21日 下午10:21:43
     * @param context
     */
    public static void doPerformDelete(Context context) {
        InputConnection inputConnection = (InputConnection) ((InputMethodService) context).getCurrentInputConnection();

        long eventTime = SystemClock.uptimeMillis();
        inputConnection.sendKeyEvent(new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL, 0,
                0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
        inputConnection.sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), eventTime, KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
    }

    /**
     * 刪除右邊
     * 
     * @author fszhouzz@qq.com
     * @time 2018年9月21日 下午11:52:11
     * @param context
     */
    public static void doPerformDeleteRight(Context context) {
        InputConnection inputConnection = (InputConnection) ((InputMethodService) context).getCurrentInputConnection();
        int keyCode = KeyEvent.KEYCODE_FORWARD_DEL;
        long eventTime = SystemClock.uptimeMillis();
        inputConnection.sendKeyEvent(new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, keyCode, 0, 0, 0, 0,
                KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
        inputConnection.sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), eventTime, KeyEvent.ACTION_UP, keyCode, 0,
                0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
    }

    /**
     * 左移光標
     * 
     * @author fszhouzz@qq.com
     * @time 2018年9月21日 下午10:21:34
     * @param context
     */
    public static void doPerformLeft(Context context) {
        InputConnection inputConnection = (InputConnection) ((InputMethodService) context).getCurrentInputConnection();
        int keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
        long eventTime = SystemClock.uptimeMillis();
        inputConnection.sendKeyEvent(new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, keyCode, 0, 0, 0, 0,
                KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
        inputConnection.sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), eventTime, KeyEvent.ACTION_UP, keyCode, 0,
                0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE));
    }
}
