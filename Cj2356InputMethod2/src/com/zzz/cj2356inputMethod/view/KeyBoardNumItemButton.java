package com.zzz.cj2356inputMethod.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 符號鍵盤等，用自定義按钮，注意三個構造式都要有
 * 
 * @author fszhouzz@qq.com
 * @time 2018年10月20日 上午1:07:09
 */
public class KeyBoardNumItemButton extends Button {

    private Context context;

    public KeyBoardNumItemButton(Context context) {
        super(context);
        this.context = context;
    }

    public KeyBoardNumItemButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyBoardNumItemButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
