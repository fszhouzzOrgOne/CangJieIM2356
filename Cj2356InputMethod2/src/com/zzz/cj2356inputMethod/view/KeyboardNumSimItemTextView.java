package com.zzz.cj2356inputMethod.view;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.listener.OnKeyNumSimItemClickListener;
import com.zzz.cj2356inputMethod.utils.DipPxUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    public KeyboardNumSimItemTextView(Context context, String text) {
        super(context);
        setText(text);
        this.setMinimumHeight(DipPxUtil.dip(context, 45));

        this.setPadding(0, 10, 0, 10);

        RelativeLayout.LayoutParams lpParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
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
    }

}
