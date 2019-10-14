package com.zzz.cj2356inputMethod.view;

import java.util.List;

import com.zzz.cj2356inputMethod.Cj2356InputMethodService;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.font.FontManager;
import com.zzz.cj2356inputMethod.listener.OnCandidateItemClickListener;
import com.zzz.cj2356inputMethod.state.InputMethodStatus;
import com.zzz.cj2356inputMethod.state.trans.InputMethodStatusCn;
import com.zzz.cj2356inputMethod.utils.DipPxUtil;
import com.zzz.cj2356inputMethod.utils.StringUtils;
import com.zzz.cj2356inputMethod.utils.UnicodeConvertUtil;
import com.zzz.cj2356inputMethod.utils.UnicodeHanziUtil;

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
 * 候選項文本標籤
 * 
 * @author t
 * @time 2017-1-8下午9:32:13
 */
public class CandidateItemTextView extends TextView {

    /** 候選欄是否顯示每個的編碼。 */
    public static boolean showEncode = false;

    private Context context = null;

    private Item item;

    public CandidateItemTextView(Context context, Item it) {
        super(context);
        this.context = context;
        this.item = it;
        String cha = it.getCharacter();
        setText(cha);
        this.setMinimumWidth(DipPxUtil.dip(context, 50));

        if (showEncode) {
            this.setPadding(50, 0, 50, 0);
        } else {
            this.setPadding(30, 0, 30, 0);
        }

        RelativeLayout.LayoutParams lpParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        setLayoutParams(lpParams);
        setTextSize(24);
        setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
        setOnClickListener(new OnCandidateItemClickListener(context));

        setTypeface(FontManager.getTypeface(context));

        // 如果是私用區的，字體顯紅色
        if (null != cha) {
            if (UnicodeHanziUtil.isInPrivateUserArea(cha)) {
                this.setTextColor(Color.parseColor("#FF0000"));
            } else if (UnicodeHanziUtil.isInhanziCompt(cha)) {
                this.setTextColor(Color.parseColor("#CC0000"));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 邊框
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        canvas.drawLine(0, 0, 0, this.getWidth(), paint);

        String str = item.getEncode();
        if (showEncode && StringUtils.hasText(str)) {
            InputMethodStatus stat = ((Cj2356InputMethodService) this
                    .getContext()).getInputMethodStatus();
            if (stat.isShouldTranslate()) {
                InputMethodStatusCn cnstat = (InputMethodStatusCn) stat;
                str = cnstat.translateCode2Name(str);
            }

            paint.setStrokeWidth(3);
            paint.setTextSize(DipPxUtil.dip(context, 10));
            paint.setColor(Color.RED);
            paint.setTextAlign(Align.LEFT);
            Rect bounds = new Rect();
            paint.getTextBounds(str, 0, str.length(), bounds);
            canvas.drawText(str, getMeasuredWidth() / 2 - bounds.width() / 2,
                    bounds.height(), paint);
        }
        // 展示統一碼碼位
        String cha = item.getCharacter();
        if (StringUtils.hasText(cha)) {
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
            canvas.drawText(code, getMeasuredWidth() / 2 - bounds.width() / 2,
                    getMeasuredHeight() - DipPxUtil.dip(context, 1), paint);
        }
    }

}
