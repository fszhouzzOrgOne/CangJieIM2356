package com.zzz.cj2356inputMethod.state.trans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.utils.StringUtils;

import android.content.Context;
import android.view.inputmethod.InputConnection;

/**
 * 要翻譯的輸入法
 */
public abstract class InputMethodStatusCn extends InputMethodStatus {

    private boolean isInputingCn = false; // 用户是否正在打中文中
    // 中文臨時輸入的內容
    private Map<String, String> inputTempCn = new HashMap<String, String>();

    InputMethodStatusCn(Context con) {
        super(con);
    }

    @Override
    public boolean isShouldTranslate() {
        return true;
    }

    @Override
    public String getCommaBtnText() {
        return "，";
    }

    @Override
    public String getPeriodBtnText() {
        return "。";
    }

    @Override
    public String getShiftBtnText() {
        return this.getSubTypeName();
    }

    /**
     * 得到候選信息
     * 
     * @param code
     *            當前輸入
     * @param extraResolve
     *            是否解析結果，如加入時間等
     */
    public abstract List<Item> getCandidatesInfo(String code,
            boolean extraResolve);

    /**
     * 按字查詢輸入法的编码
     * 
     * @param cha
     *            文字
     * @author fsz
     * @time 2017年9月27日下午5:01:18
     * @return
     */
    public abstract List<Item> getCandidatesInfoByChar(String cha);

    /**
     * 是否還可以繼續鍵入
     */
    public abstract boolean couldContinueInputing(String code);

    /**
     * 根据英文鍵位，得到鍵名串
     */
    public String translateCode2Name(String str) {
        String result = null;
        if (StringUtils.hasText(str)) {
            for (int index = 0; index < str.length(); index++) {
                Character c = str.charAt(index);
                if (null == result) {
                    result = "";
                }
                result += getKeyValue(c.toString());
            }
        }
        return result;
    }

    /**
     * 正在輸入中文，保存臨時輸入
     */
    public String inputingCnCode(String key, String value) {
        String resultValue = "";
        // 什麼都沒有傳進來，就不是臨時輸入，是最後的提交
        if (!StringUtils.hasText(key) || !StringUtils.hasText(value)) {
            setInputingCn(false);
        } else {
            setInputingCn(true);

            if (null == inputTempCn.get("code")) {
                inputTempCn.put("code", "");
            }
            inputTempCn.put("code", inputTempCn.get("code") + key);

            if (null == inputTempCn.get("value")) {
                inputTempCn.put("value", "");
            }
            inputTempCn.put("value", inputTempCn.get("value") + value);

            resultValue = inputTempCn.get("value");
        }

        // 保持提示框中的顯示一致
        ((Cj2356InputMethodService) this.getContext())
                .setComposingText(getInputingCnCode());
        return resultValue;
    }

    /**
     * 得到臨時輸入的所有鍵名
     */
    public String getInputingCnValue() {
        String result = "";
        if (null != inputTempCn
                && StringUtils.hasText(inputTempCn.get("value"))) {
            result = inputTempCn.get("value");
        }
        return result;
    }

    /**
     * 得到臨時輸入的所有鍵名，用於回車鍵輸入編碼
     */
    public String getInputingCnValueForEnter() {
        return getComposingTextForInputConn();
    }

    /**
     * 得到臨時輸入的所有英文碼位
     */
    public String getInputingCnCode() {
        String result = "";
        if (null != inputTempCn
                && StringUtils.hasText(inputTempCn.get("code"))) {
            result = inputTempCn.get("code");
        }
        return result;
    }

    /**
     * 用於inputConnection.setComposingText(composingText, 1);
     * 
     * @author fszhouzz@qq.com
     * @time 2018年10月14日 下午8:53:22
     * @return
     */
    public String getComposingTextForInputConn() {
        String code = getInputingCnCode();
        String composingText = translateCode2Name(code);
        String patternAbc123 = "^[a-zA-Z]+[0-9]?$";
        if (composingText.matches(patternAbc123)) {
            composingText = composingText.toLowerCase();
        } else {
            composingText = code.toLowerCase();
        }
        return composingText;
    }

    /**
     * 候選框上的輸入法提示
     * 
     * @author fszhouzz@qq.com
     * @time 2018年11月3日 上午12:42:51
     * @return
     */
    public String getComposingTextForCandidateView() {
        String code = getInputingCnCode();
        String composing = translateCode2Name(code);
        String patternAbc123 = "^[a-zA-Z0-9]+$";
        if (composing.matches(patternAbc123)) {
            composing = composing.toLowerCase();
            if (!composing.equals(code)) {
                composing += "（" + code + "）";
            }
        } else {
            composing = code + "（" + composing + "）";
        }
        return composing;
    }

    public boolean isInputingCn() {
        return isInputingCn;
    }

    public void setInputingCn(boolean isInputingCn) {
        this.isInputingCn = isInputingCn;
        if (false == isInputingCn) {
            inputTempCn = new HashMap<String, String>();
            // 候選置空
            ((Cj2356InputMethodService) this.getContext()).setSuggestions(null);
            // 保持提示框中的顯示一致
            ((Cj2356InputMethodService) this.getContext()).setComposingText("");
        }
    }

    public Map<String, String> getInputTempCn() {
        return inputTempCn;
    }

    public void setInputTempCn(Map<String, String> inputTempCn) {
        this.inputTempCn = inputTempCn;
    }

    @Override
    public boolean mainKeyTouchAction(String btnText, String keyPrsd) {
        if (!StringUtils.hasText(keyPrsd)) {
            return false;
        }
        Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
        InputConnection inputConnection = ser.getCurrentInputConnection();
        InputMethodStatus stat = ser.getInputMethodStatus();
        String value = (String) stat.getKeyValue(keyPrsd);
        // 取當前輸入編碼
        String code = ((InputMethodStatusCn) stat).getInputingCnCode();
        List<Item> items = null;
        items = ((InputMethodStatusCn) stat).getCandidatesInfo(code + keyPrsd,
                true);

        // 輸入的編碼帶上新鍵，沒有字對應，直接返回
        if (null == items || items.isEmpty()) {
            // 如果再打字也沒有了
            if (!((InputMethodStatusCn) stat)
                    .couldContinueInputing(code + keyPrsd)) {
                return false;
            } else {
                // 還可以繼續鍵入，所以生成一個空的，防止以後報錯
                items = new ArrayList<Item>();
            }
        }
        ((InputMethodStatusCn) stat).inputingCnCode(keyPrsd, value);

        String composingText = ((InputMethodStatusCn) stat)
                .getComposingTextForInputConn();
        if (StringUtils.hasText(composingText)) {
            // 提交正在編輯的內容
            if (Cj2356InputMethodService.SHOW_COMPOSING_TEXT_FOR_INPUT_CONN) {
                inputConnection.setComposingText(composingText, 1);
            }
        }
        // 取當前輸入編碼的候選項
        ser.setSuggestions(items);
        return true;
    }

    @Override
    public boolean mainSpaceClickAction() {
        // 如果原來是中文狀態，而且正在打字，提交候選第一個字，返回
        Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
        InputMethodStatus stat = ser.getInputMethodStatus();
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
            return true;
        }
        return super.mainSpaceClickAction();
    }

    @Override
    public boolean mainDeleteClickAction() {
        // 如果原來是中文狀態，而且正在打字
        Cj2356InputMethodService ser = ((Cj2356InputMethodService) context);
        InputMethodStatus stat = ser.getInputMethodStatus();
        if (((InputMethodStatusCn) stat).isInputingCn()) {
            InputConnection inputConnection = ser.getCurrentInputConnection();
            String getInputingCnCode = ((InputMethodStatusCn) stat)
                    .getInputingCnCode();
            // 如果當前只打了一個鍵
            if (getInputingCnCode.length() <= 1) {
                inputConnection.commitText("", 1);
                ((InputMethodStatusCn) stat).setInputingCn(false);
            } else {
                // 如果當前只打了两個以上的鍵
                // 將臨時輸入置成第一至倒數第二個鍵
                String code = "";
                String value = "";
                for (int index = 0; index < getInputingCnCode.length()
                        - 1; index++) {
                    Character thecode = getInputingCnCode.charAt(index);
                    code += thecode.toString();
                    value += ((InputMethodStatusCn) stat)
                            .getKeyValue(thecode.toString());
                }

                // 先置空，再放進去
                ((InputMethodStatusCn) stat).inputingCnCode(null, null);
                ((InputMethodStatusCn) stat).inputingCnCode(code, value);

                String composingText = ((InputMethodStatusCn) stat)
                        .getComposingTextForInputConn();
                if (StringUtils.hasText(composingText)) {
                    // 提交正在編輯的內容
                    if (Cj2356InputMethodService.SHOW_COMPOSING_TEXT_FOR_INPUT_CONN) {
                        inputConnection.setComposingText(composingText, 1);
                    }
                }

                // 取當前輸入編碼的候選項
                List<Item> items = ((InputMethodStatusCn) stat)
                        .getCandidatesInfo(code, true);
                if (items == null) {
                    // 還可以繼續鍵入，所以生成一個空的，防止以後報錯
                    items = new ArrayList<Item>();
                }
                ser.setSuggestions(items);
            }
            return true;
        }
        return super.mainDeleteClickAction();
    }

}
