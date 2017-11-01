package com.zzz.cj2356inputMethod;

import java.util.List;

import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.mb.MbUtils;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.Cangjie2356ConfigUtils;
import com.zzz.cj2356inputMethod.utils.Cangjie2356IMsUtils;
import com.zzz.cj2356inputMethod.utils.StringUtils;
import com.zzz.cj2356inputMethod.view.CandidateViewIniter;
import com.zzz.cj2356inputMethod.view.ChooseKeyboardLayoutTabIniter;
import com.zzz.cj2356inputMethod.view.ComposingTextView;
import com.zzz.cj2356inputMethod.view.KeyboardBodyIniter;
import com.zzz.cj2356inputMethod.view.KeyboardNumIniter;
import com.zzz.cj2356inputMethod.view.KeyboardSimIniter;

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.widget.Toast;

public class Cj2356InputMethodService extends InputMethodService {
    // 提交正在編輯的內容
    public static boolean SHOW_COMPOSING_TEXT = false;

    private View keyboardView; // 鍵盤

    private ComposingTextView composingTextView;

    @Override
    public void onInitializeInterface() {
        // 初始化詞典數據
        MbUtils.init(this);
        
        Cangjie2356ConfigUtils.init(this);
        
        Cangjie2356IMsUtils.init(this);
    }

    /**
     * 隱藏輸入法
     */
    @Override
    public void onWindowHidden() {
        if (null != getInputMethodStatus()) {
            // 停止中文輸入狀態
            if (getInputMethodStatus().isShouldTranslate()) {
                if (((InputMethodStatusCn) getInputMethodStatus()).isInputingCn()) {
                    ((InputMethodStatusCn) getInputMethodStatus()).setInputingCn(false);
                }
            }
        }

        KeyboardSimIniter.resetKeyboardSimPage();
        KeyboardNumIniter.resetKeyboardNumPage();
    }

    /**
     * 設置候選數據
     */
    public void setSuggestions(List<Item> suggestion) {
        CandidateViewIniter.setSuggestions(suggestion);
    }

    public List<Item> getSuggestions() {
        return CandidateViewIniter.getSuggestions();
    }

    public View onCreateCandidatesView() {
        composingTextView = new ComposingTextView(this);
        return composingTextView;
    }

    public void setComposingText(String text) {
        if (null != composingTextView && StringUtils.hasText(text)
                && false == Cj2356InputMethodService.SHOW_COMPOSING_TEXT) {
            composingTextView.setComposingText(text);
            setCandidatesViewShown(true);
        } else {
            composingTextView.setComposingText("");
            setCandidatesViewShown(false);
        }
    }

    // 解決問題：候選區底隱顯，使其牠應用老是上下移動
    @Override
    public void onComputeInsets(InputMethodService.Insets outInsets) {
        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            // visibleTopInsets默認是從下到候選區的高度
            // contentTopInsets默認是從下到打字區高度
            outInsets.visibleTopInsets = outInsets.contentTopInsets;
        }
    }

    @Override
    public View onCreateInputView() {
        keyboardView = getLayoutInflater().inflate(R.layout.keyboard, null);
        // 候選欄
        try {
            CandidateViewIniter.initCandidateView(this, keyboardView);
        } catch (Exception e) {
            Toast.makeText(this, "候選欄初始化失敗：" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        // 鍵盤
        try {
            KeyboardBodyIniter.initKeyboardBody(this, keyboardView, R.layout.keyboard_qwerty1);
        } catch (Exception e) {
            Toast.makeText(this, "主鍵盤初始化失敗：" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        // 第一個輸入法
        try {
            setInputMethodStatus(Cangjie2356IMsUtils.getFirstIm());
        } catch (Exception e) {
            Toast.makeText(this, "輸入法初始化失敗：" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        // 數字鍵盤
        try {
            KeyboardNumIniter.initKeyboardSim(this, keyboardView);
        } catch (Exception e) {
            Toast.makeText(this, "數字鍵盤初始化失敗：" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        // 符號鍵盤
        try {
            KeyboardSimIniter.initKeyboardSim(this, keyboardView);
        } catch (Exception e) {
            Toast.makeText(this, "符號鍵盤初始化失敗：" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            // 選擇鍵盤
            ChooseKeyboardLayoutTabIniter.initChooseKeyboardLayoutTab(this, keyboardView);
        } catch (Exception e) {
            Toast.makeText(this, "選擇鍵盤控件初始化失敗：" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        // 返回View对象
        return keyboardView;
    }

    /**
     * 設置輸入法狀態
     */
    public void setInputMethodStatus(InputMethodStatus stat) {
        KeyboardBodyIniter.setInputMethodStatus(stat);
        Cangjie2356IMsUtils.setCurrentIm(stat.getType(), stat);
    }

    /**
     * 輸入法狀態
     */
    public InputMethodStatus getInputMethodStatus() {
        return KeyboardBodyIniter.getInputMethodStatus();
    }

    /** 隱藏換鍵盤控件 */
    public void hideKeyboardChooser() {
        ChooseKeyboardLayoutTabIniter.hideKeyboardChooser();
    }

    /** 顯示換鍵盤控件 */
    public void showKeyboardChooser() {
        ChooseKeyboardLayoutTabIniter.showKeyboardChooser();
    }

}
