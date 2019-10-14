package com.zzz.cj2356inputMethod.view;

import java.util.List;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.listener.OnKeyNumSimItemClickListener;
import com.zzz.cj2356inputMethod.utils.DipPxUtil;
import com.zzz.cj2356inputMethod.utils.UnicodeConvertUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

/***
 * 數字鍵盤中的滑動符號
 * 
 * @author t
 * @time 2017-1-8下午9:32:13
 */
public class KeyboardNumSimItemTextView extends TextView {

    private Context context = null;

    public KeyboardNumSimItemTextView(Context context, String text) {
        super(context);
        this.context = context;
        setTextColor(Color.DKGRAY);
        setText(text);
        this.setMinimumHeight(DipPxUtil.dip(context, 45));

        this.setPadding(0, 10, 0, 10);

        RelativeLayout.LayoutParams lpParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lpParams);
        setTextSize(20);
        setBackgroundResource(R.drawable.num_button_selector);
        setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
        setOnClickListener(new OnKeyNumSimItemClickListener(context));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 邊框
        Paint paint = new Paint();
        paint.setColor(android.graphics.Color.LTGRAY);
        canvas.drawLine(0, 0, this.getWidth(), 0, paint);

        CharSequence text = this.getText();
        if (null == text) {
            return;
        } else if ("\\t".equals(text) || "Tab".equals(text)) {
            text = "\t";
        }
        // 統一碼的展示
        String cha = text.toString();
        // 統一碼碼位
        String code = "";
        List<String> codes = UnicodeConvertUtil
                .getUnicodeStr4ListFromStr(cha.trim());
        if (null != codes && codes.size() == 1) {
            code = codes.get(0);
        }

        paint.setStrokeWidth(3);
        paint.setTextSize(DipPxUtil.dip(context, 10));
        paint.setColor(Color.GRAY);
        paint.setTextAlign(Align.LEFT);
        Rect bounds = new Rect();
        paint.getTextBounds(code, 0, code.length(), bounds);
        canvas.drawText(code, DipPxUtil.dip(context, 5),
                getMeasuredHeight() - DipPxUtil.dip(context, 1), paint);
    }

}
