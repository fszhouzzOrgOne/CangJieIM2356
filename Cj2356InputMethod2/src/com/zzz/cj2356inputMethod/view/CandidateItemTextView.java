package com.zzz.cj2356inputMethod.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.font.FontManager;
import com.zzz.cj2356inputMethod.listener.OnCandidateItemClickListener;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.StringUtils;

/***
 * 候選項文本標籤
 * 
 * @author t
 * @time 2017-1-8下午9:32:13
 */
public class CandidateItemTextView extends TextView {

    private Item item;

    public CandidateItemTextView(Context context, Item it) {
        super(context);
        this.item = it;
        setText(it.getCharacter());

        this.setPadding(50, 0, 50, 0);

        RelativeLayout.LayoutParams lpParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        setLayoutParams(lpParams);
        setTextSize(24);
        setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
        setOnClickListener(new OnCandidateItemClickListener(context));

        setTypeface(FontManager.getTypeface(context));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String str = item.getEncode();
        if (StringUtils.hasText(str)) {
            InputMethodStatus stat = ((Cj2356InputMethodService) this
                    .getContext()).getInputMethodStatus();
            if (InputMethodStatusCn.TYPE_CODE.equals(stat.getType())) {
                InputMethodStatusCn cnstat = (InputMethodStatusCn) stat;
                str = cnstat.translateCode2Name(str);
            }

            Paint paint = new Paint();
            paint.setStrokeWidth(3);
            paint.setTextSize(27);
            paint.setColor(Color.RED);
            paint.setTextAlign(Align.LEFT);
            Rect bounds = new Rect();
            paint.getTextBounds(str, 0, str.length(), bounds);
            canvas.drawText(str, getMeasuredWidth() / 2 - bounds.width() / 2,
                    bounds.height(), paint);
        }
    }

}
