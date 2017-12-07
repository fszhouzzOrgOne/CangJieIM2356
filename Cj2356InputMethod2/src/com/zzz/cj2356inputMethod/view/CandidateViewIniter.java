package com.zzz.cj2356inputMethod.view;

import java.util.List;

import com.zzz.cj2356inputMethod.R;
import com.zzz.cj2356inputMethod.dto.Item;
import com.zzz.cj2356inputMethod.listener.OnHideClickListener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class CandidateViewIniter {

    private static Context context;
    private static View keyboardView;

    private static List<Item> suggestionlist; // 当前候選詞列表
    private static LinearLayout candiScrollContent; // 候選項

    public static void initCandidateView(Context con, View kbView) {
        context = con;
        keyboardView = kbView;

        keyboardView.findViewById(R.id.candiLeftBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View paramView) {
                HorizontalScrollView sv = (HorizontalScrollView) keyboardView.findViewById(R.id.candiScroll);
                sv.arrowScroll(View.FOCUS_LEFT);

            }
        });
        keyboardView.findViewById(R.id.candiRightBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View paramView) {
                HorizontalScrollView sv = (HorizontalScrollView) keyboardView.findViewById(R.id.candiScroll);
                sv.arrowScroll(View.FOCUS_RIGHT);

            }
        });
        candiScrollContent = (LinearLayout) keyboardView.findViewById(R.id.candiScrollContent);
        // 隱藏輸入法
        keyboardView.findViewById(R.id.keybtnHide).setOnClickListener(new OnHideClickListener(context));

        setSuggestions(null);
    }

    public static void setSuggestions(List<Item> suggestion) {
        // 根据按下的按钮设置候选词列表
        suggestionlist = suggestion;
        candiScrollContent.removeAllViews();
        // 左移焦點
        HorizontalScrollView sv = (HorizontalScrollView) keyboardView.findViewById(R.id.candiScroll);
        sv.arrowScroll(View.FOCUS_LEFT);

        // 爲null說明不能再繼續鍵入了
        // 爲空列表，說明還可以繼續鍵入，只是當前沒有找到
        if (null == suggestion || suggestion.isEmpty()) {
            keyboardView.findViewById(R.id.keyboardCandidateCtrls).setVisibility(View.VISIBLE);
            keyboardView.findViewById(R.id.keyboardCandidateBody).setVisibility(View.GONE);
        } else {
            keyboardView.findViewById(R.id.keyboardCandidateCtrls).setVisibility(View.GONE);
            keyboardView.findViewById(R.id.keyboardCandidateBody).setVisibility(View.VISIBLE);

            for (Item it : suggestion) {
                CandidateItemTextView tv = new CandidateItemTextView(context, it);
                candiScrollContent.addView(tv);
            }
        }
    }

    public static List<Item> getSuggestions() {
        return suggestionlist;
    }
}
