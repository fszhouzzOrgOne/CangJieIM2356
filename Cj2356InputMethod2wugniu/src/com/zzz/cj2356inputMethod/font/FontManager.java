package com.zzz.cj2356inputMethod.font;

import android.content.Context;
import android.graphics.Typeface;

/**
 * 自定義字體
 */
public class FontManager {

    private static final String FONT_NAME = "fonts/KaiXinSong3B_B2G.ttf";

    private static Typeface typeface = null;

    public static Typeface getTypeface(Context context) {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), FONT_NAME);
        }
        return typeface;
    }
    
}
