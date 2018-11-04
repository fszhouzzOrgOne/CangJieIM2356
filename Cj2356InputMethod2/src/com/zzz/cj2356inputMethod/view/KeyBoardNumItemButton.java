package com.zzz.cj2356inputMethod.view;

import java.util.List;

import com.zzz.cj2356inputMethod.utils.DipPxUtil;
import com.zzz.cj2356inputMethod.utils.StringUtils;
import com.zzz.cj2356inputMethod.utils.UnicodeConvertUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
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
    private boolean showUnicode; // 是否顯示統一碼

    public KeyBoardNumItemButton(Context context) {
        super(context);
        this.context = context;
        setTextColor(Color.RED);
    }

    public KeyBoardNumItemButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public KeyBoardNumItemButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void setShowUnicode(boolean show) {
        showUnicode = show;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 展示統一碼碼位
        String cha = null == this.getText() ? "" : this.getText().toString();
        if (showUnicode && StringUtils.hasText(cha)) {
            Paint paint = new Paint();
            // 統一碼碼位
            String code = "";
            List<String> codes = UnicodeConvertUtil.getUnicodeStr4ListFromStr(cha);
            if (null != codes && codes.size() == 1) {
                code = codes.get(0);
            }

            paint.setStrokeWidth(3);
            paint.setTextSize(DipPxUtil.dip(context, 10));
            paint.setColor(Color.GRAY);
            paint.setTextAlign(Align.LEFT);
            Rect bounds = new Rect();
            paint.getTextBounds(code, 0, code.length(), bounds);
            canvas.drawText(code, getMeasuredWidth() / 2 - bounds.width() / 2,
                    getMeasuredHeight() - DipPxUtil.dip(context, 1), paint);
        }
    }
}
